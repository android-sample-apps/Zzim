package com.meuus90.zzim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.meuus90.zzim.common.livedata.LiveEvent
import com.meuus90.zzim.model.data.request.Query
import com.meuus90.zzim.model.source.remote.repository.HomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoodsViewModel
@Inject
constructor(private val repository: HomeRepository) : ViewModel() {
    lateinit var schemaLiveData: MutableLiveData<Query>

    init {
        viewModelScope.launch {
            schemaLiveData = LiveEvent()
        }
    }

    @ExperimentalCoroutinesApi
    val goods = schemaLiveData.asFlow()
        .flatMapLatest {
            repository.execute(viewModelScope)
        }

    fun pullTrigger(query: Query) {
        schemaLiveData.value = query
    }
}