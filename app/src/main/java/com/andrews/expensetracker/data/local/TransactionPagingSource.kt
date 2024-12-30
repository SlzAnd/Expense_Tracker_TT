package com.andrews.expensetracker.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andrews.expensetracker.data.mappers.toTransaction
import com.andrews.expensetracker.domain.model.TransactionsByDay

class TransactionPagingSource(
    private val dao: TransactionDao
) : PagingSource<Int, TransactionsByDay>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionsByDay> {
        val page = params.key ?: 0

        return try {
            val transactions = dao.loadTransactionsForPage(
                limit = params.loadSize,
                offset = page * params.loadSize
            )

            if (transactions.isEmpty()) {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = null
                )
            }

            val groupedTransactions = transactions
                .map { dto -> dto.toTransaction() }
                .groupBy { it.dateTime.toLocalDate() }
                .map { (date, transactionsForDay) ->
                    TransactionsByDay(
                        date = date,
                        transactions = transactionsForDay.sortedByDescending { it.dateTime }
                    )
                }

            LoadResult.Page(
                data = groupedTransactions,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (transactions.size < params.loadSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TransactionsByDay>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}