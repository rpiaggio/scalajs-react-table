package reactST.reactTable

import japgolly.scalajs.react.facade
import reactST.reactTable.mod.Cell

object syntax {
  implicit class CellOps[Self <: Cell[_, _]](val cell: Self) extends AnyVal {
    def renderCell: facade.React.Node = cell.render_Cell(reactTableStrings.Cell)
  }
}
