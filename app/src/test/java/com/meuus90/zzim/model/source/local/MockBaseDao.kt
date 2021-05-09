package com.meuus90.zzim.model.source.local

import com.meuus90.zzim.model.data.RequestTest
import com.meuus90.zzim.model.data.ResponseTest

open class MockBaseDao<T> : BaseDao<T> {
    val requestTest = RequestTest.getInstance()
    val responseTest = ResponseTest.getInstance()

    var result: Any? = null

    override suspend fun insert(vararg obj: T) {
        result = obj
    }

    override suspend fun insert(obj: T) {
        result = obj
    }

    override suspend fun insert(obj: MutableList<T>) {
        result = obj
    }

    override suspend fun insert(obj: ArrayList<T>) {
        result = obj
    }

    override suspend fun delete(obj: T) {
        result = obj
    }

    override suspend fun update(vararg obj: T) {
        result = obj
    }

    override suspend fun update(obj: T) {
        result = obj
    }

    override suspend fun update(obj: MutableList<T>) {
        result = obj
    }
}