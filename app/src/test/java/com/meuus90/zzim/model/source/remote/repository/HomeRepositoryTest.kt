package com.meuus90.zzim.model.source.remote.repository

import com.meuus90.zzim.model.data.ResponseTest
import com.meuus90.zzim.model.source.remote.api.MockRestAPI
import com.meuus90.zzim.testkit.flow.test
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class HomeRepositoryTest : TestWatcher() {
    val responseTest = ResponseTest.getInstance()
    lateinit var repository: HomeRepository

    @Before
    fun setup() {
        repository = HomeRepository(MockRestAPI())
    }

    @Test
    fun workTest() {
        runBlockingTest {
            repository.execute(this).test(this) {}
        }
    }
}