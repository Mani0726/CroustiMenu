package com.example.croustimenu.models.dto

import com.squareup.moshi.Json

data class ListeRestaurantsDto(
    val success: Boolean,
    @Json(name = "data")
    val liste_restaurants_dto: List<RestaurantDto> = emptyList()
)

