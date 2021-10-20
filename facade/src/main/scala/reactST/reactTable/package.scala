package reactST

import reactST.reactTable.mod._

package object reactTable extends HooksApiExt {
  type TableInstance[d, col[d0], row, cell[d0, v], s]          =
    facade.tableInstance.TableInstance[d, col, row, cell, s]
  type UseSortByTableInstance[d, col[d0], row, cell[d0, v], s] =
    facade.tableInstance.UseSortByTableInstance[d, col, row, cell, s]
  // type UseExpandedTableInstance[d, co, col[d0, co, RowType, cell[d0, v], s], RowType, cell[
  //   d0,
  //   v
  // ], s]                                               =
  //   facade.tableInstance.UseExpandedTableInstance[d, co, col, RowType, cell, s]

  type ColumnOptions[d, v, col[d0], row, cell[d0, v], s] =
    facade.columnOptions.ColumnOptions[d, v, col, row, cell, s]
  // type UseSortByColumnOptions[d, co, col[d0, co, RowType, cell[d0, v], s], RowType, cell[
  //   d0,
  //   v
  // ], s]                                               =
  //   facade.column.UseSortByColumnOptions[d, co, col, RowType, cell, s]
  // type UseResizeColumnsColumnOptions[d, co, col[d0, co, RowType, cell[d0, v], s], RowType, cell[
  //   d0,
  //   v
  // ], s]                                               =
  //   facade.column.UseSortByColumnOptions[d, co, col, RowType, cell, s]

  // type ColumnOptions[D]                                  =
  //   ColumnInterface[D]
  //     with reactST.reactTable.anon.IdIdType[D]
  //     with reactST.reactTable.anon.`0`[D]
  //     with reactST.reactTable.anon.`1`[D]
  //     with ColumnFooter[D]
  // type ColumnValueOptions[D, V, Col <: ColumnOptions[D]] =
  //   Col with ColumnInterfaceBasedOnValue[D, V]

  type ColumnGroupOptions[D] = ColumnGroup[D] with ColumnFooter[D]

  type Column[d] = facade.column.Column[d]
}
