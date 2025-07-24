package com.example.passwordtracker.data.local

data class PasswordState(
val passwords: List<Password> = emptyList(),
val platform: String = "",
val username: String = "",
val password: String = "",
val email: String = "",
val isAddingPassword: Boolean = false,
val sortType: SortType = SortType.DATA_ADDED
    )