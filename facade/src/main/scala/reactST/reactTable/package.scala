package reactST

// import reactST.reactTable.mod._

package object reactTable extends HooksApiExt {
  type TableInstance[d, col, row, cell[_, _, _], s]            =
    facade.tableInstance.TableInstance[d, col, row, cell, s]
  type UseSortByTableInstance[d, col, row, cell[_, _, _], s]   =
    facade.tableInstance.UseSortByTableInstance[d, col, row, cell, s]
  type UseExpandedTableInstance[d, col, row, cell[_, _, _], s] =
    facade.tableInstance.UseExpandedTableInstance[d, col, row, cell, s]

  type ColumnOptions[d, col, row, cell[_, _, _], s]                 =
    facade.columnOptions.ColumnOptions[d, col, row, cell, s]
  type UseSortByColumnOptions[d, col, row, cell[_, _, _], s]        =
    facade.columnOptions.UseSortByColumnOptions[d, col, row, cell, s]
  type UseResizeColumnsColumnOptions[d, col, row, cell[_, _, _], s] =
    facade.columnOptions.UseSortByColumnOptions[d, col, row, cell, s]

  // format: off
  type ColumnOptionsV[d, v, col, row, cell[_, _, _], s, Col <: ColumnOptions[d, col, row, cell, s]] =
    Col with facade.columnOptions.ColumnValueOptions[d, v, col, row, cell, s]
  // format: on

  type ColumnGroupOptions[d, col, row, cell[_, _, _], s] =
    facade.columnOptions.ColumnOptions[d, col, row, cell, s]

  type Column[d]                 = facade.column.Column[d]
  type UseSortByColumn[d]        = facade.column.UseSortByColumn[d]
  type UseResizeColumnsColumn[d] = facade.column.UseResizeColumnsColumn[d]

  type Cell[d, col, row] = facade.cell.Cell[d, col, row]
}

/*
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
 */
