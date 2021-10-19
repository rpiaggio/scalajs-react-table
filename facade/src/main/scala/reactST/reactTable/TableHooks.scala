package reactST.reactTable

import japgolly.scalajs.react._
import japgolly.scalajs.react.hooks.CustomHook
import reactST.reactTable.mod.Row
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

import scalajs.js.JSConverters._

object TableHooks {
  @JSImport("react-table", "useTable")
  @js.native
  def useTableJS[
    D, // format: off
    TableInstanceD[d, co, col, row, cell, s] <: TableInstance[d, co, col, row, cell, s],
    ColumnOptsD, 
    ColumnObjectD, 
    RowD, 
    CellD, 
    TableStateD, 
  ]( // format: on
    options: TableOptions[D],
    plugins: PluginHook[D]*
  ): TableInstanceD[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD] =
    js.native

  // According to documentation, react-table memoizes the table state.
  private implicit def reuseTableState[T <: TableState[_]]: Reusability[T] =
    Reusability.byRef

  def useTableHook[
    D,
    TableOptsD <: UseTableOptions[D],
    TableInstanceD[d, co, col, row, cell, s] <: TableInstance[d, co, col, row, cell, s],
    ColumnOptsD <: ColumnOptions[D],
    ColumnObjectD <: ColumnObject[D],
    RowD <: Row[D],
    CellD <: Cell[D, js.Any],
    TableStateD <: TableState[D],
    Layout
  ] =
    CustomHook[
      TableDefWithOptions[
        D,
        TableOptsD,
        TableInstanceD,
        ColumnOptsD,
        ColumnObjectD,
        RowD,
        CellD,
        TableStateD,
        Layout
      ]
    ]
      .useMemoBy(_.cols)(_ => _.toJSArray)
      .useMemoBy(_.input.data)(_ => _.toJSArray)
      .buildReturning { (props, cols, rows) =>
        val tableInstance =
          useTableJS[D, TableInstanceD, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD](
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
