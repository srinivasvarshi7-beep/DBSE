package com.example.steppy.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Challenge(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String = "",
    val creatorId: String = "",
    val stepGoal: Long = 0,
    val startDate: Date = Date(),
    val endDate: Date = Date(),
    val isPrivate: Boolean = false,
    val participantIds: List<String> = emptyList(),
    val status: ChallengeStatus = ChallengeStatus.UPCOMING
)

enum class ChallengeStatus {
    UPCOMING, ACTIVE, COMPLETED
}
