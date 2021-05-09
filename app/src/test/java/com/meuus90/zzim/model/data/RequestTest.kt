package com.meuus90.zzim.model.data

import com.meuus90.zzim.model.data.request.Query
import com.meuus90.zzim.testkit.DataClassTestTool
import org.junit.Assert
import org.junit.Test

class RequestTest {
    companion object {
        val mock by lazy { RequestTest() }
        fun getInstance() = mock
    }

    val mockQuery0 = Query(listOf())
    val mockQuery1 = Query(listOf())

    @Test
    fun QueryTest() {
        Assert.assertTrue(
            DataClassTestTool.checkProperties(
                mockQuery0,
                mockQuery1
            )
        )
    }
}