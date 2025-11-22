package com.example.croustimenu.models.dto

import com.squareup.moshi.Json

data class InfoJourDto(
    val jour: String,

    @Json(name = "ouverture")
    val ouverture_dto: OuvertureDto
)