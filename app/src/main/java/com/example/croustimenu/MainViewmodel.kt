package com.example.croustimenu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.croustimenu.app.models.Crous
import com.example.croustimenu.app.models.entities.Region
import com.example.croustimenu.app.models.entities.Restaurant
import com.example.croustimenu.app.repository.APIRepository
import com.example.croustimenu.app.repository.CrousRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewmodel(application: Application) : AndroidViewModel(application) {

    private val crousRepository = CrousRepository(application)
    private val apiRepository = APIRepository()

    /**
     * IDs des restaurants favoris stockés en base.
     * Sert à marquer les Restaurant.estFavori dans les listes chargées depuis l’API.
     */
    private val favorisIds = mutableSetOf<Int>()

    // --- StateFlows exposés au UI ---

    private val _crousFavoris = MutableStateFlow<List<Crous>>(emptyList())
    val crousFavoris = _crousFavoris.asStateFlow()

    private val _crousAPI = MutableStateFlow<List<Restaurant>>(emptyList())
    val crousAPI = _crousAPI.asStateFlow()

    private val _crousFavorisAPI = MutableStateFlow<List<Restaurant>>(emptyList())
    val crousFavorisAPI = _crousFavorisAPI.asStateFlow()

    private val _regionsAPI = MutableStateFlow<List<Region>>(emptyList())
    val regionsAPI = _regionsAPI.asStateFlow()

    init {
        // Au démarrage : charge les favoris (Room) + les régions
        viewModelScope.launch {
            try {
                // Favoris en base
                val favoris = crousRepository.getAll()
                _crousFavoris.value = favoris
                favorisIds.clear()
                favorisIds.addAll(favoris.map { it.id })

                // Liste des régions
                val regionsResponse = apiRepository.getRegions()
                _regionsAPI.value = regionsResponse.regions
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur init", e)
            }
        }
    }

    // --- Utilitaires internes ---

    private fun recalcFavorisOnRestaurants() {
        _crousAPI.value = _crousAPI.value.map { r ->
            r.copy(estFavori = favorisIds.contains(r.code))
        }
        _crousFavorisAPI.value = _crousAPI.value.filter { it.estFavori }
    }

    private fun refreshFavorisFromDb() {
        viewModelScope.launch {
            val favoris = crousRepository.getAll()
            _crousFavoris.value = favoris
            favorisIds.clear()
            favorisIds.addAll(favoris.map { it.id })
            recalcFavorisOnRestaurants()
        }
    }

    // --- Accès API ---

    fun getAllRegions() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getRegions()
                _regionsAPI.value = response.regions
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur lors de la récupération des régions", e)
            }
        }
    }

    /**
     * Charge tous les restaurants (GET /restaurants) et applique les favoris
     */
    fun getAllCrousByAPI() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getAll()
                val dataList = response.restaurants
                _crousAPI.value = dataList.map { r ->
                    r.copy(estFavori = favorisIds.contains(r.code))
                }
                _crousFavorisAPI.value = _crousAPI.value.filter { it.estFavori }
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur lors de la récupération des restaurants", e)
            }
        }
    }

    /**
     * Charge les restaurants d'une région donnée
     */
    fun getRestaurantsByRegion(codeRegion: Int) {
        viewModelScope.launch {
            try {
                val response = apiRepository.getRestaurantsByRegion(codeRegion)
                val dataList = response.restaurants
                _crousAPI.value = dataList.map { restaurant ->
                    restaurant.copy(estFavori = favorisIds.contains(restaurant.code))
                }
                _crousFavorisAPI.value = _crousAPI.value.filter { it.estFavori }
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur lors de la récupération des restaurants", e)
            }
        }
    }

    // --- DAO Room exposé (si besoin direct dans l’UI) ---

    fun reloadFavorisFromDb() {
        refreshFavorisFromDb()
    }

    // --- Toggle favori avec persistance Room ---

    fun toggleFavori(crousId: Int) {
        viewModelScope.launch {
            val currentList = _crousAPI.value
            val restaurant = currentList.find { it.code == crousId }

            val wasFavori = favorisIds.contains(crousId)

            if (wasFavori) {
                // Suppression en base
                favorisIds.remove(crousId)
                crousRepository.deleteCrous(crousId)
            } else {
                // Ajout en base
                favorisIds.add(crousId)
                if (restaurant != null) {
                    val crous = Crous(
                        id = restaurant.code,
                        nom = restaurant.nom,
                        adresse = restaurant.adresse ?: "",
                        latitude = restaurant.latitude,
                        longitude = restaurant.longitude,
                        estFavori = true
                    )
                    crousRepository.addCrous(crous)
                }
            }

            // Met à jour la liste "en mémoire"
            _crousAPI.value = currentList.map { r ->
                if (r.code == crousId) {
                    r.copy(estFavori = !wasFavori)
                } else {
                    r
                }
            }

            // Met à jour les listes dérivées
            _crousFavorisAPI.value = _crousAPI.value.filter { it.estFavori }

            // Recharge la liste de favoris depuis la base pour rester cohérent
            val favoris = crousRepository.getAll()
            _crousFavoris.value = favoris
        }
    }
}
