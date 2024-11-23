package com.harshjoshi.aroundthecorner

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.harshjoshi.aroundthecorner.apiRequest.data.Person

class GlobalViewModel: ViewModel() {
    // Mode
    private val _mode: MutableState<Boolean> = mutableStateOf(false)

    val mode: MutableState<Boolean> = _mode

    // User
    private val _user: MutableState<Person> = mutableStateOf(
        Person(
            name = "Harsh Joshi",
            gender = "Male",
            region = "Delhi",
            age = "21"
        )
    )

    val user: MutableState<Person> = _user

    // Functions
    fun toggleMode(context: Context) {
        _mode.value = !_mode.value
    }

    fun setUser(user: Person, context: Context) {
        _user.value = user

        val sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", user.name)
        editor.putString("gender", user.gender)
        editor.putString("region", user.region)
        editor.putString("age", user.age)
        editor.apply()
    }
}