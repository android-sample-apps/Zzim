package com.meuus90.zzim.common.util

import com.meuus90.zzim.common.util.BigDecimalExt.formatNumberString
import org.junit.Assert
import org.junit.Test

class BigDecimalExtTest {
    @Test
    fun formatNumberStringTest() {
        val amount = 1000000.toBigDecimal()
        val converted = amount.formatNumberString()

        Assert.assertEquals(converted, "1,000,000")
    }
}