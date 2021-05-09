package com.meuus90.zzim.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.source.local.Cache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteViewModel
@Inject
constructor(private val db: Cache) : ViewModel() {
    val goods = Pager(
        config = PagingConfig(10)
    ) {
        db.goodsDao().getGoodsPagingSource()
    }.flow

    val goodsFlow = db.goodsDao().getGoodsFlow()

    fun addFavorite(goods: Goods) {
        GlobalScope.launch(Dispatchers.IO) {
            db.goodsDao().insert(goods)
        }
    }

    fun removeFavorite(goods: Goods) {
        GlobalScope.launch(Dispatchers.IO) {
            db.goodsDao().deleteGoods(goods.id)
        }
    }
}