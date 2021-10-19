// package reactST.reactTable.facade.column

// import japgolly.scalajs.react.facade.React.Node
// import reactST.reactTable.reactTableStrings.Footer
// import reactST.reactTable.reactTableStrings.Header
// import org.scalablytyped.runtime.StObject
// import scala.scalajs.js
// import scala.scalajs.js.`|`
// import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
// import reactST.reactTable.mod.Renderer
// import reactST.reactTable.mod.TableFooterProps
// import reactST.reactTable.mod.FooterPropGetter
// import reactST.reactTable.mod.TableHeaderProps
// import reactST.reactTable.mod.HeaderPropGetter
// import reactST.reactTable.mod.CellProps
// import reactST.reactTable.mod.HeaderProps
// import reactST.reactTable.mod.IdType

// @js.native
// trait ColumnInstance[D, CellD] extends js.Object {

//   var Cell: js.UndefOr[Renderer[CellProps[D, js.Any]]] = js.native

//   var Header: js.UndefOr[Renderer[HeaderProps[D]]] = js.native

//   var columns: js.UndefOr[js.Array[ColumnInstance[D, CellD]]] = js.native

//   var depth: Double = js.native

//   def getFooterProps(): TableFooterProps                                = js.native
//   def getFooterProps(propGetter: FooterPropGetter[D]): TableFooterProps = js.native

//   def getHeaderProps(): TableHeaderProps                                = js.native
//   def getHeaderProps(propGetter: HeaderPropGetter[D]): TableHeaderProps = js.native

//   // not documented
//   def getToggleHiddenProps(): js.Any                  = js.native
//   def getToggleHiddenProps(userProps: js.Any): js.Any = js.native

//   var id: IdType[D] = js.native

//   var isVisible: Boolean = js.native

//   var maxWidth: js.UndefOr[Double] = js.native

//   var minWidth: js.UndefOr[Double] = js.native

//   var parent: js.UndefOr[ColumnInstance[D]] = js.native

//   // not documented
//   var placeholderOf: js.UndefOr[ColumnInstance[js.Object]] = js.native

//   def render(`type`: String): Node                   = js.native
//   def render(`type`: String, props: js.Object): Node = js.native
//   @JSName("render")
//   def render_Footer(`type`: Footer): Node = js.native
//   @JSName("render")
//   def render_Footer(`type`: Footer, props: js.Object): Node = js.native
//   @JSName("render")
//   def render_Header(`type`: Header): Node = js.native
//   @JSName("render")
//   def render_Header(`type`: Header, props: js.Object): Node = js.native

//   def toggleHidden(): Unit               = js.native
//   def toggleHidden(value: Boolean): Unit = js.native

//   var totalLeft: Double = js.native

//   var totalWidth: Double = js.native

//   var width: js.UndefOr[Double | String] = js.native
// }
