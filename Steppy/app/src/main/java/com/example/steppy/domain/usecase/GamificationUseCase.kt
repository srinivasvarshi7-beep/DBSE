package com.example.steppy.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamificationUseCase @Inject constructor() {

    fun calculateXP(steps: Long): Long {
        return steps / 10 // 1 XP for every 10 steps
    }

    fun getLevel(xp: Long): Int {
        return (xp / 1000).toInt() + 1 // 1000 XP per level
    }

    fun getProgressToNextLevel(xp: Long): Float {
        val currentLevelXP = xp % 1000
        return currentLevelXP.toFloat() / 1000f
    }
}
