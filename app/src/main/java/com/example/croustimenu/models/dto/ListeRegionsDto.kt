package com.example.croustimenu.models.dto

import com.squareup.moshi.Json

data class ListeRegionsDto(
    val success: Boolean,
    @Json(name = "data")
    val liste_regions_dto: List<RegionDto> = emptyList()
)