package reactST

import reactST.reactTable.mod._

package object reactTable extends HooksApiExt {
  type TableInstance[d, co, col, row, cell, s]            =
    facade.tableInstance.TableInstance[d, co, col, row, cell, s]
  type UseSortByTableInstance[d, co, col, row, cell, s]   =
    facade.tableInstance.UseSortByTableInstance[d, co, col, row, cell, s]
  type UseExpandedTableInstance[d, co, col, row, cell, s] =
    facade.tableInstance.UseExpandedTableInstance[d, co, col, row, cell, s]

  type ColumnOptions[D]                                  =
    ColumnInterface[D]
      with reactST.reactTable.anon.IdIdType[D]
      with reactST.reactTable.anon.`0`[D]
      with reactST.reactTable.anon.`1`[D]
      with ColumnFooter[D]
  type ColumnValueOptions[D, V, Col <: ColumnOptions[D]] =
    Col with ColumnInterfaceBasedOnValue[D, V]
  type ColumnGroupOptions[D]                             = ColumnGroup[D] with ColumnFooter[D]
  type ColumnObject[D]                                   = ColumnInstance[D] with UseTableColumnFooter[D]
}
