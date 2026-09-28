package com.example.steppy.data.repository

import com.example.steppy.data.model.Challenge
import com.example.steppy.data.model.Participant
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun getChallenges(): Flow<List<Challenge>>
    fun getChallenge(challengeId: String): Flow<Challenge?>
    fun getParticipants(challengeId: String): Flow<List<Participant>>
    suspend fun createChallenge(challenge: Challenge): Result<String>
    suspend fun joinChallenge(challengeId: String, userId: String, username: String, profileImageUrl: String): Result<Unit>
    suspend fun updateParticipantSteps(challengeId: String, userId: String, steps: Long): Result<Unit>
}
