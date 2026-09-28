package com.example.steppy.data.repository

import com.example.steppy.data.model.User
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    fun getFriends(userId: String): Flow<List<User>>
    suspend fun searchUsers(query: String): Result<List<User>>
    suspend fun sendFriendRequest(fromUserId: String, toUserId: String): Result<Unit>
    suspend fun acceptFriendRequest(userId: String, friendId: String): Result<Unit>
}
