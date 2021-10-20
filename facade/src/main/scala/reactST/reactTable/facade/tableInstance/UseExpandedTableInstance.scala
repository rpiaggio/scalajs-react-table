package reactST.reactTable.facade.tableInstance

import reactST.reactTable.mod.IdType

import scala.scalajs.js

@js.native
trait UseExpandedTableInstance[D, ColumnD, RowD, CellType[d, col, row], TableStateD]
    extends js.Object {
  _: TableInstance[D, ColumnD, RowD, CellType, TableStateD] =>

  var expandedDepth: Double = js.native

  var expandedRowTypes: js.Array[RowD] = js.native

  var isAllRowTypesExpanded: Boolean = js.native

  var preExpandedRowTypes: js.Array[RowD] = js.native

  def toggleAllRowTypesExpanded(): Unit               = js.native
  def toggleAllRowTypesExpanded(value: Boolean): Unit = js.native

  def toggleRowTypeExpanded(id: js.Array[IdType[D]]): Unit                 = js.native
  def toggleRowTypeExpanded(id: js.Array[IdType[D]], value: Boolean): Unit = js.native
}
