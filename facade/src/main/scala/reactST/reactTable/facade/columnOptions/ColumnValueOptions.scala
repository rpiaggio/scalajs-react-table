package reactST.reactTable.facade.columnOptions

import japgolly.scalajs.react.facade.React.Node
import reactST.reactTable.reactTableStrings.Footer
import reactST.reactTable.reactTableStrings.Header
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.mod.Renderer
import reactST.reactTable.mod.TableFooterProps
import reactST.reactTable.mod.FooterPropGetter
import reactST.reactTable.mod.TableHeaderProps
import reactST.reactTable.mod.HeaderPropGetter
import reactST.reactTable.facade.cell.CellProps
import reactST.reactTable.mod.HeaderProps
import reactST.reactTable.mod.IdType
import japgolly.scalajs.react.vdom.VdomElement
import japgolly.scalajs.react.vdom.VdomNode
import japgolly.scalajs.react.facade.React.ComponentClassP
import reactST.reactTable.facade.cell.CellValue

@js.native
trait ColumnValueOptions[D, V, ColumnD, RowType, CellType[d, col, row], StateType]
    extends js.Object {
  val Cell: js.UndefOr[Renderer[CellProps[D, V, ColumnD, RowType, CellType, StateType]]] =
    js.native
}

object ColumnValueOptions {

  @scala.inline // format: off
  implicit class ColumnValueOptionsMutableBuilder[D, V, ColumnD, RowD, CellType[d, col, row], TableStateD, Self]( // format: on
    val colOpts: Self with ColumnValueOptions[D, V, ColumnD, RowD, CellType, TableStateD]
  ) extends AnyVal {

    @scala.inline
    def setCell(value: CellProps[D, V, ColumnD, RowD, CellType, TableStateD] => VdomNode): Self =
      StObject.set(
        colOpts,
        "Cell",
        value
          .andThen(_.rawNode): js.Function1[CellProps[D, V, ColumnD, RowD, CellType, TableStateD],
                                            Node
        ]
      )

    // Next 4 methods just copied from ColumnInterfaceBasedOnValueMutableBuilder, which lacks the function overload above.
    @scala.inline
    def setCell(value: Renderer[CellProps[D, V, ColumnD, RowD, CellType, TableStateD]]): Self =
      StObject.set(colOpts, "Cell", value.asInstanceOf[js.Any])

    @scala.inline
    def setCellComponentClass(
      value: ComponentClassP[CellProps[D, V, ColumnD, RowD, CellType, TableStateD]]
    ): Self =
      StObject.set(colOpts, "Cell", value.asInstanceOf[js.Any])

    @scala.inline
    def setCellUndefined: Self = StObject.set(colOpts, "Cell", js.undefined)

    @scala.inline
    def setCellVdomElement(value: VdomElement): Self =
      StObject.set(colOpts, "Cell", value.rawElement.asInstanceOf[js.Any])

  }
}
