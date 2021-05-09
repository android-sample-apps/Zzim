package com.meuus90.zzim.model.source.local.converter

import org.junit.Assert
import org.junit.Test

class StringListTypeConverterTest {
    @Test
    fun listToStringTest() {
        val str = StringListTypeConverter().listToString(listOf("test01", "test02"))

        Assert.assertEquals(str, "[\"test01\",\"test02\"]")
    }

    @Test
    fun stringToListTest() {
        val list = StringListTypeConverter().stringToList("[\"test01\",\"test02\"]")

        Assert.assertEquals(list, listOf("test01", "test02"))
    }
}