package com.example.croustimenu.app.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepasDto(
    val code: Int,
    val type: String,
    @SerialName("categories")
    val liste_categories_plat_dto: List<CategoriePlatDto>
)
