package reactST.reactTable

import reactST.reactTable.anon.PartialTableToggleHideAll
import reactST.reactTable.mod._
import reactST.std.Partial
import reactST.std.Record

import scalajs.js

object TableInstanceTyped {
  // format: off
  type With[D, 
    ColumnOptsD <: ColumnOptions[D], 
    ColumnObjectD <: ColumnObject[D], 
    RowD <: Row[D],
    CellD <: Cell[D, js.Any],
    TableStateD <: TableState[D],
    TableInstanceTypedD[_,_, _, _, _, _] <: TableInstanceTyped[_, _, _,_, _, _], //<: TableInstanceTyped[D, COD, CID, RD, CD, SD],
  ] = 
    TableInstanceTypedD[
      D,
      ColumnOptsD, 
      ColumnObjectD,
      RowD,
      CellD,
      TableStateD
    ]
  // format: on
}

// Copied from package reactST.reactTable.mod.TableInstance and modified
// to add specific typing. Ideally we would extend and override, but we
// cannot override vars in Scala (and everything's a var in the generated code).
@js.native // format: off
trait TableInstanceTyped[D, 
  ColumnOptsD, // <: ColumnOptions[D],  
  ColumnObjectD, //<: ColumnObject[D], 
  RowD, // <: Row[D], 
  CellD, //<: Cell[D, js.Any],
  TableStateD// <: TableState[D]
] extends js.Object { // format: on
  // type With[NewColumnOptsD, NewColumnObjectD, NewRowD, NewCellD, NewTableStateD] =
  //   TableInstanceTyped[D, NewColumnOptsD, NewColumnObjectD, NewRowD, NewCellD, NewTableStateD]

  val allColumns: js.Array[ColumnObjectD] = js.native

  val allColumnsHidden: Boolean = js.native

  val autoResetHiddenColumns: js.UndefOr[Boolean] = js.native

  val columns: js.Array[ColumnObjectD] = js.native

  val data: js.Array[D] = js.native

  val defaultColumn: js.UndefOr[Partial[ColumnOptsD]] = js.native

  val dispatch: TableDispatch[js.Any] = js.native

  val flatHeaders: js.Array[ColumnObjectD] = js.native

  val flatRows: js.Array[RowD] = js.native

  val footerGroups: js.Array[HeaderGroup[D]] = js.native

  def getHooks(): Hooks[D] = js.native

  val getRowId: js.UndefOr[
    js.Function3[
      /* originalRow */ D,
      /* relativeIndex */ Double,
      /* parent */ js.UndefOr[RowD],
      String
    ]
  ] = js.native

  val getSubRows
    : js.UndefOr[js.Function2[ /* originalRow */ D, /* relativeIndex */ Double, js.Array[D]]] =
    js.native

  def getTableBodyProps(): TableBodyProps                                   = js.native
  def getTableBodyProps(propGetter: TableBodyPropGetter[D]): TableBodyProps = js.native

  def getTableProps(): TableProps                               = js.native
  def getTableProps(propGetter: TablePropGetter[D]): TableProps = js.native

  def getToggleHideAllColumnsProps(): TableToggleHideAllColumnProps = js.native
  def getToggleHideAllColumnsProps(
    props: PartialTableToggleHideAll
  ): TableToggleHideAllColumnProps = js.native

  val headerGroups: js.Array[HeaderGroup[D]] = js.native

  val headers: js.Array[ColumnObjectD] = js.native

  val initialState: js.UndefOr[Partial[TableStateD]] = js.native

  val plugins: js.Array[PluginHook[D]] = js.native

  def prepareRow(row: RowD): Unit = js.native

  val rows: js.Array[RowD] = js.native

  val rowsById: Record[String, RowD] = js.native

  def setHiddenColumns(param: js.Array[IdType[D]]): Unit    = js.native
  def setHiddenColumns(param: UpdateHiddenColumns[D]): Unit = js.native

  val state: TableStateD = js.native

  val stateReducer: js.UndefOr[
    js.Function4[
      /* newState */ TableStateD,
      /* action */ ActionType,
      /* previousState */ TableStateD,
      /* instance */ js.UndefOr[
        TableInstanceTyped[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD]
      ],
      TableState[D]
    ]
  ] = js.native

  def toggleHideAllColumns(): Unit               = js.native
  def toggleHideAllColumns(value: Boolean): Unit = js.native

  def toggleHideColumn(columnId: IdType[D]): Unit                 = js.native
  def toggleHideColumn(columnId: IdType[D], value: Boolean): Unit = js.native

  val totalColumnsWidth: Double = js.native

  val useControlledState: js.UndefOr[
    js.Function2[
      /* state */ TableStateD,
      /* meta */ Meta[D, scala.Nothing, MetaBase[D]],
      TableState[D]
    ]
  ] = js.native

  val visibleColumns: js.Array[ColumnObjectD] = js.native
}
