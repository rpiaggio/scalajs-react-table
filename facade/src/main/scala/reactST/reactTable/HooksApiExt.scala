package reactST.reactTable

import japgolly.scalajs.react._
import reactST.reactTable._
import reactST.reactTable.mod.Row
import reactST.reactTable.mod.Cell
import reactST.reactTable.mod.TableState
import reactST.reactTable.mod.UseTableOptions

import scalajs.js

object HooksApiExt {
  sealed class Primary[Ctx, Step <: HooksApi.AbstractStep](api: HooksApi.Primary[Ctx, Step]) {

    final def useTable[
      D,
      TableOptsD <: UseTableOptions[D],
      TableInstanceType[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
      ColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: ColumnOptions[d, v, col, row, cell, s],
      ColumnType[d] <: Column[d],
      RowD <: Row[D],
      CellType[d, v] <: Cell[d, v],
      TableStateD <: TableState[D],
      Layout
    ](
      tableDefWithOptions: TableDefWithOptions[
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
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceType[D, ColumnType, RowD, CellType, TableStateD]
    ]] =
      useTableBy(_ => tableDefWithOptions)

    final def useTableBy[
      D,
      TableOptsD <: UseTableOptions[D],
      TableInstanceType[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
      ColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: ColumnOptions[d, v, col, row, cell, s],
      ColumnType[d] <: Column[d],
      RowD <: Row[D],
      CellType[d, v] <: Cell[d, v],
      TableStateD <: TableState[D],
      Layout
    ](
      tableDefWithOptions: Ctx => TableDefWithOptions[
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
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceType[D, ColumnType, RowD, CellType, TableStateD]
    ]] =
      api.customBy(ctx => TableHooks.useTableHook(tableDefWithOptions(ctx)))
  }

  final class Secondary[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ) extends Primary[Ctx, Step](api) {

    def useTableBy[
      D,
      TableOptsD <: UseTableOptions[D],
      TableInstanceType[d, col[d0], row, cell[d0, v], s] <: TableInstance[d, col, row, cell, s],
      ColumnOptsType[d, v, col[d0], row, cell[d0, v], s] <: ColumnOptions[d, v, col, row, cell, s],
      ColumnType[d] <: Column[d],
      RowD <: Row[D],
      CellType[d, v] <: Cell[d, v],
      TableStateD <: TableState[D],
      Layout
    ](
      tableDefWithOptions: CtxFn[TableDefWithOptions[
        D,
        TableOptsD,
        TableInstanceType,
        ColumnOptsType,
        ColumnType,
        RowD,
        CellType,
        TableStateD,
        Layout
      ]]
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceType[D, ColumnType, RowD, CellType, TableStateD]
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
