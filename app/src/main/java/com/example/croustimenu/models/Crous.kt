package com.example.croustimenu.models

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class Crous(
    @PrimaryKey val id:Int,
    val nom:String,
    val adresse:String,
    val latitude: Float,
    val longitude: Float,
    val estFavori: Boolean = false
)

@Dao
interface CrousDAO {
    @Query("SELECT * FROM Crous")
    suspend fun  getAll(): List<Crous>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCrous(crous:Crous)

    @Query ("DELETE FROM Crous WHERE id= :id")
    suspend fun deleteCrous(id:Int)
}