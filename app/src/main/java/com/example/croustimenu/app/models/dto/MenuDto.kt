package com.example.croustimenu.app.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Réponse de /v1/restaurants/{code}/menu/{date}
 *
 * {
 *   "success": true,
 *   "data": {
 *     "code": "...",
 *     "date": "21-10-2024",
 *     "repas": [ ... ]
 *   }
 * }
 */
@Serializable
data class MenuByDateResponseDto(
    val success: Boolean,
    @SerialName( "data")
    val menu: MenuDto?
)

/**
 * "Menu" dans le schéma de l’API
 */
@Serializable
data class MenuDto(
    val code: Int,
    val date: String,
    @SerialName( "repas")
    val repasDto: List<RepasDto>
)
