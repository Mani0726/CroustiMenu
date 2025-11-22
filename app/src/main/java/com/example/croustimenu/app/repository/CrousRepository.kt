package com.example.croustimenu.app.repository

import android.content.Context
import androidx.room.Room
import com.example.croustimenu.app.database.AppDatabase
import com.example.croustimenu.app.models.Crous
import kotlin.jvm.java

class CrousRepository (context: Context) {
    val database = Room.databaseBuilder(context, AppDatabase::class.java, "databe-name")
        .fallbackToDestructiveMigration()
        .build()

    val dao = database.crousDAO()


    suspend fun getAll() = dao.getAll()

    suspend fun addCrous(crous: Crous) = dao.addCrous(crous)

    suspend fun deleteCrous(id:Int)= dao.deleteCrous(id)
}
