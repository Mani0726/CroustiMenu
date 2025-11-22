package com.example.croustimenu.app.models.entities

data class Restaurant(
    val code: Int,
    val region: Region,
    val type: TypeRestaurant,
    val nom: String,
    val adresse: String?,
    val latitude: Float,
    val longitude: Float,
    val horaires: List<String>?,
    val jours_ouvert: List<InfoJour>,
    val image_url: String?,
    val email: String?,
    val telephone: String?,
    val ispmr: Boolean?,
    val zone: String,
    val paiements: List<String>?,
    val acces: List<String>?,
    val ouvert: Boolean,
    val actif: Boolean?,

    // ... autres champs ...
    val estFavori: Boolean = false // nouveau champ avec valeur par défaut
)
