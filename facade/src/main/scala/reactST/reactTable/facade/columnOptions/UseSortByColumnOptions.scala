package reactST.reactTable.facade.columnOptions

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{ JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName }
import reactST.reactTable.mod.SortByFn
import reactST.reactTable.mod.DefaultSortTypes

@js.native
trait UseSortByColumnOptions[D, ColumnD, RowD, CellType[d, col, row], TableStateD]
    extends ColumnOptions[D, ColumnD, RowD, CellType, TableStateD] {
  var defaultCanSort: js.UndefOr[Boolean] = js.native

  var disableSortBy: js.UndefOr[Boolean] = js.native

  var sortDescFirst: js.UndefOr[Boolean] = js.native

  var sortInverted: js.UndefOr[Boolean] = js.native

  var sortType: js.UndefOr[SortByFn[D] | DefaultSortTypes | String] = js.native
}

object UseSortByColumnOptions {

  // format: off
  implicit class ColumnOptionsMutableBuilder[D, V, ColumnD, RowD, CellType[d, col, row], TableStateD, Self]( // format: on
    val colOpts: Self
      with UseSortByColumnOptions[D, ColumnD, RowD, CellType, TableStateD]
      with ColumnValueOptions[D, V, ColumnD, RowD, CellType, TableStateD]
  ) extends AnyVal {

    /**
     * Sets the sorting for the column based on a function on its value.
     *
     * @param f
     *   A function from the value type to the target type.
     * @param ordering
     *   An implicit ordering for the target type.
     * @param evidence
     *   Evidence that this column is sortable.
     */
    def setSortByFn[U](f: V => U)(implicit ordering: Ordering[U]): Self = {
      val sbfn: SortByFn[D] = (d1, d2, col, _) =>
        ordering
          .compare(f(d1.values(col.asInstanceOf[String]).asInstanceOf[V]),
                   f(d2.values(col.asInstanceOf[String]).asInstanceOf[V])
          )
          .toDouble
      colOpts.setSortType(sbfn).asInstanceOf[Self]
    }

    /**
     * Sets the sorting for the column based on its value.
     *
     * @param ordering
     *   An implicit ordering for the value type.
     * @param evidence
     *   Evidence that this column is sortable.
     */
    def setSortByAuto(implicit ordering: Ordering[V]): Self = setSortByFn(identity)

    // @scala.inline
    // implicit class UseSortByColumnOptionsMutableBuilder[Self <: UseSortByColumnOptions[
    //   _
    // ], D /* <: js.Object */ ](val x: Self with UseSortByColumnOptions[D])
    //     extends AnyVal {

    //   @scala.inline
    //   def setDefaultCanSort(value: Boolean): Self =
    //     StObject.set(x, "defaultCanSort", value.asInstanceOf[js.Any])

    //   @scala.inline
    //   def setDefaultCanSortUndefined: Self = StObject.set(x, "defaultCanSort", js.undefined)

    //   @scala.inline
    //   def setDisableSortBy(value: Boolean): Self =
    //     StObject.set(x, "disableSortBy", value.asInstanceOf[js.Any])

    //   @scala.inline
    //   def setDisableSortByUndefined: Self = StObject.set(x, "disableSortBy", js.undefined)

    //   @scala.inline
    //   def setSortDescFirst(value: Boolean): Self =
    //     StObject.set(x, "sortDescFirst", value.asInstanceOf[js.Any])

    //   @scala.inline
    //   def setSortDescFirstUndefined: Self = StObject.set(x, "sortDescFirst", js.undefined)

    //   @scala.inline
    //   def setSortInverted(value: Boolean): Self =
    //     StObject.set(x, "sortInverted", value.asInstanceOf[js.Any])

    //   @scala.inline
    //   def setSortInvertedUndefined: Self = StObject.set(x, "sortInverted", js.undefined)

    @scala.inline
    def setSortType(value: SortByFn[D] | DefaultSortTypes | String): Self = {
      colOpts.sortType = value
      colOpts
    }

    //   @scala.inline
    //   def setSortTypeFunction4(
    //     value: (
    //       /* rowA */ Row[D], /* rowB */ Row[D], /* columnId */ IdType[D], /* desc */ js.UndefOr[
    //         Boolean
    //       ]
    //     ) => Double
    //   ): Self = StObject.set(x, "sortType", js.Any.fromFunction4(value))

    //   @scala.inline
    //   def setSortTypeUndefined: Self = StObject.set(x, "sortType", js.undefined)
  }
}
