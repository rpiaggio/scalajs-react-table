package reactST.reactTable.facade.column

import japgolly.scalajs.react.facade.React.Node
import reactST.reactTable.reactTableStrings.Footer
import reactST.reactTable.reactTableStrings.Header
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.mod.TableFooterProps
import reactST.reactTable.mod.FooterPropGetter
import reactST.reactTable.mod.TableHeaderProps
import reactST.reactTable.mod.HeaderPropGetter
import reactST.reactTable.mod.IdType
import reactST.reactTable.mod.Renderer

@js.native
trait Column[D] extends js.Object {
  var columns: js.UndefOr[js.Array[Column[D]]] = js.native

  var depth: Double = js.native

  def getFooterProps(): TableFooterProps                                = js.native
  def getFooterProps(propGetter: FooterPropGetter[D]): TableFooterProps = js.native

  def getHeaderProps(): TableHeaderProps                                = js.native
  def getHeaderProps(propGetter: HeaderPropGetter[D]): TableHeaderProps = js.native

  // not documented
  def getToggleHiddenProps(): js.Any                  = js.native
  def getToggleHiddenProps(userProps: js.Any): js.Any = js.native

  var id: IdType[D] = js.native

  var isVisible: Boolean = js.native

  var parent: js.UndefOr[Column[D]] = js.native

  // not documented
  var placeholderOf: js.UndefOr[Column[js.Object]] = js.native

  def render(`type`: String): Node                   = js.native
  def render(`type`: String, props: js.Object): Node = js.native
  @JSName("render")
  def render_Footer(`type`: Footer): Node = js.native
  @JSName("render")
  def render_Footer(`type`: Footer, props: js.Object): Node = js.native
  @JSName("render")
  def render_Header(`type`: Header): Node = js.native
  @JSName("render")
  def render_Header(`type`: Header, props: js.Object): Node = js.native

  def toggleHidden(): Unit               = js.native
  def toggleHidden(value: Boolean): Unit = js.native

  var totalLeft: Double = js.native

  var totalWidth: Double = js.native
}

object Column {
  implicit class ColumnOps(val col: Column[_]) extends AnyVal {
    def renderHeader: Node = col.render_Header(Header)
    def renderFooter: Node = col.render_Footer(Footer)
  }
}
