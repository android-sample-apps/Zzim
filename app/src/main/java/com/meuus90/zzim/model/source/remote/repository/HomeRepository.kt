package com.meuus90.zzim.model.source.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.meuus90.zzim.model.data.request.Query
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.source.local.Cache
import com.meuus90.zzim.model.source.remote.api.RestAPI
import com.meuus90.zzim.model.source.remote.paging.PagingDataInterface
import com.meuus90.zzim.model.source.remote.paging.ProductPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository
@Inject
constructor(private val db: Cache, private val restAPI: RestAPI) :
    PagingDataInterface<Query, Flow<PagingData<Goods>>> {

    override fun execute(viewModelScope: CoroutineScope, query: Query): Flow<PagingData<Goods>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 10
            )
        ) {
            ProductPagingSource(restAPI)
        }.flow.cachedIn(viewModelScope)
    }

    fun clearCache() = runBlocking {
        db.bannerDao().clear()
        db.goodsDao().clear()
    }
}
