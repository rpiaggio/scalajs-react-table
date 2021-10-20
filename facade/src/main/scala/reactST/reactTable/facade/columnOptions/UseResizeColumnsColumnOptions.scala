package reactST.reactTable.facade.columnOptions

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }

@js.native
trait UseResizeColumnsColumnOptions[D, ColumnD, RowD, CellType[d, col, row], TableStateD]
    extends ColumnOptions[D, ColumnD, RowD, CellType, TableStateD] {
  var disableResizing: js.UndefOr[Boolean] = js.native
}

object UseResizeColumnsColumnOptions {

  @scala.inline
  implicit class UseResizeColumnsColumnOptionsMutableBuilder[
    D, // format: off
    V,
    ColumnD,
    RowD,
    CellType[d, col, row],
    TableStateD,
    ColumnOptsType[d, col, row, cell[_, _, _], s] <: UseResizeColumnsColumnOptions[d, col, row, cell, s] // format: on
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
