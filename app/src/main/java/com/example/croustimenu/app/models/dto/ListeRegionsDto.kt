package com.example.croustimenu.app.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListeRegionsDto(
    val success: Boolean,
    @SerialName("data")
    val liste_regions_dto: List<RegionDto> = emptyList()
)