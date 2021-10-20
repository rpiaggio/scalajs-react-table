package reactST.reactTable

import japgolly.scalajs.react._
import japgolly.scalajs.react.hooks.CustomHook
import reactST.reactTable.mod.RowType
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
    TableInstanceD[d, co, col, RowType, cell[d0, v], s] <: TableInstance[d, co, col, RowType, cell, s],
    ColumnOptsD, 
    ColumnObjectD, 
    RowTypeD, 
    CellType[d0, v] <: Cell[d0, v], 
    StateType, 
  ]( // format: on
    options: TableOptions[D],
    plugins: PluginHook[D]*
  ): TableInstanceD[D, ColumnOptsD, ColumnObjectD, RowTypeD, CellType, StateType] =
    js.native

  // According to documentation, react-table memoizes the table state.
  private implicit def reuseTableState[T <: TableState[_]]: Reusability[T] =
    Reusability.byRef

  def useTableHook[
    D,
    TableOptsD <: UseTableOptions[D],
    TableInstanceD[d, co, col, RowType, cell[d0, v], s] <: TableInstance[d,
                                                                         co,
                                                                         col,
                                                                         RowType,
                                                                         cell,
                                                                         s
    ],
    ColumnOptsD <: ColumnOptions[D],
    ColumnObjectD <: ColumnObject[D],
    RowTypeD <: RowType[D],
    CellType[d0, v] <: Cell[d0, v],
    StateType <: TableState[D],
    Layout
  ] =
    CustomHook[
      TableDefWithOptions[
        D,
        TableOptsD,
        TableInstanceD,
        ColumnOptsD,
        ColumnObjectD,
        RowTypeD,
        CellType,
        StateType,
        Layout
      ]
    ]
      .useMemoBy(_.cols)(_ => _.toJSArray)
      .useMemoBy(_.input.data)(_ => _.toJSArray)
      .buildReturning { (props, cols, RowTypes) =>
        val tableInstance =
          useTableJS[D, TableInstanceD, ColumnOptsD, ColumnObjectD, RowTypeD, CellType, StateType](
            props.modOpts(props.tableDef.Options(cols, RowTypes)),
            props.tableDef.plugins.toList.sorted.map(_.hook: PluginHook[D]): _*
          )
        Reusable
          .implicitly((cols, RowTypes, props.modOpts, tableInstance.state))
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
