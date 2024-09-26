package com.example.fetchhometest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _navigateToHiringList = MutableLiveData<Boolean>()
    val navigateToHiringList: LiveData<Boolean> = _navigateToHiringList

    fun onRetrieveClick() {
        _navigateToHiringList.value = true
    }

    fun onNavigationComplete() {
        _navigateToHiringList.value = false
    }

}