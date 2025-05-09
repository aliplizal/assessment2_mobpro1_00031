package com.aliplizal607062300031.assessment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aliplizal607062300031.assessment2.model.Buku

@Database(entities = [Buku::class], version = 1, exportSchema = false)
abstract class BukuDb : RoomDatabase() {

    abstract val dao: BukuDao

    companion object {

        @Volatile
        private var INSTANCE: BukuDb? = null

        fun getInstance(context: Context): BukuDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BukuDb::class.java,
                        "buku.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
