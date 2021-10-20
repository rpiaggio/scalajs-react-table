package reactST.reactTable

import japgolly.scalajs.react._
import reactST.reactTable._
import reactST.reactTable.mod.RowType
import reactST.reactTable.mod.Cell
import reactST.reactTable.mod.TableState
import reactST.reactTable.mod.UseTableOptions

import scalajs.js

object HooksApiExt {
  sealed class Primary[Ctx, Step <: HooksApi.AbstractStep](api: HooksApi.Primary[Ctx, Step]) {

    final def useTable[
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
    ](
      tableDefWithOptions: TableDefWithOptions[
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
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceD[D, ColumnOptsD, ColumnObjectD, RowTypeD, CellType, StateType]
    ]] =
      useTableBy(_ => tableDefWithOptions)

    final def useTableBy[
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
    ](
      tableDefWithOptions: Ctx => TableDefWithOptions[
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
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceD[D, ColumnOptsD, ColumnObjectD, RowTypeD, CellType, StateType]
    ]] =
      api.customBy(ctx => TableHooks.useTableHook(tableDefWithOptions(ctx)))
  }

  final class Secondary[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ) extends Primary[Ctx, Step](api) {

    def useTableBy[
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
    ](
      tableDefWithOptions: CtxFn[TableDefWithOptions[
        D,
        TableOptsD,
        TableInstanceD,
        ColumnOptsD,
        ColumnObjectD,
        RowTypeD,
        CellType,
        StateType,
        Layout
      ]]
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceD[D, ColumnOptsD, ColumnObjectD, RowTypeD, CellType, StateType]
    ]] =
      super.useTableBy(step.squash(tableDefWithOptions)(_))

  }
}

trait HooksApiExt {
  import HooksApiExt._

  implicit def hooksExtUseTable1[Ctx, Step <: HooksApi.AbstractStep](
    api: HooksApi.Primary[Ctx, Step]
  ): Primary[Ctx, Step] =
    new Primary(api)

  implicit def hooksExtUseTable2[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ): Secondary[Ctx, CtxFn, Step] =
    new Secondary(api)
}
