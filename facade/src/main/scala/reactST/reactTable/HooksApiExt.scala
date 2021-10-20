package reactST.reactTable

import japgolly.scalajs.react._
import reactST.reactTable._
import reactST.reactTable.mod.Row
import reactST.reactTable.mod.TableState
import reactST.reactTable.mod.UseTableOptions

import scalajs.js

object HooksApiExt {
  sealed class Primary[Ctx, Step <: HooksApi.AbstractStep](api: HooksApi.Primary[Ctx, Step]) {

    final def useTable[
      D, // format: off
      TableOptsD <: UseTableOptions[D],
      TableInstanceType[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
      ColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
      ColumnD <: Column[D],
      RowD <: Row[D],
      CellType[d, col, row] <: Cell[d, col, row],
      TableStateD <: TableState[D],
      Layout // format: on
    ](
      tableDefWithOptions: TableDefWithOptions[
        D,
        TableOptsD,
        TableInstanceType,
        ColumnOptsType,
        ColumnD,
        RowD,
        CellType,
        TableStateD,
        Layout
      ]
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceType[D, ColumnD, RowD, CellType, TableStateD]
    ]] =
      useTableBy(_ => tableDefWithOptions)

    final def useTableBy[
      D, // format: off
      TableOptsD <: UseTableOptions[D],
      TableInstanceType[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
      ColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
      ColumnD <: Column[D],
      RowD <: Row[D],
      CellType[d, col, row] <: Cell[d, col, row],
      TableStateD <: TableState[D],
      Layout // format: on
    ](
      tableDefWithOptions: Ctx => TableDefWithOptions[
        D,
        TableOptsD,
        TableInstanceType,
        ColumnOptsType,
        ColumnD,
        RowD,
        CellType,
        TableStateD,
        Layout
      ]
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceType[D, ColumnD, RowD, CellType, TableStateD]
    ]] =
      api.customBy(ctx => TableHooks.useTableHook(tableDefWithOptions(ctx)))
  }

  final class Secondary[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ) extends Primary[Ctx, Step](api) {

    def useTableBy[
      D, // format: off
      TableOptsD <: UseTableOptions[D],
      TableInstanceType[d, col, row, cell[_, _, _], s] <: TableInstance[d, col, row, cell, s],
      ColumnOptsType[d, col, row, cell[_, _, _], s] <: ColumnOptions[d, col, row, cell, s],
      ColumnD <: Column[D],
      RowD <: Row[D],
      CellType[d, col, row] <: Cell[d, col, row],
      TableStateD <: TableState[D],
      Layout // format: on
    ](
      tableDefWithOptions: CtxFn[TableDefWithOptions[
        D,
        TableOptsD,
        TableInstanceType,
        ColumnOptsType,
        ColumnD,
        RowD,
        CellType,
        TableStateD,
        Layout
      ]]
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceType[D, ColumnD, RowD, CellType, TableStateD]
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
