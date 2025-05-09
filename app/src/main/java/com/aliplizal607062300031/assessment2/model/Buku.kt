package com.aliplizal607062300031.assessment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buku")
data class Buku(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val kategori: String,
    val status: String,
    val isDeleted: Boolean = false
)