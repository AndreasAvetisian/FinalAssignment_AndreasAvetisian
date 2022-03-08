package com.example.finalassignment_andreasavetisian

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserViewModel: ViewModel() {

    val fAuth = Firebase.auth
    val fireStore = Firebase.firestore

    var username = mutableStateOf("")
    val errorMessage = mutableStateOf("")
    var countryName = mutableStateOf("")


    fun loginUser( email: String, pw: String, country: String) {
        fAuth
            .signInWithEmailAndPassword(email, pw)
            .addOnSuccessListener {
                username.value = email
                countryName.value = country

                fireStore
                    .collection("flags")
                    .document(it.user!!.uid)
                    .set( Flag(country) )
                    .addOnSuccessListener {
                        Log.d("********", "Country flag added")
                    }
                    .addOnFailureListener { error ->
                        Log.d("********", error.message.toString())
                    }
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