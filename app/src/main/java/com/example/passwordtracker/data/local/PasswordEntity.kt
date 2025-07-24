package com.example.passwordtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "passwords")
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val platform: String?,
    val username: String?,
    val email: String,
    val password: String,
    val phoneNumber: String?,

)