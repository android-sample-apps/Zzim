package com.meuus90.zzim.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.meuus90.zzim.model.data.request.Query
import com.meuus90.zzim.model.source.remote.repository.HomeRepository
import com.meuus90.zzim.testkit.CoroutineTestRule
import com.meuus90.zzim.testkit.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class GoodsViewModelTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: GoodsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        val repository = Mockito.mock(HomeRepository::class.java) as HomeRepository

        viewModel = GoodsViewModel(repository)
        viewModel.schemaLiveData = MutableLiveData()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun pullTriggerTest() {
        runBlockingTest {
            viewModel.pullTrigger(Query(listOf("test", 1)))

            Assert.assertEquals(
                viewModel.schemaLiveData.getOrAwaitValue(),
                Query(listOf("test", 1))
            )
        }
    }
}