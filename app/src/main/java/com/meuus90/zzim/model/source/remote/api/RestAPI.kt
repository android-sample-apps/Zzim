package com.meuus90.zzim.model.source.remote.api

import com.meuus90.zzim.model.data.response.Home
import com.meuus90.zzim.model.data.response.NextGoods
import retrofit2.http.GET
import retrofit2.http.Query

interface RestAPI {
    @GET("home")
    suspend fun getHome(): Home

    @GET("home/goods")
    suspend fun getNextGoods(@Query("lastId") lastId: Int): NextGoods
}