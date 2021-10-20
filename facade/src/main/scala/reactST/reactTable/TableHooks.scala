package reactST.reactTable

import japgolly.scalajs.react._
import japgolly.scalajs.react.hooks.CustomHook
import reactST.reactTable.mod.Cell
import reactST.reactTable.mod.TableState
import reactST.reactTable.mod.UseTableOptions
import reactST.reactTable.mod.PluginHook
import reactST.reactTable.mod.TableOptions

import scala.scalajs.js
import scala.scalajs.js.annotation.JSBracketAccess
import scala.scalajs.js.annotation.JSGlobal
import scala.scalajs.js.annotation.JSGlobalScope
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.`|`
import reactST.reactTable.mod.Row

import scalajs.js.JSConverters._

object TableHooks {
  @JSImport("react-table", "useTable")
  @js.native
  def useTableJS[
    D, // format: off
    TableOptsD <: UseTableOptions[D],
    TableInstanceType[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
    ColumnType[d] <: Column[d],
    RowD <: Row[D],
    CellType[d, v] <: Cell[d, v],
    TableStateD <: TableState[D] // format: on
  ](
    options: TableOptsD,
    plugins: PluginHook[D]*
  ): TableInstanceType[D, ColumnType, RowD, CellType, TableStateD] =
    js.native

  // According to documentation, react-table memoizes the table state.
  private implicit def reuseTableState[T <: TableState[_]]: Reusability[T] =
    Reusability.byRef

  def useTableHook[
    D, // format: off
    TableOptsD <: UseTableOptions[D],
    TableInstanceType[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
    ColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: ColumnOptions[d, v, col, row, cell, s],
    ColumnType[d] <: Column[d],
    RowD <: Row[D],
    CellType[d, v] <: Cell[d, v],
    TableStateD <: TableState[D],
    Layout // format: on
  ] =
    CustomHook[
      TableDefWithOptions[
        D,
        TableOptsD,
        TableInstanceType,
        ColumnOptsType,
        ColumnType,
        RowD,
        CellType,
        TableStateD,
        Layout
      ]
    ]
      .useMemoBy(_.cols)(_ => _.toJSArray)
      .useMemoBy(_.input.data)(_ => _.toJSArray)
      .buildReturning { (props, cols, rows) =>
        val tableInstance =
          useTableJS[D, TableOptsD, TableInstanceType, ColumnType, RowD, CellType, TableStateD](
            props.modOpts(props.tableDef.Options(cols, rows)),
            props.tableDef.plugins.toList.sorted.map(_.hook: PluginHook[D]): _*
          )
        Reusable
          .implicitly((cols, rows, props.modOpts, tableInstance.state))
          .withValue(tableInstance)
      }

  sealed trait TableHook extends js.Object
  object TableHook {
    implicit def asPluginHook[D](hook: TableHook): PluginHook[D] = hook.asInstanceOf[PluginHook[D]]
  }

  @JSImport("react-table", "useSortBy")
  @js.native
  object useSortBy extends TableHook

  @JSImport("react-table", "useExpanded")
  @js.native
  object useExpanded extends TableHook

  @JSImport("react-table", "useBlockLayout")
  @js.native
  object useBlockLayout extends TableHook

  @JSImport("react-table", "useResizeColumns")
  @js.native
  object useResizeColumns extends TableHook

  @JSImport("react-table", "useGridLayout")
  @js.native
  object useGridLayout extends TableHook
}
