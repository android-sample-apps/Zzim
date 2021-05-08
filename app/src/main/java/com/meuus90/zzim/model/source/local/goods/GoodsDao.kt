package com.meuus90.zzim.model.source.local.goods

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.meuus90.zzim.model.data.response.Banner
import com.meuus90.zzim.model.source.local.BaseDao
import com.meuus90.zzim.model.data.response.Goods

@Dao
interface GoodsDao : BaseDao<Goods> {
    @Query("SELECT * FROM GoodsDoc")
    fun getGoodsPagingSource(): PagingSource<Int, Goods>

    @Query("SELECT * FROM GoodsDoc")
    fun getGoodsList(): List<Goods>

    @Query("DELETE FROM GoodsDoc")
    suspend fun clear()
}