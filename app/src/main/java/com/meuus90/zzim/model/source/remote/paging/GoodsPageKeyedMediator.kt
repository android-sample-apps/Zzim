package com.meuus90.zzim.model.source.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.source.local.Cache
import com.meuus90.zzim.model.source.local.goods.BannerDao
import com.meuus90.zzim.model.source.local.goods.GoodsDao
import com.meuus90.zzim.model.source.remote.api.RestAPI
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GoodsPageKeyedMediator(
    private val db: Cache,
    private val restAPI: RestAPI
) : RemoteMediator<Int, Goods>() {
    private val goodsDao: GoodsDao = db.goodsDao()
    private val bannerDao: BannerDao = db.bannerDao()
    private var isEnd = false
    private var lastId = ""

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Goods>
    ): MediatorResult {
        try {
            when (loadType) {
                LoadType.REFRESH -> {
                    isEnd = false
                    lastId = ""
//                    db.withTransaction {
//                        goodsDao.clear()
//                        bannerDao.clear()
//                    }
                }
                LoadType.PREPEND -> {
                }
                LoadType.APPEND -> {
                    if (!isEnd) {
                        if (lastId.isEmpty()) {
                            val home = restAPI.getHome()

                            if (home.goods.isEmpty()) {
                                isEnd = true
                                val e = EmptyResultException()
                                Timber.e(e)
                                return MediatorResult.Error(e)
                            } else {
                                lastId = home.goods.last().id
                            }

//                            db.withTransaction { bannerDao.insert(home.banners) }
//                            db.withTransaction { goodsDao.insert(home.goods) }
                        } else {
                            val goods = restAPI.getNextGoods(lastId = lastId)

                            if (goods.goods.isEmpty()) {
                                isEnd = true
                                val e = EmptyResultException()
                                Timber.e(e)
                                return MediatorResult.Error(e)
                            } else {
                                lastId = goods.goods.last().id
                            }

//                            db.withTransaction { goodsDao.insert(goods.goods) }
                        }
                    }
                }
            }
            return MediatorResult.Success(endOfPaginationReached = isEnd)
        } catch (e: IOException) {
            Timber.e(e)
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            Timber.e(e)
            return MediatorResult.Error(e)
        } catch (e: Exception) {
            Timber.e(e)
            return MediatorResult.Error(e)
        }
    }

    class EmptyResultException : Exception()
}
