package reactST.reactTable.facade.column

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.mod.TableSortByToggleProps

@js.native
trait UseSortByColumn[D] extends Column[D] {
  var canSort: Boolean = js.native

  def clearSortBy(): Unit = js.native

  def getSortByToggleProps(): TableSortByToggleProps                 = js.native
  def getSortByToggleProps(props: js.Object): TableSortByToggleProps = js.native

  var isSorted: Boolean = js.native

  var isSortedDesc: js.UndefOr[Boolean] = js.native

  var sortedIndex: Double = js.native

  def toggleSortBy(): Unit                                    = js.native
  def toggleSortBy(descending: Boolean): Unit                 = js.native
  def toggleSortBy(descending: Boolean, multi: Boolean): Unit = js.native
  def toggleSortBy(descending: Unit, multi: Boolean): Unit    = js.native
}
