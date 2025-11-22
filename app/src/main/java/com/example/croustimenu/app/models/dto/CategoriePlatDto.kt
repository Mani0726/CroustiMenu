package com.example.croustimenu.app.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriePlatDto(
    val code: Int,
    val libelle: String,
    val ordre: Int,
    @SerialName("plats")
    val liste_plats_dto: List<PlatDto>
)
