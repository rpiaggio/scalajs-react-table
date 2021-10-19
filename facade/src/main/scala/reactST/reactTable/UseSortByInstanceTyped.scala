package reactST.reactTable

import reactST.reactTable.mod._

import scala.scalajs.js

@js.native
trait UseSortByInstanceTyped[D, RowD <: Row[D]] extends js.Object {

  var preSortedRows: js.Array[RowD] = js.native

  def setSortBy(sortBy: js.Array[SortingRule[D]]): Unit = js.native

  def toggleSortBy(columnId: IdType[D]): Unit                                        = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean): Unit                   = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean, isMulti: Boolean): Unit = js.native
  def toggleSortBy(columnId: IdType[D], descending: Unit, isMulti: Boolean): Unit    = js.native
}
