package reactST.reactTable

import japgolly.scalajs.react._
import japgolly.scalajs.react.internal.JsUtil
import japgolly.scalajs.react.raw.React.ComponentClassP
import japgolly.scalajs.react.vdom.html_<^._
import react.common.Css
import reactST.reactTable.anon.Data
import reactST.reactTable.mod.ColumnInterfaceBasedOnValue._
import reactST.reactTable.mod._
import reactST.reactWindow.WindowHelper
import reactST.reactWindow.mod.ListChildComponentProps
import reactST.std.Partial

import scalajs.js
import scalajs.js.|
import scalajs.js.JSConverters._
import reactST.reactTable.TableMaker.LayoutMarker

// format: off
trait TableMaker[
  D, TableOptsD <: UseTableOptions[D], 
  TableInstanceD <: TableInstance[D], 
  ColumnOptsD <: ColumnWithLooseAccessor[D], 
  ColumnInstanceD <: ColumnInstance[D], 
  State <: TableState[D]] { self =>
  // format: on

  import syntax._

  val plugins: List[PluginHook[D]]

  /**
   * Create an empty instance of TableOptsD.
   */
  def emptyOptions: TableOptsD = js.Dynamic.literal().asInstanceOf[TableOptsD]

  /**
   * Create an empty instance of ColumnOptsD.
   * The other column creating methods are usually a better starting place.
   */
  def emptyColumn: ColumnOptsD = js.Dynamic.literal().asInstanceOf[ColumnOptsD]

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   *
   * @param id The unique id for the column.
   * @param accessor Accessor function to get the column value from the row value.
   */
  def accessorColumn[V](id: String, accessor: D => V): ColumnOptsD =
    emptyColumn.setId(id).setAccessorFn(accessor)

  // return type of a scalajs-react component function.
  type ComponentFnResult =
    Unit | Null | Boolean | String | (Byte | Short | Int | Float | Double) | japgolly.scalajs.react.raw.React.Element | japgolly.scalajs.react.raw.recursiveTypeAliases.ChildrenArray[
      Unit | Null | Boolean | String | (Byte | Short | Int | Float | Double) | japgolly.scalajs.react.raw.React.Element
    ]

  /**
   * Create a column with a scalajs-react component in it.
   *
   * @param id The unique id for the column.
   * @param component The scalajs-component (see notes below)
   *
   * Notes:
   * The component is a ScalaJsComponent on which
   *   ".cmapCtorProps[(CellProps[RowData, A]) with js.Object].jsComponent.raw"
   *   has been called.
   * Strangely enough, for a sortable column you still need to provide an accessor,
   *   even if you provide a sorting function or the column will not be sortable. Seems
   *   like a possible bug in react-table.
   */
  def componentColumn[V](
    id:        String,
    component: js.Function1[CellProps[D, V], ComponentFnResult]
  ): ColumnOptsD = {
    type ColBasedOnValue = ColumnOptsD with ColumnInterfaceBasedOnValue[D, V]
    val col = emptyColumn.setId(id).asInstanceOf[ColBasedOnValue]

    // This should be an implicit call, but I can't get the types to line up
    val c = new ColumnInterfaceBasedOnValueOps[ColBasedOnValue, D, V](col)
    c.setCellComponentClass(
      component.asInstanceOf[ComponentClassP[(CellProps[D, V]) with js.Object]]
    )
  }

  /**
   * Create a column group with the specified columns in it.
   *
   * @param header The header for the column group. Seems to be required.
   * @param cols The columns to include in the group.
   */
  def columnGroup(header: Renderer[HeaderProps[D]], cols: ColumnOptsD*): ColumnGroup[D] =
    js.Dynamic
      .literal()
      .asInstanceOf[ColumnGroup[D]]
      .setHeader(header)
      .setColumns(cols.toJSArray.asInstanceOf[js.Array[Column[D]]])

  /**
   * Create an array of columns and column groups from the varargs parameters.
   *
   * @param cols A varargs parameter of columns or column groups.
   */
  def columnArray(cols: (ColumnGroup[D] | ColumnOptsD)*): js.Array[Column[D]] =
    cols.toJSArray.asInstanceOf[js.Array[Column[D]]]

  /**
   * Create an empty instance of type State
   */
  def emptyState: State = js.Dynamic.literal().asInstanceOf[State]

  /**
   * Create a TableInstanceD instancy by calling useTable with the
   * provided options and the plugins that have been configured by
   * the with* methods.
   *
   * This is used internally by makeTable, but may be useful for
   * creating custom tables.
   *
   * @param options The table options.
   */
  def use(options: TableOptsD): TableInstanceD =
    useTable(options, plugins: _*).asInstanceOf[TableInstanceD]

  /**
   * Create a table element based on the configured plugins.
   *
   * @param options The table options to use for the table.
   * @param data The table data.
   * @param headerCellFn A function to use for creating the <th> elements. Simple ones are provided by the TableMaker object.
   * @param tableClass An optional CSS class to apply to the table element
   * @param footer An optional <tfoot> element. (see note below)
   *
   * Notes:
   * If optimization is required, parts of the options and the data
   *   should be memoized and only change when necessary. See react-table
   *   documentation for details.
   * The headerCellFn is expected to return a <th> element. The function
   *   should make use of the column instance "props" methods.
   * The typescript definitions don't seem to include the react-table
   *   support for footers. So, they aren't available here, either.
   *   So, table footers are fully "Build It Yourself". This actually
   *   has some advantages, because it is often useful for table footers
   *   to span multiple columns and react-table does not support that.
   *   At some point, something similar for an "extra" header row might
   *   be useful since header groups have some issues.
   */
  def makeTable(
    options:      TableOptsD,
    data:         js.Array[D],
    headerCellFn: Option[ColumnInstanceD => TagMod],
    tableClass:   Css = Css(""),
    footer:       TagMod = TagMod.empty
  ) = {
    val component = ScalaFnComponent[TableOptsD] { opts =>
      val tableInstance = use(opts.setData(data))
      val bodyProps     = tableInstance.getTableBodyProps()

      val header = headerCellFn.fold(TagMod.empty) { f =>
        <.thead(
          tableInstance.headerGroups.toTagMod { g =>
            <.tr(
              TableMaker.props2Attrs(g.getHeaderGroupProps()),
              headersFromGroup(g).toTagMod(f(_))
            )
          }
        )
      }

      val rows = tableInstance.rows.toTagMod { rd =>
        tableInstance.prepareRow(rd)
        val cells = rd.cells.toTagMod { cell =>
          <.td(TableMaker.props2Attrs(cell.getCellProps()), cell.render("Cell"))
        }
        <.tr(TableMaker.props2Attrs(rd.getRowProps()), cells)
      }

      <.table(tableClass, header, <.tbody(TableMaker.props2Attrs(bodyProps), rows), footer)
    }
    component(options)
  }

  /**
   * Create a table element based on the configured plugins.
   * The plugins MUST include withBlockLayout.
   *
   * @param options The table options to use for the table.
   * @param data The table data.
   * @param rowIdFn Function to extract the row id from a data element.
   * @param rowHeight The height of each row.
   * @param bodyHeight The height of the table body (virtualized portion).
   * @param headerCellFn See note. A function to use for creating the header elements. Simple ones are provided by the TableMaker object.
   * @param tableClass An optional CSS class to apply to the table element
   *
   * Notes:
   * Virtualized tables are not built from html table elements such as
   * <table>, <tr>, etc. The virtualization requires they be made from
   * <div> elements and styled as a table. This method adds a class to
   * each div that corresponds to it's role, such as "table", "thead",
   * "tr", etc.
   * This means that headerCellFn must also return a <div> instead
   * of a <td>, and should probably also have a class of "td". The basic
   * header functions in TableMaker take a boolean flag to handle this.
   */
  def makeVirtualizedTable(
    options:           TableOptsD,
    data:              js.Array[D],
    rowIdFn:           D => String,
    rowHeight:         Double,
    bodyHeight:        Double,
    headerCellFn:      Option[ColumnInstanceD => TagMod],
    tableClass:        Css = Css("")
  )(implicit evidence: TableOptsD <:< LayoutMarker) = {

    val component = ScalaFnComponent[TableOptsD] { opts =>
      val tableInstance = use(opts.setData(data))
      val bodyProps     = tableInstance.getTableBodyProps()

      val rowComponent = ScalaComponent
        .builder[ListChildComponentProps]
        .render_P { props =>
          val row   = props.data.asInstanceOf[js.Array[Row[D]]](props.index.toInt)
          tableInstance.prepareRow(row)
          val cells = row.cells.toTagMod { cell =>
            <.div(^.className := "td",
                  TableMaker.props2Attrs(cell.getCellProps()),
                  cell.render("Cell")
            )
          }
          <.div(^.className := "tr",
                ^.style := props.style,
                TableMaker.props2Attrs(row.getRowProps()),
                cells
          )
        }
        .build
        .toJsComponent
        .raw

      val header = headerCellFn.fold(TagMod.empty) { f =>
        <.div(
          ^.className := "thead",
          tableInstance.headerGroups.toTagMod { g =>
            <.div(^.className := "tr",
                  TableMaker.props2Attrs(g.getHeaderGroupProps()),
                  headersFromGroup(g).toTagMod(f(_))
            )
          }
        )
      }

      val itemKeyFn: (Double, js.Any) => reactST.react.mod.Key =
        (index, d) => rowIdFn(d.asInstanceOf[js.Array[Row[D]]](index.toInt).original)

      val rows = WindowHelper
        .fixedSizeList(rowComponent = rowComponent,
                       height = bodyHeight,
                       itemCount = data.length,
                       itemSize = rowHeight,
                       width = tableInstance.totalColumnsWidth
        )
        .itemData(tableInstance.rows)
        .itemKey(itemKeyFn)

      <.div(^.className := "table",
            tableClass,
            header,
            <.div(^.className := "tbody", TableMaker.props2Attrs(bodyProps), rows)
      )
    }
    component(options.setRowIdFn(rowIdFn))
  }

  /*
   * When adding new plugins, see https://github.com/DefinitelyTyped/DefinitelyTyped/tree/master/types/react-table
   * for help determining what the necessary type changes are.
   *
   * Heads Up! It doesn't seem to be documented, but the order of plugins seems
   * to matter. If you put useSortBy before useFilters you will get a runtime error.
   * We may want to add some means of ordering the plugs when we submit them to
   * "useTable" if we can figure out what the required order is.
   */

  /**
   * Add sort capabilities to the table via the useSortBy plugin hook.
   */
  // format: off
  def withSort = new TableMaker[
    D, 
    TableOptsD with UseSortByOptions[D], 
    TableInstanceD with UseSortByInstanceProps[D], 
    ColumnOptsD with UseSortByColumnOptions[D], 
    ColumnInstanceD with UseSortByColumnProps[D], 
    State with UseSortByState[D]] {
    val plugins = self.plugins :+ useSortBy.asInstanceOf[PluginHook[D]]
  }
  // format: on

  /**
   * Adds support for headers and cells to be rendered as inline-block divs
   * (or other non-table elements) with explicit width. This becomes useful if and when you need
   * to virtualize rows and cells for performance.
   *
   * NOTE: Although no additional options are needed for this plugin to work, the core column
   * options width, minWidth and maxWidth are used to calculate column and cell widths and must
   * be set.
   */
  // format: off
  def withBlockLayout = new TableMaker[
    D, 
    TableOptsD with LayoutMarker, // A marker trait to limit calls to makeVirtualizedTable.
    TableInstanceD, 
    ColumnOptsD, 
    ColumnInstanceD, 
    State] {
    val plugins = self.plugins :+ useBlockLayout.asInstanceOf[PluginHook[D]]
  }
  // format: on

  // When trying to use a more traditional "syntax" package and implicit
  // classes, the compiler seems to somehow loose type information along
  // the way. Putting the syntax here allows resolution to work correctly.
  object syntax {
    implicit class TableOptionOps(val table: TableOptsD) {

      /**
       * Sets the initial state of the table.
       *
       * The provided setInitialState method takes a Partial[TableState[D]]
       */
      def setInitialStateFull(s: TableState[D]): TableOptsD =
        table.setInitialState(s.asInstanceOf[Partial[TableState[D]]])

      /**
       * Sets the row id for the rows of the table based on a function.
       *
       * @param f A function from the row type to the row id.
       */
      def setRowIdFn(f: D => String): TableOptsD =
        table.setGetRowId((data, _, _) => f(data))
    }

    implicit class ColumnOptionOps(val col: ColumnOptsD) {

      /**
       * Sets the accessorFunction for the column.
       *
       * @param f A function from the row type to the column type.
       */
      def setAccessorFn[V](f: D => V): ColumnOptsD =
        col.setAccessorFunction3(TableMaker.accessorFn(f))

      /**
       * Sets the sorting for the column based on a function.
       *
       * @param f A function from the row type to the column type.
       * @param ordering An implicit ordering for the column type.
       * @param evidence Evidence that this column is sortable. (See note)
       *
       * Note:
       * This method is only valid for columns that are sortable via the
       *   useSortBy plugin. The compiler was unable to resolve the types if
       *   an implicit class requiring UseSortByColumnOptions[D] was used, so
       *   I switched to requiring evidence that ColumnOptsD is a subtype
       *   of UseSortByColumnOptions[D] and that worked. Unfortunately, requires
       *   asInstanceOfs.
       */
      def setSortByFn[V](
        f:                 D => V
      )(implicit ordering: Ordering[V], evidence: ColumnOptsD <:< UseSortByColumnOptions[D]) = {
        val sbfn: SortByFn[D] = (d1, d2, _, _) =>
          ordering.compare(f(d1.original), f(d2.original)).toDouble
        // these asInstanceOfs should be safe, but is there a way around them?
        col.asInstanceOf[UseSortByColumnOptions[D]].setSortType(sbfn).asInstanceOf[ColumnOptsD]
      }
    }
  }

  private def headersFromGroup(headerGroup: HeaderGroup[D]) =
    headerGroup.headers.asInstanceOf[js.Array[ColumnInstanceD]]
}

object TableMaker {
  // format: off
  def apply[D] = new TableMaker[
    D, 
    UseTableOptions[D], 
    TableInstance[D], 
    ColumnWithLooseAccessor[D], 
    ColumnInstance[D], 
    TableState[D]] {
      val plugins = List.empty
    }
    // format: on

  /**
   * Helper method for taking an object return by a react-table
   * and turning it into a TagMod of attributes.
   */
  def props2Attrs(obj: js.Object): TagMod = TagMod.fn { b =>
    for ((k, v) <- JsUtil.objectIterator(obj)) b.addAttr(k, v)
  }

  /**
   * A function to use in TableMaker.makeTable for the
   * headerCellFn parameter. This is a a basic header.
   *
   * @param cellClass An optional CSS class to apply the the <th>.
   * @param useDiv True to use a <div> instead of a <th>. Needed for tables withBlockLayout.
   */
  def basicHeaderCellFn(
    cellClass: Css = Css(""),
    useDiv:    Boolean = false
  ): ColumnInstance[_] => TagMod =
    col => headerCell(useDiv)(props2Attrs(col.getHeaderProps()), cellClass, col.render("Header"))

  /**
   * A function to use in TableMaker.makeTable for the headerCellFn
   * parameter. This header will make the maker sortable by clicking
   * on the header. Will only compile if the column instance is a subtype
   * of UseSortByColumnPros.
   *
   * @param cellClass An optional CSS class to apply to the <th>.
   * @param useDiv True to use a <div> instead of a <th>. Needed for tables withBlockLayout.
   * @return
   */
  def sortableHeaderCellFn(
    cellClass: Css = Css(""),
    useDiv:    Boolean = false
  ): ColumnInstance[_] with UseSortByColumnProps[_] => TagMod =
    col => {
      def sortIndicator(col: UseSortByColumnProps[_]): TagMod =
        if (col.isSorted) {
          val index   = if (col.sortedIndex > 0) (col.sortedIndex + 1).toString else ""
          val ascDesc = if (col.isSortedDesc.getOrElse(false)) "\u2191" else "\u2193"
          <.span(s" $index$ascDesc")
        } else TagMod.empty

      val headerProps = col.getHeaderProps()
      val toggleProps = col.getSortByToggleProps()

      // If both props have a "styles", only the last one gets used. Apparently, scalajs.react doesn't
      // merge them. So, we have to combine them manually.
      val styles = if (headerProps.hasOwnProperty("style") && toggleProps.hasOwnProperty("style")) {
        val headerStyles =
          js.Object.entries(
            js.Object.getOwnPropertyDescriptor(headerProps, "style").value.asInstanceOf[js.Object]
          )
        val toggleStyles =
          js.Object.entries(
            js.Object.getOwnPropertyDescriptor(toggleProps, "style").value.asInstanceOf[js.Object]
          )
        ^.style := js.Object.fromEntries(headerStyles ++ toggleStyles)
      } else TagMod.empty

      headerCell(useDiv)(
        props2Attrs(headerProps),
        props2Attrs(toggleProps),
        styles,
        cellClass,
        col.render("Header"),
        <.span(sortIndicator(col))
      )
    }

  def accessorFn[D, V](f: D => V): js.Function3[D, Double, Data[D], js.Any] =
    (data, _, _) => f(data).asInstanceOf[js.Any]

  private def headerCell(useDiv: Boolean) = if (useDiv) <.div(^.className := "th") else <.th()

  trait LayoutMarker
}
