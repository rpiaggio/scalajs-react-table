package reactST.reactTable

import japgolly.scalajs.react._
import japgolly.scalajs.react.hooks.CustomHook
import reactST.reactTable.mod._

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
    ColumnOptsD, 
    ColumnObjectD, 
    RowD, 
    CellD, 
    TableStateD, 
    TableInstanceD[D0, CO, CI, RI, C, S] <: TableInstanceTyped[D0, CO, CI, RI, C, S]
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
    ColumnOptsD <: ColumnOptions[D],
    ColumnObjectD <: ColumnObject[D],
    RowD <: Row[D],
    CellD <: Cell[D, js.Any],
    TableStateD <: TableState[D],
    TableInstanceD[D0, CO, CI, RI, C, S] <: TableInstanceTyped[D0, CO, CI, RI, C, S],
    Layout
  ] =
    CustomHook[
      TableDefWithOptions[
        D,
        TableOptsD,
        ColumnOptsD,
        ColumnObjectD,
        RowD,
        CellD,
        TableStateD,
        TableInstanceD,
        Layout
      ]
    ]
      .useMemoBy(_.cols)(_ => _.toJSArray)
      .useMemoBy(_.input.data)(_ => _.toJSArray)
      .buildReturning { (props, cols, rows) =>
        val tableInstance =
          useTableJS[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD, TableInstanceD](
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
