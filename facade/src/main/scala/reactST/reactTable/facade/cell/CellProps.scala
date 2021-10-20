package reactST.reactTable.facade.cell

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.facade.tableInstance.TableInstance

@js.native
trait CellProps[ // format: off
  D, 
  V,
  ColumnD,
  RowType,
  CellType[d, v],
  StateType // format: on
] extends TableInstance[D, ColumnD, RowType, CellType, StateType] {
  var cell: CellType[D, V] = js.native

  var column: ColumnD = js.native

  var RowType: RowType = js.native

  var value: V = js.native
}
