package com.aliplizal607062300031.assessment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliplizal607062300031.assessment2.database.BukuDao
import com.aliplizal607062300031.assessment2.model.Buku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dao: BukuDao) : ViewModel() {
    val data: StateFlow<List<Buku>> = dao.getBuku().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val deletedBuku: StateFlow<List<Buku>> = dao.getDeletedBuku().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun restore(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.restoreById(id)
        }
    }

    fun permanentlyDelete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.permanentlyDeleteById(id)
        }
    }
}
