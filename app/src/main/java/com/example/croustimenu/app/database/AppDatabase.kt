package com.example.croustimenu.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.croustimenu.app.models.dao.CrousDAO
import com.example.croustimenu.app.models.entities.Crous

//Base de données locale Room pour stocker les restaurants CROUS favoris.
//Fournit accès DAO

@Database(
    entities = [Crous::class],
    version = 4, //changé car sinon l'appli plantait automatiquement dès son ouverture
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
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
