package reactST.reactTable.facade.column

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.mod.TableResizerProps

@js.native
trait UseResizeColumnsColumn[D] extends Column[D] {
  var canResize: Boolean = js.native

  def getResizerProps(): TableResizerProps                 = js.native
  def getResizerProps(props: js.Object): TableResizerProps = js.native

  var isResizing: Boolean = js.native
}
