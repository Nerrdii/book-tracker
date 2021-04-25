package com.example.booktracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

@Entity(tableName = "activities")
data class Activity(
    @ColumnInfo val type: ActivityType,
    @ColumnInfo val description: String,
    @ColumnInfo val createdAt: LocalDateTime = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

enum class ActivityType {
    @SerializedName("0")
    INSERT,
    @SerializedName("1")
    UPDATE,
    @SerializedName("2")
    DELETE
}
