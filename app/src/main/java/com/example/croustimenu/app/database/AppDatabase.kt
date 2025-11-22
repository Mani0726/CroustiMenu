package com.example.croustimenu.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.croustimenu.app.models.Crous
import com.example.croustimenu.app.models.CrousDAO


@Database(entities = [Crous::class], version = 3 )
abstract class AppDatabase : RoomDatabase(){
    abstract fun crousDAO(): CrousDAO
}

