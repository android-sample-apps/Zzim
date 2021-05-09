package com.meuus90.zzim.model.source.remote.api

import com.meuus90.zzim.model.data.response.Home
import com.meuus90.zzim.model.data.response.NextGoods

class MockRestAPI(isMakeError: Boolean = false) : RestAPI, MockBaseAPI(isMakeError) {
    override suspend fun getHome(): Home {
        return responseTest.mockHome0
    }

    override suspend fun getNextGoods(lastId: Int): NextGoods {
        return responseTest.mockNextGoods0
    }
}