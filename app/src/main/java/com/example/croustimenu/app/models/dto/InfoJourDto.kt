package com.example.croustimenu.app.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoJourDto(
    val jour: String,

    @SerialName("ouverture")
    val ouverture_dto: OuvertureDto
)