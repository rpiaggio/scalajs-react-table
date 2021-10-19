package reactST.reactTable.facade.cell

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.facade.tableInstance.TableInstance

@js.native
trait CellProps[D, V, ColumnOptsD, ColumnObjectD, RowD, CellD[d, v], TableStateD]
    extends TableInstance[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD] {

  var cell: CellD[D, V] = js.native

  var column: ColumnObjectD = js.native

  var row: RowD = js.native

  var value: V = js.native
}
