package reactST.reactTable.facade.columnOptions

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }

@js.native
trait UseResizeColumnsColumnOptions[ // format: off
  D,
  V,
  ColumnType[d],
  RowType,
  CellType[d, v],
  StateType // format: on
] extends ColumnOptions[D, V, ColumnType, RowType, CellType, StateType] {
  var disableResizing: js.UndefOr[Boolean] = js.native
}

object UseResizeColumnsColumnOptions {

  @scala.inline
  implicit class UseResizeColumnsColumnOptionsMutableBuilder[
    D, // format: off
    V,
    ColumnType[d],
    RowType,
    CellType[d, v],
    StateType,
    ColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: 
    UseResizeColumnsColumnOptions[d, v, col, row, cell, s] // format: on
  ](val x: ColumnOptsType[D, V, ColumnType, RowType, CellType, StateType])
      extends AnyVal {
    type Self = ColumnOptsType[D, V, ColumnType, RowType, CellType, StateType]

    @scala.inline
    def setDisableResizing(value: js.UndefOr[Boolean]): Self = {
      x.disableResizing = value
      x
    }
  }
}
