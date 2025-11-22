package com.example.croustimenu.app.repository

import android.util.Log
import com.example.croustimenu.app.models.entities.ListeRegions
import com.example.croustimenu.app.models.entities.ListeRestaurants
import com.example.croustimenu.app.models.entities.MenuDuJour
import com.example.croustimenu.app.models.mapper.CrousMapper
import com.example.croustimenu.app.service.CrousService
import com.example.croustimenu.app.service.ServiceFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class APIRepository(
    private val api: CrousService = ServiceFactory.crousService,
    private val mapper: CrousMapper = CrousMapper()
) {
    suspend fun getRegions(): ListeRegions = with(mapper) {
        Log.d("APIRepository", "Fetching regions...")
        val dto = api.getRegions()
        Log.d("APIRepository", "Received regions DTO: $dto")
        dto.toDomain()
    }

    suspend fun getRestaurantsByRegion(regionCode: Int): ListeRestaurants =
        with(mapper) {
            Log.d("APIRepository", "Fetching restaurants for region: $regionCode")
            api.getRestaurantsByCodeRegion(regionCode).toDomain()
        }

    suspend fun getAll(): ListeRestaurants =
        with(mapper) {
            Log.d("APIRepository", "Fetching all restaurants")
            api.getAllRestaurants().toDomain()
        }

    /**
     * Récupère le menu du jour d'un restaurant.
     *
     * @param restaurantCode code du restaurant
     * @param dateApi date au format API (DD-MM-YYYY).
     *        Si null, on utilise la date du jour.
     *
     * Retourne null si aucun menu n'est disponible pour cette date.
     */
    suspend fun getMenuDuJour(
        restaurantCode: Int,
        dateApi: String? = null
    ): MenuDuJour? {
        val dateToUse = dateApi ?: todayForApi()

        Log.d("APIRepository", "Fetching menu for restaurant $restaurantCode on date $dateToUse")

        return try {
            val response = api.getMenuByDate(
                codeRestaurant = restaurantCode,
                date = dateToUse
            )

            // Vérifier si on a des menus dans la réponse
            val menuDto = response.menu?: return null

            // Utiliser le mapper comme receiver pour accéder à MenuDto.toDomain()
            with(mapper) { menuDto.toDomain() }
        } catch (e: Exception) {
            Log.e("APIRepository", "Erreur lors de la récupération du menu", e)
            null
        }
    }

    private fun todayForApi(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
        return "19-11-2025" // TODO DELETE, THIS IS AN EXAMPLE
//        return sdf.format(Date())
    }
}