package com.example.croustimenu.app.service

import com.example.croustimenu.app.models.dto.ListeRegionsDto
import com.example.croustimenu.app.models.dto.ListeRestaurantsDto
import com.example.croustimenu.app.models.dto.MenuByDateResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class CrousServiceImpl(private val client: HttpClient) : CrousService {

    override suspend fun getRegions(): ListeRegionsDto {
        return client.get("regions").body()
    }

    override suspend fun getRestaurantsByCodeRegion(codeRegion: Int): ListeRestaurantsDto {
        return client.get("regions/$codeRegion/restaurants").body()
    }

    override suspend fun getAllRestaurants(): ListeRestaurantsDto {
        return client.get("restaurants").body()
    }

    override suspend fun getMenuByDate(codeRestaurant: Int, date: String): MenuByDateResponseDto {
        return client.get("restaurants/$codeRestaurant/menu/$date").body()
    }
}