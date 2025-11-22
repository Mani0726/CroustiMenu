package com.example.croustimenu.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavorisDAO {
//    @Query("SELECT * FROM Crous")
//    suspend fun  getAll(): List<Crous>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addCrous(crous:Crous)
//
//    @Query ("DELETE FROM Crous WHERE id= :id")
//    suspend fun deleteCrous(id:Int)
}