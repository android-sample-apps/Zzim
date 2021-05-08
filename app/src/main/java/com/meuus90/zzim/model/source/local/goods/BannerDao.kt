package com.meuus90.zzim.model.source.local.goods

import androidx.room.Dao
import androidx.room.Query
import com.meuus90.zzim.model.data.response.Banner
import com.meuus90.zzim.model.source.local.BaseDao

@Dao
interface BannerDao : BaseDao<Banner> {
    @Query("SELECT * FROM BannerDoc")
    fun getBanners(): List<Banner>

    @Query("DELETE FROM BannerDoc")
    suspend fun clear()
}