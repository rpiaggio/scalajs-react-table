package reactST.reactTable.facade.tableInstance

import reactST.reactTable.mod.IdType
import reactST.reactTable.mod.SortingRule

import scala.scalajs.js

@js.native
trait UseSortByTableInstance[
  D, // format: off
  ColumnType[d],
  RowType,
  CellType[d, v],
  StateType // format: on
] extends TableInstance[D, ColumnType, RowType, CellType, StateType] {

  var preSortedRowTypes: js.Array[RowType] = js.native

  def setSortBy(sortBy: js.Array[SortingRule[D]]): Unit = js.native

  def toggleSortBy(columnId: IdType[D]): Unit                                        = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean): Unit                   = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean, isMulti: Boolean): Unit = js.native
  def toggleSortBy(columnId: IdType[D], descending: Unit, isMulti: Boolean): Unit    = js.native
}
