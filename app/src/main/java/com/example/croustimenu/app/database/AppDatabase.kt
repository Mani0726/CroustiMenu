package com.example.croustimenu.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.croustimenu.app.models.dao.CrousDAO
import com.example.croustimenu.app.models.entities.Crous

@Database(
    entities = [Crous::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun crousDAO(): CrousDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "crousti_menu_db"
                )
                    .fallbackToDestructiveMigration()  // en dev : auto wipe si changement schema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
