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

@js.native
trait ColumnOptions[ // format: off
  D,
  V,
  ColumnType[d],
  RowType,
  CellType[d, v],
  StateType // format: on
] extends js.Object {
  val Cell: js.UndefOr[Renderer[CellProps[D, V, ColumnType, RowType, CellType, StateType]]] =
    js.native

  var Header: js.UndefOr[Renderer[HeaderProps[D]]] = js.native

  var Footer: js.UndefOr[Renderer[HeaderProps[D]]] = js.native

  var accessor: js.UndefOr[reactST.reactTable.mod.Accessor[D] | IdType[D]] = js.native

  // TODO Proper col group type
  var columns: js.UndefOr[
    js.Array[ColumnOptions[D, _, ColumnType, RowType, CellType, StateType]]
  ] = js.native

  var depth: Double = js.native

  def getFooterProps(): TableFooterProps                                = js.native
  def getFooterProps(propGetter: FooterPropGetter[D]): TableFooterProps = js.native

  def getHeaderProps(): TableHeaderProps                                = js.native
  def getHeaderProps(propGetter: HeaderPropGetter[D]): TableHeaderProps = js.native

  // not documented
  def getToggleHiddenProps(): js.Any                  = js.native
  def getToggleHiddenProps(userProps: js.Any): js.Any = js.native

  var id: IdType[D] = js.native

  var isVisible: Boolean = js.native

  var maxWidth: js.UndefOr[Double] = js.native

  var minWidth: js.UndefOr[Double] = js.native

  def parent[VP]: js.UndefOr[ColumnOptions[D, VP, ColumnType, RowType, CellType, StateType]] =
    js.native

  // not documented
  var placeholderOf
    : js.UndefOr[ColumnOptions[js.Object, js.Any, ColumnType, RowType, CellType, StateType]] =
    js.native

  def render(`type`: String): Node                   = js.native
  def render(`type`: String, props: js.Object): Node = js.native
  @JSName("render")
  def render_Footer(`type`: Footer): Node = js.native
  @JSName("render")
  def render_Footer(`type`: Footer, props: js.Object): Node = js.native
  @JSName("render")
  def render_Header(`type`: Header): Node = js.native
  @JSName("render")
  def render_Header(`type`: Header, props: js.Object): Node = js.native

  def toggleHidden(): Unit               = js.native
  def toggleHidden(value: Boolean): Unit = js.native

  var totalLeft: Double = js.native

  var totalWidth: Double = js.native

  var width: js.UndefOr[Double | String] = js.native
}

object ColumnOptions {

  @scala.inline
  implicit class ColumnOptionsMutableBuilder[
    D, // format: off
    V,
    ColumnType[d],
    RowD,
    CellType[d, v],
    TableStateD,
    Self
    // ColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: ColumnOptions[d, v, col, row, cell, s]
    // Self <: ColumnOptions[D, V, ColumnType, RowType, CellType, StateType] // format: on
  ](val colOpts: Self with ColumnOptions[D, V, ColumnType, RowD, CellType, TableStateD])
      extends AnyVal {
    // type Self = ColumnOptsType[D, V, ColumnType, RowType, CellType, StateType]

    @scala.inline
    def setHeader(value: js.UndefOr[Renderer[HeaderProps[D]]]): Self = {
      colOpts.Header = value
      colOpts
    }

    @scala.inline
         def setCell(value: CellProps[D, V, ColumnType, RowD, CellType, TableStateD] => VdomNode): Self =
           StObject.set(colOpts,
                        "Cell",
                        value
                          .andThen(_.rawNode): js.Function1[CellProps[D, V, ColumnType, RowD, CellType, TableStateD], Node]
           )

         // Next 4 methods just copied from ColumnInterfaceBasedOnValueMutableBuilder, which lacks the function overload above.
         @scala.inline
         def setCell(value: Renderer[CellProps[D, V, ColumnType, RowD, CellType, TableStateD]]): Self =
           StObject.set(colOpts, "Cell", value.asInstanceOf[js.Any])

         @scala.inline
         def setCellComponentClass(value: ComponentClassP[CellProps[D, V, ColumnType, RowD, CellType, TableStateD]]): Self =
           StObject.set(colOpts, "Cell", value.asInstanceOf[js.Any])

         @scala.inline
         def setCellUndefined: Self = StObject.set(colOpts, "Cell", js.undefined)

         @scala.inline
         def setCellVdomElement(value: VdomElement): Self =
           StObject.set(colOpts, "Cell", value.rawElement.asInstanceOf[js.Any])

    @scala.inline
    def setAccessor(value: js.UndefOr[reactST.reactTable.mod.Accessor[D] | IdType[D]]): Self = {
      colOpts.accessor = value
      colOpts
    }

    @scala.inline
    def setId(value: IdType[D]): Self = StObject.set(colOpts, "id", value.asInstanceOf[js.Any])

    @scala.inline
    def setIdUndefined: Self = StObject.set(colOpts, "id", js.undefined)

    @scala.inline
    def setMaxWidth(value: Double): Self = StObject.set(colOpts, "maxWidth", value.asInstanceOf[js.Any])

    @scala.inline
    def setMaxWidthUndefined: Self = StObject.set(colOpts, "maxWidth", js.undefined)

    @scala.inline
    def setMinWidth(value: Double): Self = StObject.set(colOpts, "minWidth", value.asInstanceOf[js.Any])

    @scala.inline
    def setMinWidthUndefined: Self = StObject.set(colOpts, "minWidth", js.undefined)

    @scala.inline
    def setWidth(value: Double | String): Self =
      StObject.set(colOpts, "width", value.asInstanceOf[js.Any])

    @scala.inline
    def setWidthUndefined: Self = StObject.set(colOpts, "width", js.undefined)

    // TODO PRoper col group type
    @scala.inline
    // def setColumns(value: js.Array[ColumnOptsType[D, _, ColumnType, RowType, CellType, StateType]]): Self = StObject.set(colOpts, "columns", value.asInstanceOf[js.Any])
    def setColumns(value: js.Array[ColumnOptions[D, _, ColumnType, RowD, CellType, TableStateD]]): Self = StObject.set(colOpts, "columns", value.asInstanceOf[js.Any])

    @scala.inline
    // def setColumnsVarargs(value: ColumnOptsType[D, _, ColumnType, RowType, CellType, StateType]*): Self = StObject.set(colOpts, "columns", js.Array(value :_*))
    def setColumnsVarargs(value: ColumnOptions[D, _, ColumnType, RowD, CellType, TableStateD]*): Self = StObject.set(colOpts, "columns", js.Array(value :_*))

  }
}
