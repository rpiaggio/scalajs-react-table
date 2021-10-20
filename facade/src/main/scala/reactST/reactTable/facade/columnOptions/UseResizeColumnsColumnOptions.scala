package reactST.reactTable.facade.columnOptions

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }

@js.native
trait UseResizeColumnsColumnOptions[ // format: off
  D,
  ColumnD,
  RowType,
  CellType[d, v],
  StateType // format: on
] extends ColumnOptions[D, ColumnD, RowType, CellType, StateType] {
  var disableResizing: js.UndefOr[Boolean] = js.native
}

object UseResizeColumnsColumnOptions {

  @scala.inline
  implicit class UseResizeColumnsColumnOptionsMutableBuilder[
    D, // format: off
    V,
    ColumnD,
    RowD,
    CellType[d, v],
    TableStateD,
    ColumnOptsType[d, col, row, cell[d0, v0], s] <: 
      UseResizeColumnsColumnOptions[d, col, row, cell, s] // format: on
  ](val x: ColumnOptsType[D, ColumnD, RowD, CellType, TableStateD])
      extends AnyVal {
    type Self = ColumnOptsType[D, ColumnD, RowD, CellType, TableStateD]

    @scala.inline
    def setDisableResizing(value: js.UndefOr[Boolean]): Self = {
      x.disableResizing = value
      x
    }
  }
}
