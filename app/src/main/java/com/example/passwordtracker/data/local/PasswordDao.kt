package com.example.passwordtracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Insert
    suspend fun insertPassword(password: Password)

    @Update
    suspend fun updatePassword(password: Password)

    @Delete
    suspend fun deletePassword(password: Password)

    @Query("SELECT * FROM passwords ORDER BY  id DESC")
    fun getAllPasswords(): Flow<List<Password>>


    @Query("SELECT * FROM passwords ORDER BY username ASC")
    fun getContactsOrderedByUserName(): Flow<List<Password>>

    @Query("SELECT * FROM passwords ORDER BY email ASC")
    fun getContactsOrderedByEmail(): Flow<List<Password>>

    @Query("SELECT * FROM passwords ORDER BY phoneNumber ASC")
    fun getContactsOrderedByPhoneNumber(): Flow<List<Password>>

    @Query("SELECT * FROM passwords ORDER BY platform ASC")
    fun getContactsOrderedByPlatform(): Flow<List<Password>>


    @Query("SELECT * FROM passwords ORDER BY recentDate ASC")
    fun getContactsOrderedByRecentDate(): Flow<List<Password>>




}