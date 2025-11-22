package com.example.croustimenu.models.entities

data class Repas(
    val code: Int,
    val type: String,
    val liste_categories_plat: List<CategoriePlat>
)
