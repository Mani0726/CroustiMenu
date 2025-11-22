package com.example.croustimenu.app.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegionDto(
    val code: Int,
    val libelle: String
)
