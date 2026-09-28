package com.example.steppy.data.health

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.steppy.data.model.StepData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await
import java.util.Date

@HiltWorker
class StepSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val healthConnectManager: HealthConnectManager,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val uid = auth.currentUser?.uid ?: return Result.failure()
        
        return try {
            val steps = healthConnectManager.readTodaySteps()
            val stepData = StepData(date = Date(), count = steps)
            
            // Update user's today steps
            firestore.collection("users").document(uid)
                .update("totalSteps", steps) // This should be more complex logic for total, but for now...
                .await()
            
            // Also store in steps history
            firestore.collection("users").document(uid)
                .collection("steps").document("today")
                .set(stepData)
                .await()
                
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
