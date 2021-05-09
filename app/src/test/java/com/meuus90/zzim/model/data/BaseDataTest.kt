package com.meuus90.zzim.model.data

import com.meuus90.zzim.model.data.response.Home
import com.meuus90.zzim.testkit.DataClassTestTool
import org.junit.Assert
import org.junit.Test

class BaseDataTest {
    @Test
    fun QueryTest() {
        val mockData = ResponseTest.mock.mockHome0

        Assert.assertTrue(
            DataClassTestTool.checkProperties(
                mockData,
                BaseData.deepCopy(mockData, Home::class.java)!!
            )
        )
    }
}