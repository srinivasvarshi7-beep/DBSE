package com.example.steppy.ui.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class PermissionsRationaleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RationaleScreen()
        }
    }
}

@Composable
fun RationaleScreen() {
    Text("Steppy needs access to your steps data to track your progress and challenges. Your data is handled securely and not shared with third parties.")
}
