package com.example.booktracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities ORDER BY datetime(createdAt) DESC")
    fun getAll(): Flow<List<Activity>>

    @Insert
    suspend fun insertAll(activities: List<Activity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: Activity)
}