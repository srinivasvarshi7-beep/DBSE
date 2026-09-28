package com.example.steppy.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.steppy.data.health.HealthConnectManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val healthConnectManager: HealthConnectManager
) : ViewModel() {

    private val _todaySteps = MutableStateFlow(0L)
    val todaySteps: StateFlow<Long> = _todaySteps

    private val _dailyGoal = MutableStateFlow(10000L)
    val dailyGoal: StateFlow<Long> = _dailyGoal

    init {
        refreshSteps()
    }

    fun refreshSteps() {
        viewModelScope.launch {
            _todaySteps.value = healthConnectManager.readTodaySteps()
        }
    }
}
