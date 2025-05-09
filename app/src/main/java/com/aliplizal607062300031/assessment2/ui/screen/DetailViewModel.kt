package com.aliplizal607062300031.assessment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliplizal607062300031.assessment2.database.BukuDao
import com.aliplizal607062300031.assessment2.model.Buku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: BukuDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(judul: String, kategori: String, status: String) {
        val buku = Buku(
            judul = judul,
            kategori = kategori,
            status = status
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(buku)
        }
    }

    suspend fun getBuku(id: Long): Buku? {
        return dao.getBukuById(id)
    }

    fun update(id: Long, judul: String, kategori: String, status: String) {
        val buku = Buku(
            id = id,
            judul = judul,
            kategori = kategori,
            status = status
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(buku)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

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