package com.example.croustimenu.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.croustimenu.models.Crous
import com.example.croustimenu.models.CrousDAO


@Database(entities = [Crous::class], version = 1 )
abstract class AppDatabase : RoomDatabase(){
    abstract fun crousDAO(): CrousDAO
}

