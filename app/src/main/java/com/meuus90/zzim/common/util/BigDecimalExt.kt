package com.meuus90.zzim.common.util

import java.math.BigDecimal
import java.text.NumberFormat

object BigDecimalExt {
    fun BigDecimal.formatNumberString(): String {
        val format = NumberFormat.getNumberInstance()
        return format.format(this)
    }
}