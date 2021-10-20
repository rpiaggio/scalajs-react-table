package reactST.reactTable.facade.tableInstance

import reactST.reactTable.mod.IdType
import reactST.reactTable.mod.SortingRule

import scala.scalajs.js

@js.native
trait UseSortByTableInstance[
  D, // format: off
  ColumnD,
  RowD,
  CellType[d, v],
  TableStateD // format: on
] extends TableInstance[D, ColumnD, RowD, CellType, TableStateD] {

  var preSortedRowTypes: js.Array[RowD] = js.native

  def setSortBy(sortBy: js.Array[SortingRule[D]]): Unit = js.native

  def toggleSortBy(columnId: IdType[D]): Unit                                        = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean): Unit                   = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean, isMulti: Boolean): Unit = js.native
  def toggleSortBy(columnId: IdType[D], descending: Unit, isMulti: Boolean): Unit    = js.native
}
