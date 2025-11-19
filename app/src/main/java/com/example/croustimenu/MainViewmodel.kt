package com.example.croustimenu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.croustimenu.models.Crous
import com.example.croustimenu.models.CrousAPI
import com.example.croustimenu.models.Data
import com.example.croustimenu.repository.APIReposotory
import com.example.croustimenu.repository.CrousRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewmodel (application: Application): AndroidViewModel(application){
    val CrousRepository = CrousRepository(application)
    val APIReposotory = APIReposotory()

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
            crousAPI.value = APIReposotory.getAll().data
        }
    }

    //liste de nos crous

    val crousFavoris = MutableStateFlow<List<Crous>>(emptyList())
    val crousAPI = MutableStateFlow<List<Data>>(emptyList())



    }