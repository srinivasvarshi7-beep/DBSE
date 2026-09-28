package com.example.steppy.viewmodel.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.steppy.data.model.Challenge
import com.example.steppy.data.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    val challenges: StateFlow<List<Challenge>> = challengeRepository.getChallenges()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createChallenge(challenge: Challenge, onResult: (Result<String>) -> Unit) {
        viewModelScope.launch {
            onResult(challengeRepository.createChallenge(challenge))
        }
    }

    fun joinChallenge(challengeId: String, userId: String, username: String, profileImageUrl: String, onResult: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            onResult(challengeRepository.joinChallenge(challengeId, userId, username, profileImageUrl))
        }
    }
}
