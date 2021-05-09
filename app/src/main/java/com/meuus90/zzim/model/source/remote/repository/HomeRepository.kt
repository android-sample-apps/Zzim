package com.meuus90.zzim.model.source.remote.repository

import androidx.paging.*
import com.meuus90.zzim.model.data.GoodsDataModel
import com.meuus90.zzim.model.data.response.Banner
import com.meuus90.zzim.model.source.remote.api.RestAPI
import com.meuus90.zzim.model.source.remote.paging.ProductPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository
@Inject
constructor(private val restAPI: RestAPI) {
    fun execute(viewModelScope: CoroutineScope): Flow<PagingData<GoodsDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 10
            )
        ) {
            ProductPagingSource(restAPI)
        }.flow.cachedIn(viewModelScope)
    }
}