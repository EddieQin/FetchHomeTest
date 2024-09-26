package com.example.fetchhometest.ui.hiring

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchhometest.ui.entity.HiringItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HiringListViewModel : ViewModel() {

    private val _allItems = MutableLiveData<List<HiringItem>>()

    private var _hiringList = MutableLiveData<List<HiringItem>>()
    var hiringList: LiveData<List<HiringItem>> = _hiringList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _currentListId = MutableLiveData<Int>()
    val currentListId: LiveData<Int> = _currentListId

    private val _isFiltered = MutableLiveData<Boolean>()
    val isFiltered: LiveData<Boolean> = _isFiltered

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val apiService = ApiService.create()

    init {
        _currentListId.value = 0
        _isFiltered.value = false
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            apiService.getHiringItems().enqueue(object : Callback<List<HiringItem>> {
                override fun onResponse(call: Call<List<HiringItem>>, response: Response<List<HiringItem>>
                ) {
                    if (response.isSuccessful) {
                        _allItems.value = response.body()?.sortedWith(compareBy<HiringItem> { it.id }.thenBy { it.name })
                        _hiringList.value = _allItems.value
                        Log.e("HiringListViewModel", "Fetch data successfully")
                    } else {
                        Log.e("HiringListViewModel", "Response is not successful")
                    }
                }
                override fun onFailure(call: Call<List<HiringItem>>, t: Throwable) {
                    Log.e("HiringListViewModel", "Exception: Failed to fetch data. $t")
                }
            })
        }
    }

    fun setCurrentListId(listId: Int) {
        _currentListId.value = listId
    }

    fun toggleFilter() {
        _isFiltered.value = !(_isFiltered.value ?: false)
    }

    fun getFilteredItems(): List<HiringItem> {
        val allItems = _allItems.value ?: emptyList()
        val curListItems = when {
            currentListId.value == 0 -> allItems
            else -> allItems.filter { it.listId == currentListId.value }
        }
        return when {
            isFiltered.value == true -> curListItems.filter { !it.name.isNullOrBlank() }
            else -> curListItems
        }
    }

    fun updateListFilter(listId: Int) {
        _currentListId.value = listId
        _hiringList.value = getFilteredItems()
    }

    fun filterItems() {
        _isFiltered.value = !(_isFiltered.value ?: true)
        _hiringList.value = getFilteredItems()
    }
}