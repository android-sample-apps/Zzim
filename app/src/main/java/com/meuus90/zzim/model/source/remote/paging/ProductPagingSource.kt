package com.meuus90.zzim.model.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.meuus90.zzim.model.data.GoodsDataModel
import com.meuus90.zzim.model.source.remote.api.RestAPI

class ProductPagingSource(private val restAPI: RestAPI) : PagingSource<Int, GoodsDataModel>() {
    companion object {
        const val ERROR_MESSAGE_PAGING_END = "Paging End"
    }

    private var isEnd = false
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GoodsDataModel> {
        return try {
            if (params.key == null || params.key == 0) {
                val home = restAPI.getHome()
                if (home.goods.isEmpty()) isEnd = true

                val list =
                    mutableListOf<GoodsDataModel>(GoodsDataModel.Header(home.banners)).apply {
                        addAll(home.goods.map {
                            GoodsDataModel.Item(it)
                        })
                    }
                val last = list.lastOrNull()

                LoadResult.Page(
                    data = list, prevKey = null, nextKey =
                    if (last is GoodsDataModel.Item) last.goods.id else null
                )
            } else {
                if (!isEnd) {
                    val home = restAPI.getNextGoods(params.key!!)
                    if (home.goods.isEmpty()) isEnd = true

                    val list = home.goods.map { GoodsDataModel.Item(it) }
                    val last = list.lastOrNull()

                    LoadResult.Page(
                        data = list, prevKey = null, nextKey =
                        if (last is GoodsDataModel.Item) last.goods.id else null
                    )
                } else {
                    LoadResult.Error(Throwable(ERROR_MESSAGE_PAGING_END))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GoodsDataModel>): Int {
        isEnd = false
        return 0
    }
}