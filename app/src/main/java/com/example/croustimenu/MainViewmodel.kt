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
import kotlinx.coroutines.launch

class MainViewmodel (application: Application): AndroidViewModel(application){
    val CrousRepository = CrousRepository(application)
    val APIRepository = APIRepository()

    // Liste pour mémoriser les IDs des favoris
    private val favorisIds = mutableSetOf<Int>()

    //liste de nos crous
    val crousFavoris = MutableStateFlow<List<Crous>>(emptyList())
    val crousAPI = MutableStateFlow<List<Restaurant>>(emptyList())
    val crousFavorisAPI = MutableStateFlow<List<Restaurant>>(emptyList())
    val regionsAPI = MutableStateFlow<List<Region>>(emptyList())

    fun getAll(){
        viewModelScope.launch {
            crousFavoris.value = CrousRepository.getAll()
        }
    }

    fun updateFavorisAPI() {
        crousFavorisAPI.value = crousAPI.value.filter { it.estFavori }
    }

    fun addCrous(crous: Crous){
        viewModelScope.launch {
            CrousRepository.addCrous(crous)
            getAll()
        }
    }

    fun deleteCrous(id: Int){
        viewModelScope.launch {
            CrousRepository.deleteCrous(id)
            getAll()
        }
    }

    fun getAllRegions() {
        viewModelScope.launch {
            try {
                val response = APIRepository.getRegions()
                regionsAPI.value = response.regions
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur lors de la récupération des régions", e)
            }
        }
    }

    fun getAllCrousByAPI() {
        viewModelScope.launch {
            try {
                val response = APIRepository.getAll()
                val dataList = response.restaurants
                // Restaurer l'état des favoris après le chargement
                crousAPI.value = dataList.map { it.copy(estFavori = favorisIds.contains(it.code)) }
                updateFavorisAPI()
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur lors de la récupération des restaurants", e)
            }
        }
    }

    fun getRestaurantsByRegion(codeRegion: Int) {
        viewModelScope.launch {
            try {
                val response = APIRepository.getRestaurantsByRegion(codeRegion)
                val dataList = response.restaurants
                crousAPI.value = dataList.map { restaurant ->
                    restaurant.copy(estFavori = favorisIds.contains(restaurant.code))
                }
                updateFavorisAPI()
            } catch (e: Exception) {
                Log.e("MainViewmodel", "Erreur lors de la récupération des restaurants", e)
            }
        }
    }

    fun toggleFavori(crousId: Int) {
        // Ajouter/retirer de la liste mémorisée
        if (favorisIds.contains(crousId)) {
            favorisIds.remove(crousId)
        } else {
            favorisIds.add(crousId)
        }

        crousAPI.value = crousAPI.value.map { restaurant ->
            if (restaurant.code == crousId) {
                restaurant.copy(estFavori = !restaurant.estFavori)
            } else {
                restaurant
            }
        }
        updateFavorisAPI()
    }
}