package com.example.composeflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MyViewModel:ViewModel() {
    val countDownTimerFlow= flow<Int> {
        val countdownFrom = 10
        var counter = countdownFrom
        emit(countdownFrom)
        while (counter > 0) {
            delay(1000)
            counter--
            emit(counter)

        }
    }
    private fun collectInViewModel(){
        viewModelScope.launch {
            countDownTimerFlow
                .filter {
                    it % 2 == 0
                }
                .map {
                    it + it
                }
                .collect{
                    println("counter is : $it")
            }
        }
    }

    private val _liveData = MutableLiveData<String>("Kotlin Live Data")
    val liveData: LiveData<String> = _liveData
    fun changeLiveDataValue() {
        _liveData.value = "Live Data is Changed"
    }
    private val _stateFlow = MutableStateFlow("Kotlin StateFlow")
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun changedSharedFlowValue() {
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow")
        }
    }
    fun changeStateFlowValue() {
        _stateFlow.value = "StateFlow"
    }

}
