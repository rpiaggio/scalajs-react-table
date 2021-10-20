package reactST.reactTable.facade.tableInstance

import reactST.reactTable.anon.PartialTableToggleHideAll
import reactST.reactTable.mod.{ HeaderGroup => _, _ }
import reactST.std.Partial
import reactST.std.Record
import reactST.reactTable.facade.headerGroup.HeaderGroup

import scalajs.js

// Copied from package reactST.reactTable.mod.TableInstance and modified
// to add specific typing. Ideally we would extend and override, but we
// cannot override vars in Scala (and everything's a var in the generated code).
@js.native
trait TableInstance[
  D, // format: off
  ColumnType[d],
  RowType,
  CellType[d, v],
  StateType // format: on
] extends js.Object {
  val allColumns: js.Array[ColumnType[D]] = js.native

  val allColumnsHidden: Boolean = js.native

  val autoResetHiddenColumns: js.UndefOr[Boolean] = js.native

  val columns: js.Array[ColumnType[D]] = js.native

  val data: js.Array[D] = js.native

  // val defaultColumn: js.UndefOr[Partial[ColumnOptsD]] = js.native

  val dispatch: TableDispatch[js.Any] = js.native

  val flatHeaders: js.Array[ColumnType[D]] = js.native

  val flatRows: js.Array[RowType] = js.native

  // FIXME HEaderGroups new one
  val footerGroups: js.Array[HeaderGroup[D, ColumnType[D]]] =
    js.native

  def getHooks(): Hooks[D] = js.native

  val getRowId: js.UndefOr[
    js.Function3[
      /* originalRow */ D,
      /* relativeIndex */ Double,
      /* parent */ js.UndefOr[RowType],
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

  val headerGroups: js.Array[HeaderGroup[D, ColumnType[D]]] =
    js.native

  val headers: js.Array[ColumnType[D]] = js.native

  val initialState: js.UndefOr[Partial[StateType]] = js.native

  val plugins: js.Array[PluginHook[D]] = js.native

  def prepareRow(row: RowType): Unit = js.native

  val rows: js.Array[RowType] = js.native

  val rowsById: Record[String, RowType] = js.native

  def setHiddenColumns(param: js.Array[IdType[D]]): Unit    = js.native
  def setHiddenColumns(param: UpdateHiddenColumns[D]): Unit = js.native

  val state: StateType = js.native

  val stateReducer: js.UndefOr[
    js.Function4[
      /* newState */ StateType,
      /* action */ ActionType,
      /* previousState */ StateType,
      /* instance */ js.UndefOr[
        TableInstance[D, ColumnType, RowType, CellType, StateType]
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
      /* state */ StateType,
      /* meta */ Meta[D, scala.Nothing, MetaBase[D]],
      TableState[D]
    ]
  ] = js.native

  val visibleColumns: js.Array[ColumnType[D]] = js.native
}
