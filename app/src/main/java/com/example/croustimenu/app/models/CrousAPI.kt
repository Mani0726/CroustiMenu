package com.example.croustimenu.app.models

import kotlinx.serialization.Serializable

@Serializable
data class CrousAPI(
    val data: List<Data>,
    val success: Boolean
)
@Serializable
data class Data(
    val acces: List<String>? = null,
    val actif: Boolean? = null,
    val adresse: String? = null,
    val code: Int? = null,
    val email: String? = null,
    val horaires: List<String>? = null,
    val image_url: String? = null,
    val ispmr: Boolean? = null,
    val jours_ouvert: List<JoursOuvert>? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val nom: String? = null,
    val paiement: List<String>? = null,
    val region: Region? = null,
    val telephone: String? = null,
    val type_restaurant: TypeRestaurant? = null,
    val zone: String? = null,
    val estFavori: Boolean = false
)

@Serializable
data class JoursOuvert(
    val jour: String,
    val ouverture: Ouverture
)
@Serializable
data class Region(
    val code: Int,
    val libelle: String
)
@Serializable
data class TypeRestaurant(
    val code: Int,
    val libelle: String
)
@Serializable
data class Ouverture(
    val matin: Boolean,
    val midi: Boolean,
    val soir: Boolean
)