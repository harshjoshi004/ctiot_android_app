package com.harshjoshi.aroundthecorner.apiRequest.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshjoshi.aroundthecorner.apiRequest.data.Person
import com.harshjoshi.aroundthecorner.apiRequest.domain.RetrofitInstance
import kotlinx.coroutines.launch

class LaundryViewModel : ViewModel() {
    private var _message: MutableState<String> =  mutableStateOf("")
    val message: MutableState<String> = _message

    fun fetchLaundryMessage(user: Person) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.laundryAd(user)
                _message.value = response.message
            } catch (e: Exception) {
                _message.value = "Error: ${e.localizedMessage}"
            }
        }
    }

    fun fetchBabaJuiceMessage(user: Person) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.babaJuiceAd(user)
                _message.value = response.message
            } catch (e: Exception) {
                _message.value = "Error: ${e.localizedMessage}"
            }
        }
    }
}