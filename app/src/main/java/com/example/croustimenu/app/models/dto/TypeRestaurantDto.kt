package com.example.croustimenu.app.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class TypeRestaurantDto(
    val code: Int,
    val libelle: String
)