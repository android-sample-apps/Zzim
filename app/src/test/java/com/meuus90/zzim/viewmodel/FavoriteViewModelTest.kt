package com.meuus90.zzim.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.meuus90.zzim.model.data.ResponseTest
import com.meuus90.zzim.model.source.local.MockCache
import com.meuus90.zzim.model.source.local.MockGoodsDao
import com.meuus90.zzim.testkit.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: FavoriteViewModel

    val responseTest = ResponseTest.getInstance()

    val cache = MockCache()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = FavoriteViewModel(cache)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getFavoriteListTest() {
        Assert.assertEquals(viewModel.getFavoriteList(), listOf(responseTest.mockGoods0))
    }

    @Test
    fun addFavoriteTest() {
        viewModel.addFavorite(responseTest.mockGoods0)
        Assert.assertEquals((cache.goodsDao() as MockGoodsDao).result, responseTest.mockGoods0)
    }

    @Test
    fun removeFavoriteTest() {
        viewModel.removeFavorite(responseTest.mockGoods0)
        Assert.assertEquals((cache.goodsDao() as MockGoodsDao).result, responseTest.mockGoods0.id)
    }

    @Test
    fun updateFavoritesTest() {
        viewModel.updateFavorites(listOf(responseTest.mockGoods0))
        Assert.assertEquals(
            (cache.goodsDao() as MockGoodsDao).result,
            listOf(responseTest.mockGoods0)
        )
    }
}