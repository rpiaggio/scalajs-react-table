// Copyright (c) 2016-2021 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package reactST.reactTable

import japgolly.scalajs.react.Reusable
import japgolly.scalajs.react.facade
import japgolly.scalajs.react.facade.React.ComponentClassP
import japgolly.scalajs.react.vdom._
import org.scalablytyped.runtime.StObject
import reactST.reactTable.anon.Data
import reactST.reactTable.anon.IdIdType
import reactST.reactTable.anon.IdIdType._
import reactST.reactTable.anon.`1`._
import reactST.reactTable.mod.ColumnInterfaceBasedOnValue._
import reactST.reactTable.mod.{ ^ => _, _ }
import reactST.std.Partial

import scalajs.js
import scalajs.js.|
import scalajs.js.JSConverters._
import javax.sql.RowSetMetaData

case class TableDefWithOptions[
  D,
  TableOptsD <: UseTableOptions[D],
  ColumnOptsD <: ColumnOptions[D],
  ColumnObjectD <: ColumnObject[D],
  RowD <: Row[D],
  CellD <: Cell[D, js.Any],
  TableStateD <: TableState[D],
  TableInstanceD[_, _ /*<: ColumnOptsD*/, _, _, _, _] <: TableInstanceTyped[_, _, _, _, _, _],
  Layout
](
  tableDef: TableDef[
    D,
    TableOptsD,
    ColumnOptsD,
    ColumnObjectD,
    RowD,
    CellD,
    TableStateD,
    TableInstanceD,
    Layout
  ],
  cols:     Reusable[List[ColumnInterface[D]]],
  data:     Reusable[List[D]],
  modOpts:  Reusable[TableOptsD => TableOptsD]
)

case class TableDef[
  D,
  TableOptsD <: UseTableOptions[D],
  ColumnOptsD <: ColumnOptions[D],
  ColumnObjectD <: ColumnObject[D],
  RowD <: Row[D],
  CellD <: Cell[D, js.Any],
  TableStateD <: TableState[D],
  TableInstanceD[_, _, _, _, _, _] <: TableInstanceTyped[_, _, _, _, _, _],
  Layout
](plugins: Set[Plugin]) {
  type OptionsType       = TableOptsD
  type InstanceType      = TableInstanceD[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD]
  type ColumnOptionsType = ColumnOptsD
  type ColumnType        = ColumnObjectD
  type RowType           = RowD
  type CellType          = CellD
  type StateType         = TableStateD

  import syntax._

  def apply(
    cols:    Reusable[List[ColumnInterface[D]]],
    data:    Reusable[List[D]],
    modOpts: Reusable[TableOptsD => TableOptsD] = Reusable.always(identity[TableOptsD] _)
  ): TableDefWithOptions[
    D,
    TableOptsD,
    ColumnOptsD,
    ColumnObjectD,
    RowD,
    CellD,
    TableStateD,
    TableInstanceD,
    Layout
  ] =
    TableDefWithOptions(this, cols, data, modOpts)

  private def emptyOptions: TableOptsD = js.Dynamic.literal().asInstanceOf[TableOptsD]

  /**
   * Create a TableOptsD. columns and data are required. Other options can be `set*`.
   */
  protected[reactTable] def Options(
    columns: js.Array[ColumnInterface[D]],
    data:    js.Array[D]
  ): TableOptsD =
    emptyOptions
      .setColumns(columns.asInstanceOf[js.Array[Column[D]]])
      .setData(data)

  /**
   * Create an empty instance of ColumnOptsD. As per react-table's doc: Warning: Only omit accessor
   * if you really know what you're doing.
   */
  def emptyColumn[V]: ColumnValueOptions[D, V, ColumnOptsD] =
    js.Dynamic.literal().asInstanceOf[ColumnValueOptions[D, V, ColumnOptsD]]

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor string.
   */
  def Column[V](accessor: String): ColumnValueOptions[D, V, ColumnOptsD] =
    emptyColumn[V].setAccessor(accessor)

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   */
  def Column[V](id: String, accessor: D => V): ColumnValueOptions[D, V, ColumnOptsD] =
    emptyColumn[V].setId(id).setAccessorFn(accessor)

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   */
  def Column[V](id: String, accessor: (D, Int) => V): ColumnValueOptions[D, V, ColumnOptsD] =
    emptyColumn[V].setId(id).setAccessorFn(accessor)

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   */
  def Column[V](
    id:       String,
    accessor: (D, Int, Data[D]) => V
  ): ColumnValueOptions[D, V, ColumnOptsD] =
    emptyColumn.setId(id).setAccessorFn(accessor)

  /**
   * Create a column group with the specified columns in it.
   *
   * @param header
   *   The header for the column group. Seems to be required.
   * @param cols
   *   The columns to include in the group.
   */
  def ColumnGroup(cols: (ColumnGroup[D] | ColumnOptsD)*): ColumnGroupOptions[D] =
    js.Dynamic
      .literal()
      .asInstanceOf[ColumnGroupOptions[D]]
      .setColumns(cols.toJSArray.asInstanceOf[js.Array[Column[D]]])

  /**
   * Create an empty instance of type TableStateD
   */
  def State(): TableStateD = js.Dynamic.literal().asInstanceOf[TableStateD]

  /*
   * When adding new plugins, see https://github.com/DefinitelyTyped/DefinitelyTyped/tree/master/types/react-table
   * for help determining what the necessary type changes are.
   *
   * Hooks need to have facades created in TableHooks.scala, as the facades created by
   * ScalablyTyped won't work as hooks.
   */

  protected[reactST] def withFeaturePlugin[
    NewTableOptsD <: UseTableOptions[D],
    NewColumnOptsD <: ColumnOptions[D],
    NewColumnObjectD <: ColumnObject[D],
    NewRowD <: Row[D],
    NewCellD <: Cell[D, js.Any],
    NewTableStateD <: TableState[D],
    NewTableInstanceD[_, _, _, _, _, _] <: TableInstanceTyped[_, _, _, _, _, _]
  ](plugin: Plugin) =
    TableDef[D,
             NewTableOptsD,
             NewColumnOptsD,
             NewColumnObjectD,
             NewRowD,
             NewCellD,
             NewTableStateD,
             NewTableInstanceD,
             Layout
    ](plugins + plugin)

  /**
   * Add sort capabilities to the table via the useSortBy plugin hook.
   */
  def withSort = withFeaturePlugin[
    TableOptsD with UseSortByOptions[D],
    ColumnOptsD with UseSortByColumnOptions[D],
    ColumnObjectD with UseSortByColumnProps[D],
    RowD,
    CellD,
    TableStateD with UseSortByState[D],
    // TableInstanceTyped.With[D,
    //                         TableInstanceD,
    //                         ColumnOptsD with UseSortByColumnOptions[D],
    //                         ColumnObjectD with UseSortByColumnProps[D],
    //                         RowD,
    //                         CellD,
    //                         TableStateD with UseSortByState[D],
    // ]
    TableInstanceD with UseSortByInstanceTyped //[D, RowD]
  ](Plugin.SortBy)

  /**
   * Add capabilities to expand rows of the table via the useExpanded plugin hook.
   */
  def withExpanded = withFeaturePlugin[
    TableOptsD with UseExpandedOptions[D],
    ColumnOptsD,
    ColumnObjectD,
    RowD with UseExpandedRowProps[D],
    CellD,
    TableStateD with UseExpandedState[D],
    TableInstanceD with UseExpandedInstanceProps, //[D],
  ](Plugin.Expanded)

  // When trying to use a more traditional "syntax" package and implicit
  // classes, the compiler seems to somehow loose type information along
  // the way. Putting the syntax here allows resolution to work correctly.
  object syntax {
    implicit class TableOptionOps[Self <: TableOptsD](val table: Self) {

      /**
       * Sets the initial state of the table.
       *
       * The provided setInitialState method takes a Partial[TableState[D]]
       */
      def setInitialStateFull(s: TableState[D]): Self =
        table.setInitialState(s.asInstanceOf[Partial[TableState[D]]])

      /**
       * Sets the row id for the rows of the table based on a function.
       *
       * @param f
       *   A function from the row type to the row id.
       */
      def setRowIdFn(f: D => String): Self =
        table.setGetRowId((originalRow, _, _) => f(originalRow))

      /**
       * Sets the row id for the rows of the table based on a function.
       *
       * @param f
       *   A function from the row type and index to the row id.
       */
      def setRowIdFn(f: (D, Int) => String): Self =
        table.setGetRowId((originalRow, relativeIndex, _) => f(originalRow, relativeIndex.toInt))

      /**
       * Sets the row id for the rows of the table based on a function.
       *
       * @param f
       *   A function from the row type, index and parent to the row id.
       */
      def setRowIdFn(f: (D, Int, js.UndefOr[Row[D]]) => String): Self =
        table.setGetRowId((originalRow, relativeIndex, parent) =>
          f(originalRow, relativeIndex.toInt, parent)
        )
    }

    implicit class ColumnOptionOps[Self <: ColumnOptsD](val col: Self) {

      /**
       * Sets the accessorFunction for the column.
       *
       * @param f
       *   A function from the row type to the column type.
       */
      def setAccessorFn[V](f: D => V): Self =
        col.setAccessorFunction3((data, _, _) => f(data).asInstanceOf[js.Any])

      def setAccessorFn[V](f: (D, Int) => V): Self =
        col.setAccessorFunction3((data, index, _) => f(data, index.toInt).asInstanceOf[js.Any])

      def setAccessorFn[V](f: (D, Int, Data[D]) => V): Self =
        col.setAccessorFunction3((data, index, sub) =>
          f(data, index.toInt, sub).asInstanceOf[js.Any]
        )

      /**
       * Sets the sorting for the column based on a function on the row.
       *
       * @param f
       *   A function from the row type to the target type.
       * @param ordering
       *   An implicit ordering for the target type.
       * @param evidence
       *   Evidence that this column is sortable. (See note)
       *
       * Note: This method is only valid for columns that are sortable via the useSortBy plugin. The
       * compiler was unable to resolve the types if an implicit class requiring
       * UseSortByColumnOptions[D] was used, so I switched to requiring evidence that ColumnOptsD is
       * a subtype of UseSortByColumnOptions[D] and that worked. Unfortunately, requires
       * asInstanceOfs.
       */
      def setSortByRowFn[V](
        f:        D => V
      )(implicit
        ordering: Ordering[V],
        ev:       Self <:< UseSortByColumnOptions[D]
      ): Self = {
        val sbfn: SortByFn[D] = (d1, d2, _, _) =>
          ordering.compare(f(d1.original), f(d2.original)).toDouble
        ev(col).setSortType(sbfn).asInstanceOf[Self]
      }
    }

    implicit class ColumnValueOptionOps[V, Self <: ColumnInterfaceBasedOnValue[_, _]](
      col: Self with (ColumnInterfaceBasedOnValue[D, V])
    ) {
      @scala.inline
      def setCell(value: CellProps[D, V] => VdomNode): Self =
        StObject.set(col,
                     "Cell",
                     value
                       .andThen(_.rawNode): js.Function1[CellProps[D, V], facade.React.Node]
        )

      // Next 4 methods just copied from ColumnInterfaceBasedOnValueMutableBuilder, which lacks the function overload above.
      @scala.inline
      def setCell(value: Renderer[CellProps[D, V]]): Self =
        StObject.set(col, "Cell", value.asInstanceOf[js.Any])

      @scala.inline
      def setCellComponentClass(value: ComponentClassP[(CellProps[D, V]) with js.Object]): Self =
        StObject.set(col, "Cell", value.asInstanceOf[js.Any])

      @scala.inline
      def setCellUndefined: Self = StObject.set(col, "Cell", js.undefined)

      @scala.inline
      def setCellVdomElement(value: VdomElement): Self =
        StObject.set(col, "Cell", value.rawElement.asInstanceOf[js.Any])

      /**
       * Sets the sorting for the column based on a function on its value.
       *
       * @param f
       *   A function from the value type to the target type.
       * @param ordering
       *   An implicit ordering for the target type.
       * @param evidence
       *   Evidence that this column is sortable.
       */
      def setSortByFn[U](f: V => U)(implicit
        ordering:           Ordering[U],
        ev:                 Self <:< UseSortByColumnOptions[D]
      ): Self = {
        val sbfn: SortByFn[D] = (d1, d2, col, _) =>
          ordering
            .compare(f(d1.values(col.asInstanceOf[String]).asInstanceOf[CellValue[V]]),
                     f(d2.values(col.asInstanceOf[String]).asInstanceOf[CellValue[V]])
            )
            .toDouble
        ev(col).setSortType(sbfn).asInstanceOf[Self]
      }

      /**
       * Sets the sorting for the column based on its value.
       *
       * @param ordering
       *   An implicit ordering for the value type.
       * @param evidence
       *   Evidence that this column is sortable.
       */
      def setSortByAuto(implicit
        ordering: Ordering[V],
        ev:       Self <:< UseSortByColumnOptions[D]
      ): Self = setSortByFn(identity)
    }
  }
}

object TableDef {
  def apply[D]: TableDef[ // format: off
    D,
    UseTableOptions[D],
    ColumnOptions[D],
    ColumnObject[D],
    Row[D],
    Cell[D, js.Any],
    TableState[D],
    TableInstanceTyped,
    Layout.Table
  ] = TableDef[ // format: off
    D,
    UseTableOptions[D],
    ColumnOptions[D],
    ColumnObject[D],
    Row[D],
    Cell[D, js.Any],
    TableState[D],
    TableInstanceTyped,
    Layout.Table
  ](Set.empty)// format: on

  def headersFromGroup[D, ColumnD <: Column[D]](headerGroup: HeaderGroup[D]) =
    headerGroup.headers.asInstanceOf[js.Array[ColumnD]]

  // format: off
  implicit class TableLayoutTableDefOps[D,
      TableOptsD <: UseTableOptions[D],
      ColumnOptsD <: ColumnOptions[D],
      ColumnObjectD <: ColumnObject[D],
      RowD <: Row[D],
      CellD <: Cell[D, js.Any],
      TableStateD <: TableState[D],
      TableInstanceD[_, _, _, _, _, _] <: TableInstanceTyped[_, _, _, _, _, _],
    ] (val tableDef: TableDef[D, 
          TableOptsD, 
          ColumnOptsD,
          ColumnObjectD,
          RowD,
          CellD,
          TableStateD,
          TableInstanceD,
          Layout.Table]) extends AnyVal { // format: on
    private def withLayoutPlugin[
      NewTableOptsD <: UseTableOptions[D],
      NewColumnOptsD <: ColumnOptions[D],
      NewColumnObjectD <: ColumnObject[D],
      NewRowD <: Row[D],
      NewCellD <: Cell[D, js.Any],
      NewState <: TableState[D],
      NewTableInstanceD[_, _, _, _, _, _] <: TableInstanceTyped[_, _, _, _, _, _]
    ](plugin: Plugin) =
      TableDef[D,
               NewTableOptsD,
               NewColumnOptsD,
               NewColumnObjectD,
               NewRowD,
               NewCellD,
               NewState,
               NewTableInstanceD,
               Layout.NonTable
      ](tableDef.plugins + plugin)

    /**
     * Adds support for headers and cells to be rendered as inline-block divs (or other non-table
     * elements) with explicit width. This becomes useful if and when you need to virtualize rows
     * and cells for performance.
     *
     * NOTE: Although no additional options are needed for this plugin to work, the core column
     * options width, minWidth and maxWidth are used to calculate column and cell widths and must be
     * set.
     */
    def withBlockLayout = withLayoutPlugin[
      TableOptsD,
      ColumnOptsD,
      ColumnObjectD,
      RowD,
      CellD,
      TableStateD,
      TableInstanceD,
    ](Plugin.BlockLayout)

    /**
     * Adds support for headers and cells to be rendered as divs (or other non-table elements) with
     * the immediate parent (table) controlling the layout using CSS Grid. This hook becomes useful
     * when implementing both virtualized and resizable tables that must also be able to stretch to
     * fill all available space. Uses a minimal amount of html to give greater control of styling.
     * Works with useResizeColumns.
     */
    def withGridLayout = withLayoutPlugin[
      TableOptsD,
      ColumnOptsD,
      ColumnObjectD,
      RowD,
      CellD,
      TableStateD,
      TableInstanceD
    ](Plugin.GridLayout)
  }

  // format: off

  implicit class NonTableLayoutTableDefOps[D, 
    TableOptsD <: UseTableOptions[D], 
    ColumnOptsD <: ColumnOptions[D], 
    ColumnObjectD <: ColumnObject[D], 
    RowD <: Row[D],
    CellD <: Cell[D, js.Any],
    TableStateD <: TableState[D],
    TableInstanceD[_, _, _, _, _, _] <: TableInstanceTyped[_, _, _, _, _, _], 
  ]( // format: on
    val tableDef: TableDef[
      D,
      TableOptsD,
      ColumnOptsD,
      ColumnObjectD,
      RowD,
      CellD,
      TableStateD,
      TableInstanceD,
      Layout.NonTable
    ]
  ) extends AnyVal {

    /**
     * Add column resize capabilities to the table via the useResizeColumns plugin hook.
     */
    def withResizeColumns = tableDef.withFeaturePlugin[
      TableOptsD with UseResizeColumnsOptions[D],
      ColumnOptsD with UseResizeColumnsColumnOptions[D],
      ColumnObjectD with UseResizeColumnsColumnProps[D],
      RowD,
      CellD,
      TableStateD with UseResizeColumnsState[D],
      TableInstanceD,
    ](Plugin.ResizeColumns)
  }
  // format: on

}
