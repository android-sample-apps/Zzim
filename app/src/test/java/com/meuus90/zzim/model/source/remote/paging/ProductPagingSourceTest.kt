package com.meuus90.zzim.model.source.remote.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.meuus90.zzim.model.data.GoodsDataModel
import com.meuus90.zzim.model.data.ResponseTest
import com.meuus90.zzim.model.source.remote.api.MockRestAPI
import com.meuus90.zzim.testkit.CoroutineTestRule
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class ProductPagingSourceTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    val responseTest = ResponseTest.getInstance()
    val mockAPI = MockRestAPI(true)

    lateinit var dataSource: ProductPagingSource

    @Before
    fun setup() {
        dataSource = ProductPagingSource(mockAPI)
    }

    @Test
    fun ProductPagingSourceTest() {
        runBlockingTest {
            Assert.assertEquals(
                dataSource.load(PagingSource.LoadParams.Append(0, 10, true)),
                PagingSource.LoadResult.Page(
                    mutableListOf(
                        GoodsDataModel.Header(responseTest.mockHome0.banners),
                        GoodsDataModel.Item(responseTest.mockGoods0)
                    ), null,
                    1
                )
            )

            Assert.assertEquals(
                dataSource.load(PagingSource.LoadParams.Append(1, 10, true)),
                PagingSource.LoadResult.Page(
                    mutableListOf(
                        GoodsDataModel.Item(responseTest.mockGoods0)
                    ), null,
                    1
                )
            )
        }
    }
}