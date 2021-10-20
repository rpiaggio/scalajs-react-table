package reactST.reactTable

import japgolly.scalajs.react._
import japgolly.scalajs.react.facade.React.ComponentClassP
import japgolly.scalajs.react.vdom.html_<^._
import react.common.Css
import react.virtuoso.Virtuoso
import reactST.reactTable.anon.Data
import reactST.reactTable.mod.ColumnInterfaceBasedOnValue._
import reactST.reactTable.mod.Row
import reactST.reactTable.mod.Cell
import reactST.reactTable.mod.TableState
// import reactST.reactTable.mod.UseSortByColumnOptions
import reactST.reactTable.util._
import reactST.reactTable.syntax._
import reactST.std.Partial

import scalajs.js
import scalajs.js.|
import scalajs.js.JSConverters._

object HTMLTable {

  /**
   * Create a table component based on the configured plugins of a `TableDef`.
   *
   * @param tableDef
   *   The TableDef providing the useTable hook.
   * @param headerCellFn
   *   A function to use for creating the <th> elements. Simple ones are provided by the TableDef
   *   object.
   * @param tableClass
   *   An optional CSS class to apply to the table element
   * @param RowTypeClassFn
   *   An option function that takes the index and the RowTypeData and creates a class for the
   *   RowType. Relying on index does not work well for sortable tables since it is the unsorted
   *   index.
   * @param footer
   *   An optional <tfoot> element. (see note below)
   *
   * Notes:
   *   - If optimization is required, parts of the options and the data should be memoized and only
   *     change when necessary. See react-table documentation for details.
   *   - The headerCellFn is expected to return a <th> element. The function should make use of the
   *     column instance "props" methods.
   *   - Table footers are fully "Build It Yourself" since it is often useful for table footers to
   *     span multiple columns and react-table does not support that. At some point, something
   *     similar for an "extra" header RowType might be useful since header groups have some issues.
   */
  def apply[
    D, // format: off
    TableInstanceType[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
    ColumnType[d] <: Column[d],
    RowD <: Row[D],
    CellType[d, v] <: Cell[d, v],
    TableStateD <: TableState[D] // format: on
  ](
    tableDef: TableDef[D,
                       _,
                       TableInstanceType,
                       _,
                       ColumnType,
                       RowD,
                       CellType,
                       TableStateD,
                       _
    ] // Only used to infer types
  )(
    headerCellFn: Option[ColumnType[D] => TagMod],
    tableClass:   Css = Css(""),
    rowClassFn:   (Int, D) => Css = (_: Int, _: D) => Css(""),
    footer:       TagMod = TagMod.empty
  ) =
    ScalaFnComponent[TableInstanceType[D, ColumnType, RowD, CellType, TableStateD]] {
      tableInstance =>
        val bodyProps = tableInstance.getTableBodyProps()

        val header = headerCellFn.fold(TagMod.empty) { f =>
          <.thead(
            tableInstance.headerGroups.toTagMod { g =>
              <.tr(
                props2Attrs(g.getHeaderGroupProps()),
                g.headers.toTagMod(f(_))
              )
            }
          )
        }

        val rows = tableInstance.rows.toTagMod { rd =>
          tableInstance.prepareRow(rd)
          val rowClass = rowClassFn(rd.index.toInt, rd.original)
          val cells    = rd.cells.toTagMod { cell =>
            <.td(props2Attrs(cell.getCellProps()), cell.renderCell)
          }
          <.tr(rowClass, props2Attrs(rd.getRowProps()), cells)
        }

        <.table(tableClass, header, <.tbody(props2Attrs(bodyProps), rows), footer)
    }

  /**
   * Create a virtualized table based on the configured plugins of a `TableDef`. The plugins MUST
   * include withBlockLayout.
   *
   * @param tableDef
   *   The TableDef providing the useTable hook, configured with withBlockLayout.
   * @param options
   *   The table options to use for the table.
   * @param bodyHeight
   *   The optional height of the table body (virtualized portion) in px. See note.
   * @param headerCellFn
   *   See note. A function to use for creating the header elements. Simple ones are provided by the
   *   TableDef object.
   * @param tableClass
   *   An optional CSS class to apply to the table element
   * @param RowTypeClassFn
   *   An option function that takes the index and the RowTypeData and creates a class for the
   *   RowType. Relying on index does not work well for sortable tables since it is the unsorted
   *   index.
   *
   * Notes:
   *   - Virtualized tables are not built from html table elements such as <table>, <tr>, etc. The
   *     virtualization requires they be made from <div> elements and styled as a table. This method
   *     adds a class to each div that corresponds to it's role, such as "table", "thead", "tr",
   *     etc. The "tr" div is currently wrapped inside an additional div that handles the
   *     virtualization. This means that headerCellFn must also return a <div> instead of a <td>,
   *     and should probably also have a class of "td". The basic header functions in TableDef take
   *     a boolean flag to handle this.
   *
   *   - The body height must be set to a value either through the bodyHeight parameter or via CSS
   *     or the body will collapse to nothing. In CSS, you MUST NOT use relative values like "100%"
   *     or it won't work.
   */
  def virtualized[
    D, // format: off
    TableInstanceType[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
    ColumnType[d] <: Column[d],
    RowD <: Row[D],
    CellType[d, v] <: Cell[d, v],
    TableStateD <: TableState[D] // format: on
  ](
    tableDef: TableDef[D,
                       _,
                       TableInstanceType,
                       _,
                       ColumnType,
                       RowD,
                       CellType,
                       TableStateD,
                       Layout.NonTable
    ] // Only used to infer types
  )(
    bodyHeight:   Option[Double] = None,
    headerCellFn: Option[ColumnType[D] => TagMod],
    tableClass:   Css = Css(""),
    rowClassFn:   (Int, D) => Css = (_: Int, _: D) => Css("")
  ) =
    ScalaFnComponent[TableInstanceType[D, ColumnType, RowD, CellType, TableStateD]] {
      tableInstance =>
        val bodyProps = tableInstance.getTableBodyProps()

        val rowComp = (_: Int, row: RowD) => {
          tableInstance.prepareRow(row)
          val cells = row.cells.toTagMod { cell =>
            <.div(^.className := "td", props2Attrs(cell.getCellProps()), cell.renderCell)
          }

          val rowClass = rowClassFn(row.index.toInt, row.original)
          // This div is being wrapped inside the div that handles virtualization.
          // This means the the `getRowProps` are nested an extra layer in. This does
          // not seem to cause any issues with react-table, but could possibly be an
          // issue with some plugins.
          <.div(^.className := "tr", rowClass, props2Attrs(row.getRowProps()), cells)
        }

        val header = headerCellFn.fold(TagMod.empty) { f =>
          <.div(
            ^.className := "thead",
            tableInstance.headerGroups.toTagMod { g =>
              <.div(^.className := "tr",
                    props2Attrs(g.getHeaderGroupProps()),
                    g.headers.toTagMod(f(_))
              )
            }
          )
        }

        val height = bodyHeight.fold(TagMod.empty)(h => ^.height := s"${h}px")
        val rows   = Virtuoso[RowD](data = tableInstance.rows, itemContent = rowComp)

        <.div(^.className := "table",
              tableClass,
              header,
              <.div(^.className := "tbody", height, props2Attrs(bodyProps), rows)
        )
    }

  /**
   * A function to use in the builder methods for the headerCellFn parameter. This is a a basic
   * header.
   *
   * @param cellClass
   *   An optional CSS class to apply the the <th>.
   * @param useDiv
   *   True to use a <div> instead of a <th>. Needed for tables withBlockLayout.
   */
  def basicHeaderCellFn(
    cellClass: Css = Css.Empty,
    useDiv:    Boolean = false
  ): Column[_] => TagMod =
    col => headerCell(useDiv)(props2Attrs(col.getHeaderProps()), cellClass, col.renderHeader)

  /**
   * A function to use in the builder methods for the headerCellFn parameter. This header will make
   * the maker sortable by clicking on the header. Will only compile if the column instance is a
   * subtype of UseSortByColumnPros.
   *
   * @param cellClass
   *   An optional CSS class to apply to the <th>.
   * @param useDiv
   *   True to use a <div> instead of a <th>. Needed for tables withBlockLayout.
   * @return
   */
  def sortableHeaderCellFn(
    cellClass: Css = Css.Empty,
    useDiv:    Boolean = false
  ): Column[_] with UseSortByColumn[_] => TagMod =
    col => {
      def sortIndicator(col: UseSortByColumn[_]): TagMod =
        if (col.isSorted) {
          val index   = if (col.sortedIndex > 0) (col.sortedIndex + 1).toString else ""
          val ascDesc = if (col.isSortedDesc.getOrElse(false)) "\u2191" else "\u2193"
          <.span(s" $index$ascDesc")
        } else TagMod.empty

      val headerProps = col.getHeaderProps()
      val toggleProps = col.getSortByToggleProps()

      headerCell(useDiv)(
        props2Attrs(headerProps),
        props2Attrs(toggleProps),
        cellClass,
        col.renderHeader,
        <.span(sortIndicator(col))
      )
    }

  /**
   * A function to use in the builder methods for the footerCellFn parameter. This is a a basic
   * footer.
   *
   * @param cellClass
   *   An optional CSS class to apply the the <th>.
   * @param useDiv
   *   True to use a <div> instead of a <th>. Needed for tables withBlockLayout.
   */
  // def basicFooterCellFn(
  //   cellClass: Css = Css.Empty,
  //   useDiv:    Boolean = false
  // ): Column[_] => TagMod = { col =>
  //   col.Footer.map(_ =>
  //     headerCell(useDiv)(
  //       props2Attrs(col.getFooterProps()),
  //       cellClass,
  //       col.renderFooter
  //     )
  //   )
  // }

  private def headerCell(useDiv: Boolean) = if (useDiv) <.div(^.className := "th") else <.th()
}
