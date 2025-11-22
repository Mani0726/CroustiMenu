package com.example.croustimenu.app.models.entities

data class MenuDuJour(
    val code: Int,
    val date: String,
    val liste_repas: List<Repas>
)
