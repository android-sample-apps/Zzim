package com.meuus90.zzim.model.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.meuus90.zzim.model.data.GoodsDataModel
import com.meuus90.zzim.model.source.remote.api.RestAPI

class ProductPagingSource(private val restAPI: RestAPI) : PagingSource<Int, GoodsDataModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GoodsDataModel> {
        return try {
            val goods: List<GoodsDataModel> = if (params.key == null || params.key == 0) {
                val home = restAPI.getHome()

                val list = mutableListOf<GoodsDataModel>(GoodsDataModel.Header(home.banners))
                list.addAll(home.goods.map {
                    GoodsDataModel.Item(it)
                })
                list
            } else {
                val home = restAPI.getNextGoods(params.key!!)

                home.goods.map {
                    GoodsDataModel.Item(it)
                }
            }
            val last = goods.last()
            LoadResult.Page(
                data = goods, prevKey = null, nextKey =
                if (last is GoodsDataModel.Item) last.goods.id else null
            )
        } catch (e: Exception) {
            LoadResult.Error(Throwable("Paging Error"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GoodsDataModel>): Int {
        return 0
    }
}