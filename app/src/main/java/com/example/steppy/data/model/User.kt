package com.example.steppy.data.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "",
    val email: String = "",
    val username: String = "",
    val profileImageUrl: String = "",
    val level: Int = 1,
    val xp: Long = 0,
    val totalSteps: Long = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val coins: Long = 0,
    val fcmToken: String = ""
)
