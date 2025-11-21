package com.example.croustimenu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.croustimenu.models.Crous
import com.example.croustimenu.models.Data
import com.example.croustimenu.repository.APIRepository
import com.example.croustimenu.repository.CrousRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewmodel (application: Application): AndroidViewModel(application){
    val CrousRepository = CrousRepository(application)
    val APIRepository = APIRepository()

    fun getAll(){
        viewModelScope.launch {
            crousFavoris.value= CrousRepository.getAll()
        }
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
            crousAPI.value = APIRepository.getAll().data
        }
    }

    //liste de nos crous

    val crousFavoris = MutableStateFlow<List<Crous>>(emptyList())
    val crousAPI = MutableStateFlow<List<Data>>(emptyList())



    }