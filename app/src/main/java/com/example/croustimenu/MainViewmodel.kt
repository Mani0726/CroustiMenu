package com.example.croustimenu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.croustimenu.app.models.Crous
import com.example.croustimenu.app.models.entities.MenuDuJour
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

    private val favorisIds = mutableSetOf<Int>()

    // --- State API ---

    private val _regionsAPI = MutableStateFlow<List<Region>>(emptyList())
    val regionsAPI = _regionsAPI.asStateFlow()

    private val _crousAPI = MutableStateFlow<List<Restaurant>>(emptyList())
    val crousAPI = _crousAPI.asStateFlow()

    private val _crousFavorisAPI = MutableStateFlow<List<Restaurant>>(emptyList())
    val crousFavorisAPI = _crousFavorisAPI.asStateFlow()

    // Favoris en base (Room)
    private val _crousFavoris = MutableStateFlow<List<Crous>>(emptyList())
    val crousFavoris = _crousFavoris.asStateFlow()

    // --- Menu du jour ---

    private val _menuDuJour = MutableStateFlow<MenuDuJour?>(null)
    val menuDuJour = _menuDuJour.asStateFlow()

    val isMenuLoading = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            try {
                // Charge les favoris depuis Room
                val favoris = crousRepository.getAll()
                _crousFavoris.value = favoris
                favorisIds.clear()
                favorisIds.addAll(favoris.map { it.id })

                // Charge les régions
                val regionsResponse = apiRepository.getRegions()
                _regionsAPI.value = regionsResponse.regions
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur init", e)
            }
        }
    }

    // --- Régions & restaurants ---

    fun getAllRegions() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getRegions()
                _regionsAPI.value = response.regions
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur getAllRegions", e)
            }
        }
    }

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
                Log.e("MainViewmodel", "Erreur getRestaurantsByRegion", e)
            }
        }
    }

    fun getAllCrousByAPI() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getAll()
                val dataList = response.restaurants
                _crousAPI.value = dataList.map { restaurant ->
                    restaurant.copy(estFavori = favorisIds.contains(restaurant.code))
                }
                _crousFavorisAPI.value = _crousAPI.value.filter { it.estFavori }
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur getAllCrousByAPI", e)
            }
        }
    }

    // --- Favoris / Room ---

    private fun recalcFavorisOnRestaurants() {
        _crousAPI.value = _crousAPI.value.map { r ->
            r.copy(estFavori = favorisIds.contains(r.code))
        }
        _crousFavorisAPI.value = _crousAPI.value.filter { it.estFavori }
    }

    fun reloadFavorisFromDb() {
        viewModelScope.launch {
            val favoris = crousRepository.getAll()
            _crousFavoris.value = favoris
            favorisIds.clear()
            favorisIds.addAll(favoris.map { it.id })
            recalcFavorisOnRestaurants()
        }
    }

    fun toggleFavori(crousId: Int) {
        viewModelScope.launch {
            val currentList = _crousAPI.value
            val restaurant = currentList.find { it.code == crousId }

            val wasFavori = favorisIds.contains(crousId)

            if (wasFavori) {
                favorisIds.remove(crousId)
                crousRepository.deleteCrous(crousId)
            } else {
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

            _crousAPI.value = currentList.map { r ->
                if (r.code == crousId) {
                    r.copy(estFavori = !wasFavori)
                } else {
                    r
                }
            }

            _crousFavorisAPI.value = _crousAPI.value.filter { it.estFavori }

            val favoris = crousRepository.getAll()
            _crousFavoris.value = favoris
        }
    }

    // --- Menu du jour ---

    fun loadMenuDuJour(codeRestaurant: Int) {
        viewModelScope.launch {
            isMenuLoading.value = true
            try {
                val menu = apiRepository.getMenuDuJour(codeRestaurant)
                _menuDuJour.value = menu
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur loadMenuDuJour", e)
                _menuDuJour.value = null
            } finally {
                isMenuLoading.value = false
            }
        }
    }

    fun clearMenuDuJour() {
        _menuDuJour.value = null
        isMenuLoading.value = false
    }
}
