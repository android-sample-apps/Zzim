package com.meuus90.zzim.model.source.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.source.local.goods.GoodsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockGoodsDao : GoodsDao, MockBaseDao<Goods>() {
    class MockPagingSource : PagingSource<Int, Goods>() {
        override fun getRefreshKey(state: PagingState<Int, Goods>): Int {
            return 0
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Goods> {
            return LoadResult.Page(listOf(), null, null)
        }
    }

    override fun getGoodsPagingSource(): PagingSource<Int, Goods> {
        return MockPagingSource()
    }

    override fun getGoodsFlow(): Flow<List<Goods>> {
        return flowOf(listOf(responseTest.mockGoods0))
    }

    override fun getGoodsList(): List<Goods> {
        return listOf(responseTest.mockGoods0)
    }

    override suspend fun deleteGoods(deleteId: Int) {
        result = deleteId
    }

    override suspend fun clear() {
        result = null
    }
}