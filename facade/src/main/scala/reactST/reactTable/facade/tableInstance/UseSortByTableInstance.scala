package reactST.reactTable.facade.tableInstance

import reactST.reactTable.mod.IdType
import reactST.reactTable.mod.SortingRule

import scala.scalajs.js

@js.native
trait UseSortByTableInstance[D, ColumnOptsD, ColumnObjectD, RowD, CellD[d, v], TableStateD]
    extends TableInstance[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD] {

  var preSortedRows: js.Array[RowD] = js.native

  def setSortBy(sortBy: js.Array[SortingRule[D]]): Unit = js.native

  def toggleSortBy(columnId: IdType[D]): Unit                                        = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean): Unit                   = js.native
  def toggleSortBy(columnId: IdType[D], descending: Boolean, isMulti: Boolean): Unit = js.native
  def toggleSortBy(columnId: IdType[D], descending: Unit, isMulti: Boolean): Unit    = js.native
}
