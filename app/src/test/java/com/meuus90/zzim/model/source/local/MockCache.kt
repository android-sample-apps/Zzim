package com.meuus90.zzim.model.source.local

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.meuus90.zzim.model.source.local.goods.GoodsDao
import org.mockito.Mockito

class MockCache : Cache() {
    val mockGoodsDao = MockGoodsDao()

    override fun goodsDao(): GoodsDao {
        return mockGoodsDao
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        return Mockito.mock(SupportSQLiteOpenHelper::class.java) as SupportSQLiteOpenHelper
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return Mockito.mock(InvalidationTracker::class.java) as InvalidationTracker
    }

    override fun clearAllTables() {
    }
}