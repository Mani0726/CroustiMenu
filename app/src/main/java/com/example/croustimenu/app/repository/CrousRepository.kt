package com.example.croustimenu.app.repository

import android.content.Context
import androidx.room.Room
import com.example.croustimenu.app.database.AppDatabase
import com.example.croustimenu.app.models.entities.Crous
import kotlin.jvm.java

class CrousRepository(context: Context) {

    private val crousDao = AppDatabase.getInstance(context).crousDAO()

    suspend fun getAll(): List<Crous> = crousDao.getAll()

    suspend fun addCrous(crous: Crous) {
        crousDao.insert(crous)
    }

    suspend fun deleteCrous(id: Int) {
        crousDao.deleteById(id)
    }
}
