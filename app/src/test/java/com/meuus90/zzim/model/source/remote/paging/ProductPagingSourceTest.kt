package com.meuus90.zzim.model.source.remote.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.meuus90.zzim.model.data.ResponseTest
import com.meuus90.zzim.model.source.remote.api.MockRestAPI
import com.meuus90.zzim.testkit.CoroutineTestRule
import io.mockk.junit5.MockKExtension
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class ProductPagingSourceTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    val mockGoods = ResponseTest.getInstance().mockGoods0
    val mockData = listOf(mockGoods)
    val mockAPI = MockRestAPI(true)

    lateinit var dataSource: ProductPagingSource

    @Before
    fun setup() {
        dataSource = ProductPagingSource(mockAPI)
    }

    @Test
    fun ProductPagingSourceTest() {
        //todo
    }
}