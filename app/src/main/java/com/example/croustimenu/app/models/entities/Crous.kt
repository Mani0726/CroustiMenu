package com.example.croustimenu.app.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crous_favoris")
data class Crous(
    @PrimaryKey val id: Int,
    val nom: String,
    val adresse: String?
)
