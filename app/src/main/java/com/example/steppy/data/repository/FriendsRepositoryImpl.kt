package com.example.steppy.data.repository

import com.example.steppy.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FriendsRepository {

    override fun getFriends(userId: String): Flow<List<User>> = callbackFlow {
        val subscription = firestore.collection("users").document(userId)
            .collection("friends")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                if (snapshot != null) {
                    // This is a bit simplified, usually you'd fetch the actual User objects
                    // For now, let's assume we store minimal user info in the friends collection
                    trySend(snapshot.toObjects(User::class.java))
                }
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun searchUsers(query: String): Result<List<User>> {
        return try {
            val snapshot = firestore.collection("users")
                .whereGreaterThanOrEqualTo("username", query)
                .whereLessThanOrEqualTo("username", query + "\uf8ff")
                .get().await()
            Result.success(snapshot.toObjects(User::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendFriendRequest(fromUserId: String, toUserId: String): Result<Unit> {
        return try {
            // Simplified: just add to friends directly for now
            // In a real app, you'd have a 'requests' collection
            firestore.collection("users").document(fromUserId)
                .collection("friends").document(toUserId).set(mapOf("id" to toUserId)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun acceptFriendRequest(userId: String, friendId: String): Result<Unit> {
        // Implementation for accepting
        return Result.success(Unit)
    }
}
