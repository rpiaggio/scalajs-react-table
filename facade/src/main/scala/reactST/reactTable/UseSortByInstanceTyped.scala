package reactST.reactTable

import reactST.reactTable.mod._

import scala.scalajs.js

@js.native
trait UseSortByInstanceTyped[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD]
    extends TableInstanceTyped[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD] {

  var preSortedRows: js.Array[RowD] = js.native

  def setSortBy(sortBy: js.Array[SortingRule[D]]): Unit = js.native

  def toggleSortBy(columnId: IdType[D]): Unit                                        = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean): Unit                   = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean, isMulti: Boolean): Unit = js.native
  def toggleSortBy(columnId: IdType[D], descending: Unit, isMulti: Boolean): Unit    = js.native
}
