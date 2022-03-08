package com.example.finalassignment_andreasavetisian

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserViewModel: ViewModel() {
    var username = mutableStateOf("")
    val errorMessage = mutableStateOf("")
    var countryName = mutableStateOf("")


    fun loginUser( email: String, pw: String, country: String) {
        Firebase.auth
            .signInWithEmailAndPassword(email, pw)
            .addOnSuccessListener {
                username.value = email
                countryName.value = country
            }
            .addOnFailureListener {
                errorMessage.value = "Incorrect email or password"
            }
    }

    fun logoutUser() {
        Firebase.auth
            .signOut()
        username.value = ""
    }
}