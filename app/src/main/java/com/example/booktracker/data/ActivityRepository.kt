package com.example.booktracker.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityRepository @Inject constructor(private val activityDao: ActivityDao) {
    val activities = activityDao.getAll()

    suspend fun insert(activity: Activity) {
        activityDao.insert(activity)
    }
}