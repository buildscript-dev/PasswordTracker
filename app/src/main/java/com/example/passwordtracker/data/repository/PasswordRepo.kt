package com.example.passwordtracker.data.repository

// Repository
class PasswordRepo(private val dao: PasswordDao) {
    suspend fun fetchPasswords() = dao.getAllPasswords()
}
