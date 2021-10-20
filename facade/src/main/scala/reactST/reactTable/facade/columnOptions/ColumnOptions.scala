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

  var columns: js.UndefOr[
    js.Array[ColumnOptions[D, js.Any, ColumnType, RowType, CellType, StateType]]
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
  def apply[ // format: off
    D,
    V,
    ColumnType[d],
    RowType,
    CellType[d, v],
    StateType // format: on
  ](): ColumnOptions[D, V, ColumnType, RowType, CellType, StateType] =
    js.Dynamic.literal().asInstanceOf[ColumnOptions[D, V, ColumnType, RowType, CellType, StateType]]

  @scala.inline
  implicit class ColumnOptionsMutableBuilder[
    D,
    V,
    ColumnType[d],
    RowType,
    CellType[d, v],
    StateType,
    ColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: ColumnOptions[d, v, col, row, cell, s]
    // Self <: ColumnOptions[D, V, ColumnType, RowType, CellType, StateType] // format: on
  ](val x: ColumnOptsType[D, V, ColumnType, RowType, CellType, StateType])
      extends AnyVal {
    type Self = ColumnOptsType[D, V, ColumnType, RowType, CellType, StateType]

    @scala.inline
    def setHeader(value: js.UndefOr[Renderer[HeaderProps[D]]]): Self = {
      x.Header = value
      x
    }

    @scala.inline
    def setAccessor(value: js.UndefOr[reactST.reactTable.mod.Accessor[D] | IdType[D]]): Self = {
      x.accessor = value
      x
    }

    @scala.inline
    def setId(value: IdType[D]): Self = StObject.set(x, "id", value.asInstanceOf[js.Any])

    @scala.inline
    def setIdUndefined: Self = StObject.set(x, "id", js.undefined)

    @scala.inline
    def setMaxWidth(value: Double): Self = StObject.set(x, "maxWidth", value.asInstanceOf[js.Any])

    @scala.inline
    def setMaxWidthUndefined: Self = StObject.set(x, "maxWidth", js.undefined)

    @scala.inline
    def setMinWidth(value: Double): Self = StObject.set(x, "minWidth", value.asInstanceOf[js.Any])

    @scala.inline
    def setMinWidthUndefined: Self = StObject.set(x, "minWidth", js.undefined)

    @scala.inline
    def setWidth(value: Double | String): Self =
      StObject.set(x, "width", value.asInstanceOf[js.Any])

    @scala.inline
    def setWidthUndefined: Self = StObject.set(x, "width", js.undefined)
  }
}
