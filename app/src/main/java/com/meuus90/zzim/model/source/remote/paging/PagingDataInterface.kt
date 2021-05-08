package com.meuus90.zzim.model.source.remote.paging

import kotlinx.coroutines.CoroutineScope


interface PagingDataInterface<Request, Response> {
    fun execute(viewModelScope: CoroutineScope, query: Request): Response
}