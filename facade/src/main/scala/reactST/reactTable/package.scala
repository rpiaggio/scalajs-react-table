package reactST

import reactST.reactTable.mod._

package object reactTable extends HooksApiExt {
  type TableInstance[d, col, row, cell[d0, v], s]            =
    facade.tableInstance.TableInstance[d, col, row, cell, s]
  type UseSortByTableInstance[d, col, row, cell[d0, v], s]   =
    facade.tableInstance.UseSortByTableInstance[d, col, row, cell, s]
  type UseExpandedTableInstance[d, col, row, cell[d0, v], s] =
    facade.tableInstance.UseExpandedTableInstance[d, col, row, cell, s]

  type ColumnOptions[d, v, col, row, cell[d0, v], s]          =
    facade.columnOptions.ColumnOptions[d, v, col, row, cell, s]
  type UseSortByColumnOptions[d, v, col, row, cell[d0, v], s] =
    facade.columnOptions.UseSortByColumnOptions[d, v, col, row, cell, s]

  type ColumnGroupOptions[d, v, col, row, cell[d0, v], s] =
    facade.columnOptions.ColumnOptions[d, v, col, row, cell, s]

  type Column[d]          = facade.column.Column[d]
  type UseSortByColumn[d] = facade.column.UseSortByColumn[d]
}
