package com.example.steppy.data.model

import com.google.firebase.firestore.DocumentId

data class Participant(
    @DocumentId val id: String = "",
    val challengeId: String = "",
    val userId: String = "",
    val username: String = "",
    val profileImageUrl: String = "",
    val steps: Long = 0,
    val hasJoined: Boolean = false
)
