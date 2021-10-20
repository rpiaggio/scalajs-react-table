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
import reactST.reactTable.mod.UseTableOptions
import reactST.reactTable.mod.Row
import reactST.reactTable.mod.Cell
import reactST.reactTable.mod.TableState
import reactST.reactTable.mod.ColumnInterface
// import reactST.reactTable.mod.Column
import reactST.reactTable.mod.ColumnGroup
import reactST.reactTable.mod.UseSortByColumnOptions
import reactST.reactTable.mod.UseSortByOptions
import reactST.reactTable.mod.UseSortByState
import reactST.reactTable.mod.UseExpandedOptions
import reactST.reactTable.mod.UseExpandedInstanceProps
import reactST.reactTable.mod.UseExpandedRowProps
import reactST.reactTable.mod.UseExpandedState
import reactST.reactTable.mod.SortByFn
import reactST.reactTable.mod.ColumnInterfaceBasedOnValue
import reactST.reactTable.mod.CellProps
import reactST.reactTable.mod.Renderer
import reactST.reactTable.mod.CellValue
import reactST.reactTable.mod.HeaderGroup
import reactST.reactTable.mod.UseResizeColumnsState
import reactST.reactTable.mod.UseResizeColumnsColumnOptions
import reactST.reactTable.mod.UseResizeColumnsOptions
import reactST.std.Partial

import scalajs.js
import scalajs.js.|
import scalajs.js.JSConverters._
// import reactST.reactTable.facade.column.ColumnOptions

case class TableDefWithOptions[ // format: off
  D,
  TableOptsD <: UseTableOptions[D],
  TableInstanceType[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
  ColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: ColumnOptions[d, v, col, row, cell, s],
  ColumnType[d] <: Column[d],
  RowD <: Row[D],
  CellType[d, v] <: Cell[d, v],
  StateTypeD <: TableState[D],
  Layout // format: on
](
  tableDef: TableDef[
    D,
    TableOptsD,
    TableInstanceType,
    ColumnOptsType,
    ColumnType,
    RowD,
    CellType,
    StateTypeD,
    Layout
  ],
  cols:     Reusable[List[ColumnInterface[D]]],
  data:     Reusable[List[D]],
  modOpts:  Reusable[TableOptsD => TableOptsD]
)

case class TableDef[ // format: off
  D,
  TableOptsD <: UseTableOptions[D],
  TableInstanceType[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
  ColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: ColumnOptions[d, v, col, row, cell, s],
  ColumnType[d] <: Column[d],
  RowD <: Row[D],
  CellType[d, v] <: Cell[d, v],
  StateTypeD <: TableState[D],
  Layout // format: on
](plugins: Set[Plugin]) {
  object Type {
    type Options          = TableOptsD
    type Instance         = TableInstanceType[D, ColumnType, RowD, CellType, StateTypeD]
    type ColumnOptions[V] = ColumnOptsType[D, V, ColumnType, RowD, CellType, StateTypeD]
    type Column           = ColumnType[D]
    type Row              = RowD
    type Cell[V]          = CellType[D, V]
    type State            = StateTypeD
  }

  import syntax._

  def apply(
    cols:    Reusable[List[ColumnInterface[D]]],
    data:    Reusable[List[D]],
    modOpts: Reusable[TableOptsD => TableOptsD] = Reusable.always(identity[TableOptsD] _)
  ): TableDefWithOptions[
    D,
    TableOptsD,
    TableInstanceType,
    ColumnOptsType,
    ColumnType,
    RowD,
    CellType,
    StateTypeD,
    Layout
  ] =
    TableDefWithOptions(this, cols, data, modOpts)

  private def emptyOptions: TableOptsD = js.Dynamic.literal().asInstanceOf[TableOptsD]

  /**
   * Create a TableOptsD. columns and data are required. Other options can be `set*`.
   */
  protected[reactTable] def Options(
    columns: js.Array[Type.ColumnOptions[js.Any]],
    data:    js.Array[D]
  ): TableOptsD =
    emptyOptions
      .setColumns(columns.asInstanceOf[js.Array[reactST.reactTable.mod.Column[D]]])
      .setData(data)

  /**
   * Create an empty instance of ColumnOptsD. As per react-table's doc: Warning: Only omit accessor
   * if you really know what you're doing.
   */
  def emptyColumn[V]: Type.ColumnOptions[V] =
    js.Dynamic.literal().asInstanceOf[Type.ColumnOptions[V]]

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor string.
   */
  def Column[V](accessor: String): Type.ColumnOptions[V] =
    // reactST.reactTable.facade.columnOptions.ColumnOptions
    //   .ColumnOptionsMutableBuilder(emptyColumn[V])
    //   .setAccessor(accessor)
    emptyColumn[V].setAccessor(accessor)

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   */
  def Column[V](id: String, accessor: D => V): Type.ColumnOptions[V] =
    Column(id, (d, _, _) => accessor(d))

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   */
  def Column[V](id: String, accessor: (D, Int) => V): Type.ColumnOptions[V] =
    Column(id, (d, index, _) => accessor(d, index))

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   */
  def Column[V](
    id:       String,
    accessor: (D, Int, Data[D]) => V
  ): Type.ColumnOptions[V] =
    emptyColumn
      .setId(id)
      .setAccessor(
        (
          (
            d:     D,
            index: Double,
            sub:   reactST.reactTable.anon.Data[D]
          ) => accessor(d, index.toInt, sub).asInstanceOf[js.Any]
        ): js.Function3[D, Double, reactST.reactTable.anon.Data[D], js.Any]
      )

  /**
   * Create a column group with the specified columns in it.
   *
   * @param header
   *   The header for the column group. Seems to be required.
   * @param cols
   *   The columns to include in the group.
   */
  def ColumnGroup(cols: (ColumnGroup[D] | Type.ColumnOptions[js.Any])*): ColumnGroupOptions[D] =
    js.Dynamic
      .literal()
      .asInstanceOf[ColumnGroupOptions[D]]
      .setColumns(cols.toJSArray.asInstanceOf[js.Array[reactST.reactTable.mod.Column[D]]])

  /**
   * Create an empty instance of type StateType
   */
  def State(): StateTypeD = js.Dynamic.literal().asInstanceOf[StateTypeD]

  /*
   * When adding new plugins, see https://github.com/DefinitelyTyped/DefinitelyTyped/tree/master/types/react-table
   * for help determining what the necessary type changes are.
   *
   * Hooks need to have facades created in TableHooks.scala, as the facades created by
   * ScalablyTyped won't work as hooks.
   */

  protected[reactST] def withFeaturePlugin[ // format: off
    NewTableOptsD <: UseTableOptions[D],
    NewTableInstanceD[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
    NewColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: ColumnOptions[d, v, col, row, cell, s],
    NewColumnType[d] <: Column[d],
    NewRowD <: Row[D],
    NewCellType[d, v] <: Cell[d, v],
    NewStateTypeD <: TableState[D] // format: on
  ](plugin: Plugin) =
    TableDef[D,
             NewTableOptsD,
             NewTableInstanceD,
             NewColumnOptsType,
             NewColumnType,
             NewRowD,
             NewCellType,
             NewStateTypeD,
             Layout
    ](plugins + plugin)

  /**
   * Add sort capabilities to the table via the useSortBy plugin hook.
   */
  def withSort = withFeaturePlugin[
    TableOptsD with UseSortByOptions[D],
    TableInstanceType with UseSortByTableInstance,
    ColumnOptsType with UseSortByColumnOptions,
    ColumnType with UseSortByColumnOptions,
    RowD,
    CellType,
    StateTypeD with UseSortByState[D]
  ](Plugin.SortBy)

  /**
   * Add capabilities to expand RowTypes of the table via the useExpanded plugin hook.
   */
  def withExpanded = withFeaturePlugin[
    TableOptsD with UseExpandedOptions[D],
    TableInstanceType with UseExpandedTableInstance,
    ColumnOptsType,
    ColumnType,
    RowD with UseExpandedRowProps[D],
    CellType,
    StateTypeD with UseExpandedState[D]
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

    implicit class ColumnOptionOps[Self <: Type.ColumnOptions[_]](val col: Self) {

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
      def setSortByRowTypeFn[V](
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
    TableInstance,
    ColumnOptions[D],
    ColumnOptions,
    RowType[D],
    Cell,
    TableState[D],
    Layout.Table
  ] = TableDef[
    D,
    UseTableOptions[D],
    TableInstance,
    ColumnOptions[D],
    ColumnOptions,
    RowType[D],
    Cell,
    TableState[D],
    Layout.Table
  ](Set.empty)// format: on

  // format: off
  implicit class TableLayoutTableDefOps[ // format: off
      D,
      TableOptsD <: UseTableOptions[D],
      TableInstanceD[d, co, col[d0, co0, RowType0, cell0[d0, v], s0], RowType, cell[d0, v], s] <: TableInstance[d, co, col, RowType, cell, s],
      ColumnOptsD <: ColumnOptions[D],
      ColumnType[d, co, RowType, cell[d0, v], s] <: ColumnOptions[d, co, RowType, cell, s],
      RowTypeD <: RowType[D],
      CellType[d, v] <: Cell[d, v],
      StateType <: TableState[D]
  ](
    val tableDef: TableDef[D,
                           TableOptsD,
                           TableInstanceD,
                           ColumnOptsD,
                           ColumnType,
                           RowTypeD,
                           CellType,
                           StateType,
                           Layout.Table
    ]
  ) extends AnyVal { 
    private def withLayoutPlugin[
      NewTableOptsD <: UseTableOptions[D],
      NewTableInstanceD[d, co, col[d0, co0, RowType0, cell0[d0, v], s0], RowType, cell[d0, v], s] <: 
        TableInstance[d, co, col, RowType, cell, s],
      NewColumnOptsD <: ColumnOptions[D],
      NewColumnType[d, co, RowType, cell[d0, v], s] <: ColumnOptions[d, co, RowType, cell, s],
      NewRowTypeD <: RowType[D],
      NewCellType[d, v] <: Cell[d, v],
      NewState <: TableState[D] // format: on
    ](plugin: Plugin) =
      TableDef[D,
               NewTableOptsD,
               NewTableInstanceD,
               NewColumnOptsD,
               NewColumnType,
               NewRowTypeD,
               NewCellType,
               NewState,
               Layout.NonTable
      ](tableDef.plugins + plugin)

    /**
     * Adds support for headers and cells to be rendered as inline-block divs (or other non-table
     * elements) with explicit width. This becomes useful if and when you need to virtualize
     * RowTypes and cells for performance.
     *
     * NOTE: Although no additional options are needed for this plugin to work, the core column
     * options width, minWidth and maxWidth are used to calculate column and cell widths and must be
     * set.
     */
    def withBlockLayout = withLayoutPlugin[
      TableOptsD,
      TableInstanceD,
      ColumnOptsD,
      ColumnType,
      RowTypeD,
      CellType,
      StateType,
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
      TableInstanceD,
      ColumnOptsD,
      ColumnType,
      RowTypeD,
      CellType,
      StateType,
    ](Plugin.GridLayout)
  }

  implicit class NonTableLayoutTableDefOps[ // format: off
    D, 
    TableOptsD <: UseTableOptions[D], 
    TableInstanceD[d, co, col[d0, co0, RowType0, cell0[d0, v], s0], RowType, cell[d0, v], s] <: 
      TableInstance[d, co, col, RowType, cell, s],
    ColumnOptsD <: ColumnOptions[D],
    ColumnType[d, co, RowType, cell[d0, v], s] <: ColumnOptions[d, co, RowType, cell, s],
    RowTypeD <: RowType[D],
    CellType[d, v] <: Cell[d, v],
    StateType <: TableState[D] // format: on
  ](
    val tableDef: TableDef[
      D,
      TableOptsD,
      TableInstanceD,
      ColumnOptsD,
      ColumnType,
      RowTypeD,
      CellType,
      StateType,
      Layout.NonTable
    ]
  ) extends AnyVal {

    /**
     * Add column resize capabilities to the table via the useResizeColumns plugin hook.
     */
    def withResizeColumns = tableDef.withFeaturePlugin[
      TableOptsD with UseResizeColumnsOptions[D],
      TableInstanceD,
      ColumnOptsD with UseResizeColumnsColumnOptions[D],
      ColumnType with UseResizeColumnsColumnOptions,
      RowTypeD,
      CellType,
      StateType with UseResizeColumnsState[D],
    ](Plugin.ResizeColumns)
  }
  // format: on

}
