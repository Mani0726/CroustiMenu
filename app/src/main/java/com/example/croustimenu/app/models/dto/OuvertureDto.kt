package com.example.croustimenu.app.models.dto

import kotlinx.serialization.Serializable

//pour les heures d'ouvertures du crous
@Serializable
data class OuvertureDto(
    val matin: Boolean,
    val midi: Boolean,
    val soir: Boolean
)
