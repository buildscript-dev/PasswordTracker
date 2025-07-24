package com.example.passwordtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.room.Room
import com.example.passwordtracker.data.local.PasswordDatabase
import com.example.passwordtracker.data.local.PasswordViewModel
import com.example.passwordtracker.ui.theme.PasswordTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Room database
        val db = Room.databaseBuilder(
            applicationContext,
            PasswordDatabase::class.java,
            "passwords.db"
        ).build()

        // Create ViewModelFactory
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PasswordViewModel(db.dao) as T
            }
        }

        // Use ViewModelProvider to get your ViewModel instance
        val viewModel = ViewModelProvider(this, factory)[PasswordViewModel::class.java]

        // Set Compose content
        setContent {
            PasswordTrackerTheme {
                val state by viewModel.state.collectAsState()
                PasswordScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}
