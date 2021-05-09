package com.meuus90.zzim.model.source.local.converter

import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class BigDecimalTypeConverterTest {
    @Test
    fun bigDecimalToStringTest() {
        val str = BigDecimalTypeConverter().bigDecimalToString(BigDecimal("123456"))

        Assert.assertEquals(str, "123456")
    }

    @Test
    fun stringToBigDecimalTest() {
        val bd = BigDecimalTypeConverter().stringToBigDecimal("123456")

        Assert.assertEquals(bd, BigDecimal("123456"))
    }
}