package com.meuus90.zzim.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.source.local.Cache
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

    fun getFavoriteList() = db.goodsDao().getGoodsList()

    fun addFavorite(goods: Goods) {
        viewModelScope.launch {
            db.goodsDao().insert(goods)
        }
    }

    fun removeFavorite(goods: Goods) {
        viewModelScope.launch {
            db.goodsDao().deleteGoods(goods.id)
        }
    }

    fun updateFavorites(goods: List<Goods>) {
        viewModelScope.launch {
            db.goodsDao().insert(goods.toMutableList())
        }
    }
}