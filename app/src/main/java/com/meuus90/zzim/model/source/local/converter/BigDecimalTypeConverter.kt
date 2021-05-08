package com.meuus90.zzim.model.source.local.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalTypeConverter {
    @TypeConverter
    fun bigDecimalToString(value: BigDecimal?): String? {
        return value?.toPlainString()
    }

    @TypeConverter
    fun stringToBigDecimal(value: String?): BigDecimal? {
        return if (value.isNullOrEmpty()) null
        else value.toBigDecimal()
    }
}