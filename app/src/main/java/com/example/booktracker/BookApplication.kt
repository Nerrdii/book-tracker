package com.example.booktracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// Annotation to set this as base application class so Hilt knows how to setup dependency injection
@HiltAndroidApp
class BookApplication : Application()
