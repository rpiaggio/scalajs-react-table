package reactST.reactTable.facade.cell

import japgolly.scalajs.react.facade.React.Node
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.facade.column.Column
import reactST.reactTable.mod.TableCellProps
import reactST.reactTable.mod.CellPropGetter

@js.native
trait Cell[D, ColumnD, RowD] extends js.Object {

  var column: ColumnD = js.native

  def getCellProps(): TableCellProps                              = js.native
  def getCellProps(propGetter: CellPropGetter[D]): TableCellProps = js.native

  def render(`type`: String): Node                       = js.native
  def render(`type`: String, userProps: js.Object): Node = js.native
  @JSName("render")
  def render_Cell(`type`: reactST.reactTable.reactTableStrings.Cell): Node = js.native
  @JSName("render")
  def render_Cell(`type`: reactST.reactTable.reactTableStrings.Cell, userProps: js.Object): Node =
    js.native

  var row: RowD = js.native
}

@js.native
trait CellValue[V] extends js.Object {
  var value: V = js.native
}
