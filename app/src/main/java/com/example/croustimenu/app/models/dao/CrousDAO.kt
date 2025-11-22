package com.example.croustimenu.app.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.croustimenu.app.models.entities.Crous

@Dao
interface CrousDAO {

    @Query("SELECT * FROM crous_favoris")
    suspend fun getAll(): List<Crous>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crous: Crous)

    @Query("DELETE FROM crous_favoris WHERE id = :id")
    suspend fun deleteById(id: Int)
}
