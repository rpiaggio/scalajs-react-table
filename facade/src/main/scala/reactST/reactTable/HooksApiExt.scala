package reactST.reactTable

import japgolly.scalajs.react._
import reactST.reactTable._
import reactST.reactTable.mod._

import scalajs.js

object HooksApiExt {
  sealed class Primary[Ctx, Step <: HooksApi.AbstractStep](api: HooksApi.Primary[Ctx, Step]) {

    final def useTable[
      D,
      TableOptsD <: UseTableOptions[D],
      ColumnOptsD <: ColumnOptions[D],
      ColumnObjectD <: ColumnObject[D],
      RowD <: Row[D],
      CellD <: Cell[D, js.Any],
      TableStateD <: TableState[D],
      TableInstanceD[_, _, _, _, _, _] <: TableInstanceTyped[_, _, _, _, _, _],
      Layout
    ](
      tableDefWithOptions: TableDefWithOptions[
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
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceD[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD]
    ]] =
      useTableBy(_ => tableDefWithOptions)

    final def useTableBy[
      D,
      TableOptsD <: UseTableOptions[D],
      ColumnOptsD <: ColumnOptions[D],
      ColumnObjectD <: ColumnObject[D],
      RowD <: Row[D],
      CellD <: Cell[D, js.Any],
      TableStateD <: TableState[D],
      TableInstanceD[_, _, _, _, _, _] <: TableInstanceTyped[_, _, _, _, _, _],
      Layout
    ](
      tableDefWithOptions: Ctx => TableDefWithOptions[
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
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceD[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD]
    ]] =
      api.customBy(ctx => TableHooks.useTableHook(tableDefWithOptions(ctx)))
  }

  final class Secondary[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ) extends Primary[Ctx, Step](api) {

    def useTableBy[
      D,
      TableOptsD <: UseTableOptions[D],
      ColumnOptsD <: ColumnOptions[D],
      ColumnObjectD <: ColumnObject[D],
      RowD <: Row[D],
      CellD <: Cell[D, js.Any],
      TableStateD <: TableState[D],
      TableInstanceD[_, _, _, _, _, _] <: TableInstanceTyped[_, _, _, _, _, _],
      Layout
    ](
      tableDefWithOptions: CtxFn[TableDefWithOptions[
        D,
        TableOptsD,
        ColumnOptsD,
        ColumnObjectD,
        RowD,
        CellD,
        TableStateD,
        TableInstanceD,
        Layout
      ]]
    )(implicit
      step:                Step
    ): step.Next[Reusable[
      TableInstanceD[D, ColumnOptsD, ColumnObjectD, RowD, CellD, TableStateD]
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
