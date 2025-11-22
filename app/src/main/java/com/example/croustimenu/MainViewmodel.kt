package com.example.croustimenu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.croustimenu.app.models.Crous
import com.example.croustimenu.app.models.Data
import com.example.croustimenu.app.repository.APIRepository
import com.example.croustimenu.app.repository.CrousRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewmodel (application: Application): AndroidViewModel(application){
    val CrousRepository = CrousRepository(application)
    val APIRepository = APIRepository()
    val crousFavorisAPI = MutableStateFlow<List<Data>>(emptyList())


    // Liste pour mémoriser les IDs des favoris
    private val favorisIds = mutableSetOf<Int>()

    fun getAll(){
        viewModelScope.launch {
            crousFavoris.value= CrousRepository.getAll()
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

    fun getAllCrousByAPI() {
        viewModelScope.launch {
            val data = APIRepository.getAll().data
            // Restaurer l'état des favoris après le chargement
            crousAPI.value = data.map { it.copy(estFavori = favorisIds.contains(it.code)) }
        }
    }

    fun toggleFavori(crousId: Int) {
        // Ajouter/retirer de la liste mémorisée
        if (favorisIds.contains(crousId)) {
            favorisIds.remove(crousId)
        } else {
            favorisIds.add(crousId)
        }

        crousAPI.value = crousAPI.value.map { data ->
            if (data.code == crousId) {
                data.copy(estFavori = !data.estFavori)
            } else {
                data
            }
        }
        updateFavorisAPI()

    }

    //liste de nos crous
    val crousFavoris = MutableStateFlow<List<Crous>>(emptyList())
    val crousAPI = MutableStateFlow<List<Data>>(emptyList())
}