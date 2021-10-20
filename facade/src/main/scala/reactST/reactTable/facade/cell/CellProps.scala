package reactST.reactTable.facade.cell

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.facade.tableInstance.TableInstance

@js.native
trait CellProps[D, V, ColumnD, RowD, CellType[_, _, _], StateType]
    extends TableInstance[D, ColumnD, RowD, CellType, StateType] {
  val cell: CellType[D, ColumnD, RowD] with CellValue[V] = js.native

  val column: ColumnD = js.native

  val RowType: RowD = js.native

  val value: V = js.native
}
