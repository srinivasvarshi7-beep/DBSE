package com.example.steppy.data.repository

import com.example.steppy.data.model.Challenge
import com.example.steppy.data.model.Participant
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChallengeRepository {

    override fun getChallenges(): Flow<List<Challenge>> = callbackFlow {
        val subscription = firestore.collection("challenges")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                if (snapshot != null) {
                    trySend(snapshot.toObjects(Challenge::class.java))
                }
            }
        awaitClose { subscription.remove() }
    }

    override fun getChallenge(challengeId: String): Flow<Challenge?> = callbackFlow {
        val subscription = firestore.collection("challenges").document(challengeId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                trySend(snapshot?.toObject(Challenge::class.java))
            }
        awaitClose { subscription.remove() }
    }

    override fun getParticipants(challengeId: String): Flow<List<Participant>> = callbackFlow {
        val subscription = firestore.collection("challenges").document(challengeId)
            .collection("participants")
            .orderBy("steps", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                if (snapshot != null) {
                    trySend(snapshot.toObjects(Participant::class.java))
                }
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun createChallenge(challenge: Challenge): Result<String> {
        return try {
            val docRef = firestore.collection("challenges").add(challenge).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun joinChallenge(challengeId: String, userId: String, username: String, profileImageUrl: String): Result<Unit> {
        return try {
            val participant = Participant(
                challengeId = challengeId,
                userId = userId,
                username = username,
                profileImageUrl = profileImageUrl,
                hasJoined = true
            )
            firestore.collection("challenges").document(challengeId)
                .collection("participants").document(userId)
                .set(participant).await()
            
            // Also update challenge participantIds list
            firestore.collection("challenges").document(challengeId)
                .update("participantIds", FieldValue.arrayUnion(userId))
                .await()
                
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateParticipantSteps(challengeId: String, userId: String, steps: Long): Result<Unit> {
        return try {
            firestore.collection("challenges").document(challengeId)
                .collection("participants").document(userId)
                .update("steps", steps).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
