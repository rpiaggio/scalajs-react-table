package reactST.reactTable.facade.tableInstance

import reactST.reactTable.mod.IdType

import scala.scalajs.js

@js.native
trait UseExpandedTableInstance[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD]
    extends TableInstance[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD] {

  var expandedDepth: Double = js.native

  var expandedRows: js.Array[RowD] = js.native

  var isAllRowsExpanded: Boolean = js.native

  var preExpandedRows: js.Array[RowD] = js.native

  def toggleAllRowsExpanded(): Unit               = js.native
  def toggleAllRowsExpanded(value: Boolean): Unit = js.native

  def toggleRowExpanded(id: js.Array[IdType[D]]): Unit                 = js.native
  def toggleRowExpanded(id: js.Array[IdType[D]], value: Boolean): Unit = js.native
}
