package com.example.croustimenu.app.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RestaurantDto(
    val code: Int,
    val region: RegionDto,
    // JSON: "type": { code, libelle }
    @SerialName("type")
    val type_restaurant_dto: TypeRestaurantDto,

    val nom: String,
    val adresse: String?,
    val latitude: Double,
    val longitude: Double,
    val horaires: List<String>?,

    // JSON: "jours_ouvert": [ { jour, ouverture { matin, midi, soir } }, ... ]
    @SerialName("jours_ouvert")
    val liste_jours_ouvert_dto: List<InfoJourDto>,

    val image_url: String?,
    val email: String?,
    val telephone: String?,
    val ispmr: Boolean?,
    val zone: String,

    // JSON: "paiement": [...]
    @SerialName("paiement")
    val paiements: List<String>?,

    // JSON: "acces": [ ... ]
    val acces: List<String>?,

    val ouvert: Boolean = false,
    val actif: Boolean? = null
)
