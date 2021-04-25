package com.example.booktracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.booktracker.data.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: ActivityRepository
) : ViewModel() {
    val activities = repository.activities.asLiveData()
}