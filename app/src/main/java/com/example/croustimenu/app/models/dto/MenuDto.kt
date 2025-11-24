package com.example.croustimenu.app.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class MenuByDateResponseDto(
    val success: Boolean,
    @SerialName( "data")
    val menu: MenuDto?
)


@Serializable
data class MenuDto(
    val code: Int,
    val date: String,
    @SerialName( "repas")
    val repasDto: List<RepasDto>
)
