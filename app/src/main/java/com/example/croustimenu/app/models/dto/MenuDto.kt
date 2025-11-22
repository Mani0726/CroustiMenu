package com.example.croustimenu.app.models.dto

import com.squareup.moshi.Json


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
data class MenuByDateResponseDto(
    val success: Boolean,
    @Json(name = "data")
    val menu: MenuDto?
)

/**
 * "Menu" dans le schéma de l’API
 */
data class MenuDto(
    val code: Int,
    val date: String,
    @Json(name = "repas")
    val repasDto: List<RepasDto>
)
