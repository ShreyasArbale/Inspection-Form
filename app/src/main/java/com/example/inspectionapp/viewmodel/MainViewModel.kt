package com.example.inspectionapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    fun nextTab() {
        if (_selectedTabIndex.value < 2) {
            _selectedTabIndex.value += 1
        }
    }

    fun previousTab() {
        if (_selectedTabIndex.value > 0) {
            _selectedTabIndex.value -= 1
        }
    }

    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

}