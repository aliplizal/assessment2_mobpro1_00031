package com.aliplizal607062300031.assessment2.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliplizal607062300031.assessment2.database.BukuDao
import com.aliplizal607062300031.assessment2.ui.screen.MainViewModel
import com.aliplizal607062300031.assessment2.ui.screen.DetailViewModel

class ViewModelFactory(
    private val dao: BukuDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
