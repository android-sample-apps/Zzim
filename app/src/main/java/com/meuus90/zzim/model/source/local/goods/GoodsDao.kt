package com.meuus90.zzim.model.source.local.goods

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.source.local.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodsDao : BaseDao<Goods> {
    @Query("SELECT * FROM GoodsDoc")
    fun getGoodsPagingSource(): PagingSource<Int, Goods>

    @Query("SELECT * FROM GoodsDoc")
    fun getGoodsFlow(): Flow<List<Goods>>

    @Query("SELECT * FROM GoodsDoc")
    fun getGoodsList(): List<Goods>

    @Query("DELETE FROM GoodsDoc WHERE id = :deleteId")
    suspend fun deleteGoods(deleteId: Int)

    @Query("DELETE FROM GoodsDoc")
    suspend fun clear()
}