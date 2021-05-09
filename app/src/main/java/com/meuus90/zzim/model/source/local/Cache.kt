package com.meuus90.zzim.model.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.meuus90.zzim.common.constant.AppConfig
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.source.local.converter.BigDecimalTypeConverter
import com.meuus90.zzim.model.source.local.converter.StringListTypeConverter
import com.meuus90.zzim.model.source.local.goods.GoodsDao

@Database(
    entities = [Goods::class],
    exportSchema = false,
    version = AppConfig.roomVersionCode
)
@TypeConverters(BigDecimalTypeConverter::class, StringListTypeConverter::class)
abstract class Cache : RoomDatabase() {
    abstract fun goodsDao(): GoodsDao
}