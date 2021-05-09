package com.meuus90.zzim.model.source.remote.api

import com.meuus90.zzim.common.network.ApiResponse
import com.meuus90.zzim.model.data.RequestTest
import com.meuus90.zzim.model.data.ResponseTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

open class MockBaseAPI(private val isMakeError: Boolean) {
    val requestTest = RequestTest.getInstance()
    val responseTest = ResponseTest.getInstance()

    fun <T> getFlowResponse(body: T): Flow<ApiResponse<T>> {
        return flow {
            if (isMakeError) emit(ApiResponse.create<T>(Throwable()))
            else emit(ApiResponse.create(Response.success(body)))
        }
    }
}