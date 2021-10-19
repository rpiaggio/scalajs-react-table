package reactST.reactTable.facade.headerGroup

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.mod.TableFooterProps
import reactST.reactTable.mod.FooterGroupPropGetter
import reactST.reactTable.mod.TableHeaderProps
import reactST.reactTable.mod.HeaderGroupPropGetter

@js.native
trait UseTableHeaderGroupProps[D] extends js.Object {

  def getFooterGroupProps(): TableFooterProps                                     = js.native
  def getFooterGroupProps(propGetter: FooterGroupPropGetter[D]): TableFooterProps = js.native

  def getHeaderGroupProps(): TableHeaderProps                                     = js.native
  def getHeaderGroupProps(propGetter: HeaderGroupPropGetter[D]): TableHeaderProps = js.native

  var headers: js.Array[HeaderGroup[D]] = js.native

  var totalHeaderCount: Double = js.native
}
