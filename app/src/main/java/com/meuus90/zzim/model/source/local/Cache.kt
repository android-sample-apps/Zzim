package com.meuus90.zzim.model.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.meuus90.zzim.model.source.local.goods.GoodsDao
import com.meuus90.zzim.common.AppConfig
import com.meuus90.zzim.model.data.response.Banner
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.source.local.converter.BigDecimalTypeConverter
import com.meuus90.zzim.model.source.local.converter.StringListTypeConverter
import com.meuus90.zzim.model.source.local.goods.BannerDao

@Database(
    entities = [Goods::class, Banner::class],
    exportSchema = false,
    version = AppConfig.roomVersionCode
)
@TypeConverters(BigDecimalTypeConverter::class, StringListTypeConverter::class)
abstract class Cache : RoomDatabase() {
    abstract fun goodsDao(): GoodsDao
    abstract fun bannerDao(): BannerDao
}