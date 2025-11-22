package com.example.croustimenu.app.service

import com.example.croustimenu.app.models.dto.ListeRegionsDto
import com.example.croustimenu.app.models.dto.ListeRestaurantsDto
import com.example.croustimenu.app.models.dto.MenuByDateResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class CrousServiceImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : CrousService {

    override suspend fun getRegions(): ListeRegionsDto {
        return client.get("${baseUrl}regions").body()
    }

    override suspend fun getRestaurantsByCodeRegion(codeRegion: Int): ListeRestaurantsDto {
        return client.get("${baseUrl}regions/$codeRegion/restaurants").body()
    }

    override suspend fun getAllRestaurants(): ListeRestaurantsDto {
        return client.get("${baseUrl}restaurants").body()
    }

    override suspend fun getMenuByDate(codeRestaurant: Int, date: String): MenuByDateResponseDto {
        return client.get("${baseUrl}restaurants/$codeRestaurant/menu/$date").body()
    }
}