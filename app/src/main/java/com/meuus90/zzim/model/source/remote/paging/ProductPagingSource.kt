package com.meuus90.zzim.model.source.remote.paging

import androidx.paging.PagingSource
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.source.remote.api.RestAPI
import javax.inject.Inject
import javax.inject.Singleton


class ProductPagingSource(private val restAPI: RestAPI) : PagingSource<String, Goods>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Goods> {
        return try {
            val goods: List<Goods> = if (params.key == null) {
                val home = restAPI.getHome()
                home.goods
            } else {
                val home = restAPI.getNextGoods(params.key!!)
                home.goods
            }

            LoadResult.Page(
                data = goods, prevKey = null, nextKey = goods.last().id
            )
        } catch (e: Exception) {
            LoadResult.Error(Throwable("Paging Error"))
        }
    }
}