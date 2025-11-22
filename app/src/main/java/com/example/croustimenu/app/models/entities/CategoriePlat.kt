package com.example.croustimenu.app.models.entities

data class CategoriePlat(
    val code: Int,
    val libelle: String,
    val ordre: Int,
    val liste_plats: List<Plat>
)
