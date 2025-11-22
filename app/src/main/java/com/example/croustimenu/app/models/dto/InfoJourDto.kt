package com.example.croustimenu.app.models.dto

import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoJourDto(
    val jour: String,

    @SerialName("ouverture")
    val ouverture_dto: OuvertureDto
)