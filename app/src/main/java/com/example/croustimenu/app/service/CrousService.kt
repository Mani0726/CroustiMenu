package com.example.croustimenu.app.service

import com.example.croustimenu.app.models.dto.ListeRegionsDto
import com.example.croustimenu.app.models.dto.ListeRestaurantsDto
import com.example.croustimenu.app.models.dto.MenuByDateResponseDto

interface CrousService {
    suspend fun getRegions(): ListeRegionsDto
    suspend fun getRestaurantsByCodeRegion(codeRegion: Int): ListeRestaurantsDto
    suspend fun getAllRestaurants(): ListeRestaurantsDto
    suspend fun getMenuByDate(codeRestaurant: Int, date: String): MenuByDateResponseDto
}