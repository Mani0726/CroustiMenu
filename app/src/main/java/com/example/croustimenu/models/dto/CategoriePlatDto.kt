package com.example.croustimenu.models.dto

import com.squareup.moshi.Json

data class CategoriePlatDto(
    val code: Int,
    val libelle: String,
    val ordre: Int,
    @Json(name = "plats")
    val liste_plats_dto: List<PlatDto>
)
