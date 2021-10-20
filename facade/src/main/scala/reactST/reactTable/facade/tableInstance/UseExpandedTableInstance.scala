package reactST.reactTable.facade.tableInstance

import reactST.reactTable.mod.IdType

import scala.scalajs.js

@js.native
trait UseExpandedTableInstance[
  D, // format: off
  ColumnType[d],
  RowType,
  CellType[d, v],
  StateType // format: on
] extends TableInstance[D, ColumnType, RowType, CellType, StateType] {
  var expandedDepth: Double = js.native

  var expandedRowTypes: js.Array[RowType] = js.native

  var isAllRowTypesExpanded: Boolean = js.native

  var preExpandedRowTypes: js.Array[RowType] = js.native

  def toggleAllRowTypesExpanded(): Unit               = js.native
  def toggleAllRowTypesExpanded(value: Boolean): Unit = js.native

  def toggleRowTypeExpanded(id: js.Array[IdType[D]]): Unit                 = js.native
  def toggleRowTypeExpanded(id: js.Array[IdType[D]], value: Boolean): Unit = js.native
}
