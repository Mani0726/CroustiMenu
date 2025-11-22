package com.example.croustimenu.app.models.dto
import com.squareup.moshi.Json

data class RepasDto(
    val code: Int,
    val type: String,
    @Json(name = "categories")
    val liste_categories_plat_dto: List<CategoriePlatDto>
)
