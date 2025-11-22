package com.example.croustimenu.app.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlatDto(
    val code: Int,
    val ordre: Int,
    val libelle: String
)
