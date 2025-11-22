package com.example.croustimenu.app.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListeRestaurantsDto(
    val success: Boolean,
    @SerialName( "data")
    val liste_restaurants_dto: List<RestaurantDto> = emptyList()
)

