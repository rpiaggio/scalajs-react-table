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
// import reactST.reactTable.mod.Cell
import reactST.reactTable.mod.TableState
import reactST.reactTable.mod.ColumnInterface
import reactST.reactTable.mod.ColumnGroup
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
// import reactST.reactTable.mod.UseResizeColumnsColumnOptions
import reactST.reactTable.mod.UseResizeColumnsOptions
import reactST.std.Partial

import scalajs.js
import scalajs.js.|
import scalajs.js.JSConverters._

case class TableDefWithOptions[ // format: off
  D,
  TableOptsD <: UseTableOptions[D],
  TableInstanceType[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
  ColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
  ColumnD <: Column[D],
  RowD <: Row[D],
  CellType[d, col, row] <: Cell[d, col, row],
  TableStateD <: TableState[D],
  Layout // format: on
](
  tableDef: TableDef[
    D,
    TableOptsD,
    TableInstanceType,
    ColumnOptsType,
    ColumnD,
    RowD,
    CellType,
    TableStateD,
    Layout
  ],
  cols:     Reusable[List[ColumnOptsType[D, ColumnD, RowD, CellType, TableStateD]]],
  data:     Reusable[List[D]],
  modOpts:  Reusable[TableOptsD => TableOptsD]
)

case class TableDef[ // format: off
  D,
  TableOptsD <: UseTableOptions[D],
  TableInstanceType[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
  ColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
  ColumnD <: Column[D],
  RowD <: Row[D],
  CellType[d, col, row] <: Cell[d, col, row],
  TableStateD <: TableState[D],
  Layout // format: on
](plugins: Set[Plugin]) {
  object Type {
    type Options                                       = TableOptsD
    type InstanceC[d, col, row, cell[_, _, _], s]      = TableInstanceType[d, col, row, cell, s]
    type Instance                                      = InstanceC[D, ColumnD, RowD, CellType, TableStateD]
    type ColumnOptionsC[d, col, row, cell[_, _, _], s] = ColumnOptsType[d, col, row, cell, s]
    type ColumnOptions                                 = ColumnOptionsC[D, ColumnD, RowD, CellType, TableStateD]
    type ColumnValueOptions[V]                         =
      ColumnOptionsV[D, V, ColumnD, RowD, CellType, TableStateD, ColumnOptions]
    type ColumnGroupOptions                            =
      ColumnOptsType[D, ColumnD, RowD, CellType, TableStateD] // TODO Proper col group type
    type Column             = ColumnD
    type Row                = RowD
    type CellC[d, col, row] = CellType[d, col, row]
    type Cell               = CellC[D, ColumnD, RowD]
    type CellValue[V]       = Cell with reactST.reactTable.facade.cell.CellValue[V]
    type TableState         = TableStateD
  }

  import syntax._

  def apply(
    cols:    Reusable[List[Type.ColumnOptions]],
    data:    Reusable[List[D]],
    modOpts: Reusable[TableOptsD => TableOptsD] = Reusable.always(identity[TableOptsD] _)
  ): TableDefWithOptions[
    D,
    TableOptsD,
    TableInstanceType,
    ColumnOptsType,
    ColumnD,
    RowD,
    CellType,
    TableStateD,
    Layout
  ] =
    TableDefWithOptions(this, cols, data, modOpts)

  private def emptyOptions: TableOptsD = js.Dynamic.literal().asInstanceOf[TableOptsD]

  /**
   * Create a TableOptsD. columns and data are required. Other options can be `set*`.
   */
  protected[reactTable] def Options(
    columns: js.Array[Type.ColumnOptions],
    data:    js.Array[D]
  ): TableOptsD =
    emptyOptions
      .setColumns(columns.asInstanceOf[js.Array[reactST.reactTable.mod.Column[D]]])
      .setData(data)

  /**
   * Create an empty instance of ColumnOptsD. As per react-table's doc: Warning: Only omit accessor
   * if you really know what you're doing.
   */
//   def emptyColumn[V]: Type.ColumnOptions[V] =
//     js.Dynamic.literal().asInstanceOf[Type.ColumnOptions[V]]

// type ColumnValueOptions[d, v, col, row, cell, s, Col <: ColumnOptions[d, col, row, cell, s]] =
//   Col with facade.columnOptions.ColumnValueOptions[d, v, col, row, cell, s]

  def emptyColumn[V]: Type.ColumnValueOptions[V] =
    js.Dynamic.literal().asInstanceOf[Type.ColumnValueOptions[V]]

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor string.
   */
  def Column[V](accessor: String): Type.ColumnValueOptions[V] =
    emptyColumn[V].setAccessor(accessor)

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   */
  def Column[V](
    id:       String,
    accessor: D => V
  ): Type.ColumnValueOptions[V] =
    Column(id, (d, _, _) => accessor(d))

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   */
  def Column[V](id: String, accessor: (D, Int) => V): Type.ColumnValueOptions[V] =
    Column(id, (d, index, _) => accessor(d, index))

  /**
   * Create a ColumnOptsD setup up for a simple column with an accessor function.
   */
  def Column[V](
    id:       String,
    accessor: (D, Int, Data[D]) => V
  ): Type.ColumnValueOptions[V] =
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
  def ColumnGroup(
    cols: (Type.ColumnGroupOptions | Type.ColumnOptions)*
  ): Type.ColumnGroupOptions =
    js.Dynamic
      .literal()
      .asInstanceOf[Type.ColumnGroupOptions]
      .setColumns(
        cols.toJSArray
          .asInstanceOf[js.Array[ColumnOptions[D, ColumnD, RowD, CellType, TableStateD]]]
      )

  /**
   * Create an empty instance of type StateType
   */
  def State(): TableStateD = js.Dynamic.literal().asInstanceOf[TableStateD]

  /*
   * When adding new plugins, see https://github.com/DefinitelyTyped/DefinitelyTyped/tree/master/types/react-table
   * for help determining what the necessary type changes are.
   *
   * Hooks need to have facades created in TableHooks.scala, as the facades created by
   * ScalablyTyped won't work as hooks.
   */

  protected[reactST] def withFeaturePlugin[ // format: off
    NewTableOptsD <: UseTableOptions[D],
    NewTableInstanceD[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
    NewColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
    NewColumnD <: Column[D],
    NewRowD <: Row[D],
    NewCellType[d, col, row] <: Cell[d, col, row],
    NewTableStateD <: TableState[D] // format: on
  ](plugin: Plugin) =
    TableDef[D,
             NewTableOptsD,
             NewTableInstanceD,
             NewColumnOptsType,
             NewColumnD,
             NewRowD,
             NewCellType,
             NewTableStateD,
             Layout
    ](plugins + plugin)

  protected[reactST] def withLayoutPlugin[
    NewTableOptsD <: UseTableOptions[D], // format: off
      NewTableInstanceType[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
      NewColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
      NewColumnD <: Column[D],
      NewRowD <: Row[D],
      NewCellType[d, col, row] <: Cell[d, col, row],
      NewTableStateD <: TableState[D] // format: on
  ](plugin: Plugin) =
    TableDef[D,
             NewTableOptsD,
             NewTableInstanceType,
             NewColumnOptsType,
             NewColumnD,
             NewRowD,
             NewCellType,
             NewTableStateD,
             Layout.NonTable
    ](plugins + plugin)

  /**
   * Add sort capabilities to the table via the useSortBy plugin hook.
   */
  def withSortBy = withFeaturePlugin[
    TableOptsD with UseSortByOptions[D],
    TableInstanceType with UseSortByTableInstance,
    ColumnOptsType with UseSortByColumnOptions,
    ColumnD with UseSortByColumn[D],
    RowD,
    CellType,
    TableStateD with UseSortByState[D]
  ](Plugin.SortBy)

  // only nontable
  def withResizeColumns = withFeaturePlugin[
    TableOptsD with UseResizeColumnsOptions[D],
    TableInstanceType,
    ColumnOptsType with UseResizeColumnsColumnOptions,
    ColumnD with UseResizeColumnsColumn[D],
    RowD,
    CellType,
    TableStateD with UseResizeColumnsState[D]
  ](Plugin.ResizeColumns)

  /**
   * Add capabilities to expand rows of the table via the useExpanded plugin hook.
   */
  def withExpanded = withFeaturePlugin[
    TableOptsD with UseExpandedOptions[D],
    TableInstanceType with UseExpandedTableInstance,
    ColumnOptsType,
    ColumnD,
    RowD with UseExpandedRowProps[D],
    CellType,
    TableStateD with UseExpandedState[D]
  ](Plugin.Expanded)

  // only table
  def withBlockLayout = withLayoutPlugin[
    TableOptsD,
    TableInstanceType,
    ColumnOptsType,
    ColumnD,
    RowD,
    CellType,
    TableStateD
  ](Plugin.BlockLayout)

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

    implicit class ColumnOptionOps[Self <: Type.ColumnOptions](val col: Self) {

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
      // def setSortByRowFn[V](
      //   f:        D => V
      // )(implicit
      //   ordering: Ordering[V],
      //   ev:       Self <:< UseSortByColumnOptions[D]
      // ): Self = {
      //   val sbfn: SortByFn[D] = (d1, d2, _, _) =>
      //     ordering.compare(f(d1.original), f(d2.original)).toDouble
      //   ev(col).setSortType(sbfn).asInstanceOf[Self]
      // }
    }

  }
}

object TableDef {
  def apply[D]: TableDef[ // format: off
    D,
    UseTableOptions[D],
    TableInstance,
    ColumnOptions,
    Column[D],
    Row[D],
    Cell,
    TableState[D],
    Layout.Table
  ] = TableDef[
    D,
    UseTableOptions[D],
    TableInstance,
    ColumnOptions,
    Column[D],
    Row[D],
    Cell,
    TableState[D],
    Layout.Table
  ](Set.empty)// format: on

  // format: off
  implicit class TableLayoutTableDefOps[ // format: off
    D,
    TableOptsD <: UseTableOptions[D],
    TableInstanceType[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
    ColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
    ColumnD <: Column[D],
    RowD <: Row[D],
    CellType[d, col, row] <: Cell[d, col, row],
    TableStateD <: TableState[D],
  ](
    val tableDef: TableDef[
      D,
      TableOptsD,
      TableInstanceType,
      ColumnOptsType,
      ColumnD,
      RowD,
      CellType,
      TableStateD,
      Layout.Table
  ]) extends AnyVal { 
    private def withLayoutPlugin[
      NewTableOptsD <: UseTableOptions[D],
      NewTableInstanceType[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
      NewColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
      NewColumnD <: Column[D],
      NewRowD <: Row[D],
      NewCellType[d, col, row] <: Cell[d, col, row],
      NewTableStateD <: TableState[D] // format: on
    ](plugin: Plugin) =
      TableDef[D,
               NewTableOptsD,
               NewTableInstanceType,
               NewColumnOptsType,
               NewColumnD,
               NewRowD,
               NewCellType,
               NewTableStateD,
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
    // def withBlockLayout = withLayoutPlugin[
    //   TableOptsD,
    //   TableInstanceType,
    //   ColumnOptsType,
    //   ColumnD,
    //   RowD,
    //   CellType,
    //   TableStateD
    // ](Plugin.BlockLayout)

    /**
     * Adds support for headers and cells to be rendered as divs (or other non-table elements) with
     * the immediate parent (table) controlling the layout using CSS Grid. This hook becomes useful
     * when implementing both virtualized and resizable tables that must also be able to stretch to
     * fill all available space. Uses a minimal amount of html to give greater control of styling.
     * Works with useResizeColumns.
     */
    def withGridLayout = withLayoutPlugin[
      TableOptsD,
      TableInstanceType,
      ColumnOptsType,
      ColumnD,
      RowD,
      CellType,
      TableStateD
    ](Plugin.GridLayout)
  }

  // implicit class NonTableLayoutTableDefOps[ // format: off
  //   D,
  //   TableOptsD <: UseTableOptions[D],
  //   TableInstanceType[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
  //   ColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
  //   ColumnD <: Column[D],
  //   RowD <: Row[D],
  //   CellType[d, col, row] <: Cell[d, col, row],
  //   TableStateD <: TableState[D] // format: on
  // ](
  //   val tableDef: TableDef[
  //     D,
  //     TableOptsD,
  //     TableInstanceType,
  //     ColumnOptsType,
  //     ColumnD,
  //     RowD,
  //     CellType,
  //     TableStateD,
  //     Layout.NonTable
  //   ]
  // ) extends AnyVal {

  //   /**
  //    * Add column resize capabilities to the table via the useResizeColumns plugin hook.
  //    */
  //   def withResizeColumns = tableDef.withFeaturePlugin[
  //     TableOptsD with UseResizeColumnsOptions[D],
  //     TableInstanceType,
  //     ColumnOptsType with UseResizeColumnsColumnOptions,
  //     ColumnD with UseResizeColumnsColumn[D],
  //     RowD,
  //     CellType,
  //     TableStateD with UseResizeColumnsState[D]
  //   ](Plugin.ResizeColumns)
  // }
  // format: on

}
