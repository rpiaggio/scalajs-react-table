package react.table.demo

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import react.common.Css
import reactST.reactTable._
import reactST.reactTable.mod.SortingRule

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

import js.annotation._

@JSExportTopLevel("DemoMain")
object DemoMain {

  def logit(message: String, obj: Any) = dom.console.log(message, obj.asInstanceOf[js.Any])

  case class Details(year: Int, pickups: Int, color: String)
  case class Guitar(id: Int, make: String, model: String, details: Details)

  def rowClassEvenOdd[D]: (Int, D) => Css = (i, _) => if (i % 2 == 0) Css("even") else Css("odd")

  val guitars =
    Reusable.always(
      List(
        Guitar(1, "Fender", "Stratocaster", Details(2019, 3, "Sunburst")),
        Guitar(2, "Gibson", "Les Paul", Details(1958, 2, "Gold top")),
        Guitar(3, "Fender", "Telecaster", Details(1971, 2, "Ivory")),
        Guitar(4, "Godin", "LG", Details(2008, 2, "Burgundy"))
      )
    )

  val randomData = Reusable.always(RandomData.randomPeople(1000))

  // TABLE 0
  val BaseTableDef = TableDef[Guitar].withBlockLayout.withExpanded.withSortBy // THIS DOESN'T WORK
  // val BaseTableDef = TableDef[Guitar].withBlockLayout.withExpanded // THIS WORKS
  val BaseTable    =
    ScalaFnComponent
      .withHooks[
        (Reusable[List[BaseTableDef.Type.ColumnOptions]], Reusable[List[Guitar]])
      ]
      .useTableBy { case (cols, data) =>
        val xxx = BaseTableDef(cols, data)
        xxx
      }
      .renderWithReuse((_, tableInstance) =>
        HTMLTable /*[ // FORCED TYPES, STILL DOESN'T WORK
          Guitar,
          reactST.reactTable.mod.UseTableOptions[
            react.table.demo.DemoMain.Guitar
          ] with reactST.reactTable.mod.UseExpandedOptions[
            react.table.demo.DemoMain.Guitar
          ] with reactST.reactTable.mod.UseSortByOptions[react.table.demo.DemoMain.Guitar],
          reactST.reactTable.TableInstance with reactST.reactTable.UseExpandedTableInstance with reactST.reactTable.UseSortByTableInstance,
          reactST.reactTable.ColumnOptions with reactST.reactTable.UseSortByColumnOptions,
          reactST.reactTable.facade.column.Column[
            react.table.demo.DemoMain.Guitar
          ] with reactST.reactTable.facade.column.UseSortByColumn[react.table.demo.DemoMain.Guitar],
          reactST.reactTable.mod.UseTableRowProps[
            react.table.demo.DemoMain.Guitar
          ] with reactST.reactTable.mod.UseExpandedRowProps[react.table.demo.DemoMain.Guitar],
          reactST.reactTable.Cell,
          reactST.reactTable.mod.TableState[
            react.table.demo.DemoMain.Guitar
          ] with reactST.reactTable.mod.UseExpandedState[
            react.table.demo.DemoMain.Guitar
          ] with reactST.reactTable.mod.UseSortByState[react.table.demo.DemoMain.Guitar],
          Layout.NonTable
        ]*/ (BaseTableDef)(
          tableClass = Css("guitars"),
          headerCellFn = Some(HTMLTable.basicHeaderCellFn()),
          footer = <.tfoot(<.tr(<.th(^.colSpan := 6, s"Guitar Count: ${guitars.length}")))
        )(tableInstance)
      )

  val baseTableColumns =
    Reusable.always(
      List(
        BaseTableDef
          .Column("id", _.id)
          .setCell(cell => <.span(s"g-${cell.value}"))
          .setHeader("Id"),
        BaseTableDef.Column("make", _.make).setHeader("Make"),
        BaseTableDef.Column("model", _.model).setHeader("Model"),
        BaseTableDef
          .ColumnGroup(
            BaseTableDef.Column("year", _.details.year).setHeader("Year"),
            BaseTableDef.Column("pickups", _.details.pickups).setHeader("Pickups"),
            BaseTableDef.Column("color", _.details.color).setHeader("Color")
          )
          .setHeader("Details")
      )
    )

  // TABLE 1
  val SortedTableDef = TableDef[Guitar].withSortBy
  import SortedTableDef.syntax._

  val sortedTableState = SortedTableDef.State().setSortByVarargs(SortingRule("model"))

  val SortedTable =
    ScalaFnComponent
      .withHooks[
        (Reusable[List[SortedTableDef.Type.ColumnOptions]], Reusable[List[Guitar]])
      ]
      .useTableBy { case (cols, data) =>
        SortedTableDef(cols, data, Reusable.always(_.setInitialStateFull(sortedTableState)))
      }
      .renderWithReuse((_, tableInstance) =>
        HTMLTable(SortedTableDef)(
          tableClass = Css("guitars"),
          headerCellFn = Some(HTMLTable.sortableHeaderCellFn()),
          footer = <.tfoot(<.tr(<.th(^.colSpan := 6, s"Guitar Count: ${guitars.length}")))
        )(tableInstance)
      )

  val sortedTableColumns =
    Reusable.always(
      List(
        SortedTableDef
          .Column("id", _.id)
          .setCell(cell => <.span(s"g-${cell.value}"))
          .setSortByAuto
          .setHeader("Id"),
        SortedTableDef.Column("make", _.make).setHeader("Make"),
        SortedTableDef.Column("model", _.model).setHeader("Model"),
        SortedTableDef
          .ColumnGroup(
            SortedTableDef.Column("year", _.details.year).setHeader("Year"),
            SortedTableDef.Column("pickups", _.details.pickups).setHeader("Pickups"),
            SortedTableDef.Column("color", _.details.color).setHeader("Color")
          )
          .setHeader("Details")
      )
    )

  // TABLE 2
  val VirtualizedTableDef = TableDef[RandomData.Person].withBlockLayout

  val VirtualizedTable =
    ScalaFnComponent
      .withHooks[
        (Reusable[List[VirtualizedTableDef.Type.ColumnOptions]], Reusable[List[RandomData.Person]])
      ]
      .useTableBy { case (cols, data) => VirtualizedTableDef(cols, data) }
      .render((_, tableInstance) =>
        HTMLTable.virtualized(VirtualizedTableDef)(
          tableClass = Css("virtualized"),
          rowClassFn = rowClassEvenOdd,
          headerCellFn = Some(HTMLTable.basicHeaderCellFn(useDiv = true))
        )(tableInstance)
      )

  val virtualizedTableColumns = Reusable.always(
    List(
      VirtualizedTableDef.Column("first", _.first).setHeader("First").setWidth(100),
      VirtualizedTableDef.Column("last", _.last).setHeader("Last").setWidth(100),
      VirtualizedTableDef.Column("age", _.age).setHeader("Age").setWidth(50)
    )
  )

  // TABLE 3
  val SortedVirtualizedTableDef = TableDef[RandomData.Person].withSortBy.withBlockLayout

  val SortedVirtualizedTable =
    ScalaFnComponent
      .withHooks[
        (Reusable[List[SortedVirtualizedTableDef.Type.ColumnOptions]],
         Reusable[List[RandomData.Person]]
        )
      ]
      .useTableBy { case (cols, data) => SortedVirtualizedTableDef(cols, data) }
      .render((_, tableInstance) =>
        HTMLTable.virtualized(SortedVirtualizedTableDef)(
          tableClass = Css("virtualized"),
          headerCellFn = Some(HTMLTable.sortableHeaderCellFn(useDiv = true))
        )(tableInstance)
      )

  val sortedVirtualizedTableColumns = Reusable.always(
    List(
      SortedVirtualizedTableDef.Column("first", _.first).setHeader("First").setWidth(100),
      SortedVirtualizedTableDef.Column("last", _.last).setHeader("Last").setWidth(100),
      SortedVirtualizedTableDef.Column("age", _.age).setHeader("Age").setWidth(75)
    )
  )

  // Table 4

  // D,
  //      TableOptsD <: UseTableOptions[D],
  //      TableInstanceType[d, col, row, cell, s] <: TableInstance[d, col, row, cell, s],
  //      ColumnOptsType[d, v, col, row, cell, s] <: ColumnOptions[d, v, col, row, cell, s],
  //      ColumnD <: Column[D],
  //      RowD <: Row[D],
  //      CellType[d, v] <: Cell[d, v],
  //      TableStateD <: TableState[D],
  //      Layout

  val SortedVariableVirtualizedTableDef =
    TableDef[RandomData.Person].withSortBy.withBlockLayout //.withResizeColumns

  val SortedVariableVirtualizedTable =
    ScalaFnComponent
      .withHooks[
        (Reusable[List[SortedVariableVirtualizedTableDef.Type.ColumnOptions]],
         Reusable[List[RandomData.Person]]
        )
      ]
      // .useTableBy[RandomData.Person,
      //             SortedVariableVirtualizedTableDef.Type.Options,
      //             SortedVariableVirtualizedTableDef.Type.InstanceC,
      //             SortedVariableVirtualizedTableDef.Type.ColumnOptionsC,
      //             SortedVariableVirtualizedTableDef.Type.Column,
      //             SortedVariableVirtualizedTableDef.Type.Row,
      //             SortedVariableVirtualizedTableDef.Type.CellC,
      //             SortedVariableVirtualizedTableDef.Type.TableState,
      //             Layout.NonTable
      // ]
      .useTableBy { case (cols, data) => SortedVariableVirtualizedTableDef(cols, data) }
      .render((_, tableInstance) =>
        HTMLTable.virtualized(SortedVariableVirtualizedTableDef)(
          tableClass = Css("virtualized"),
          bodyHeight = Some(300), // make this one a different height
          headerCellFn = Some(HTMLTable.sortableHeaderCellFn(useDiv = true)),
          rowClassFn = (_: Int, p: RandomData.Person) => if (p.id % 2 == 0) Css("") else Css("big")
        )(tableInstance)
      )

  val sortedVariableVirtualizedTableColumns = Reusable.always(
    List(
      SortedVariableVirtualizedTableDef.Column("id", _.id).setHeader("Id").setWidth(50),
      SortedVariableVirtualizedTableDef.Column("first", _.first).setHeader("First").setWidth(100),
      SortedVariableVirtualizedTableDef.Column("last", _.last).setHeader("Last").setWidth(100),
      SortedVariableVirtualizedTableDef.Column("age", _.age).setHeader("Age").setWidth(75)
    )
  )

  @JSExport
  def main(): Unit = {

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    <.div(
      <.h1("Demo for scalajs-react-table"),
      <.h2("Base table"),
      BaseTable((baseTableColumns, guitars)),
      <.h2("Sortable table"),
      SortedTable((sortedTableColumns, guitars)),
      "Click header to sort. Shift-Click for multi-sort.",
      <.h2("Virtualized Table"),
      VirtualizedTable((virtualizedTableColumns, randomData)),
      <.h2("Sortable Virtualized Table"),
      SortedVirtualizedTable((sortedVirtualizedTableColumns, randomData)),
      <.h2("Sortable Variable RowType Height Virtualized Table"),
      <.h3("RowTypes with odd id's are taller via CSS."),
      SortedVariableVirtualizedTable((sortedVariableVirtualizedTableColumns, randomData))
    )
      .renderIntoDOM(container)

    ()
  }
}
