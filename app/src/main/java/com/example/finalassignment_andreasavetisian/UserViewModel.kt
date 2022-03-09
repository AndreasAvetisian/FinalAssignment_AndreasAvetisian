package com.example.finalassignment_andreasavetisian

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserViewModel: ViewModel() {

    private val fAuth = Firebase.auth
    private val fireStore = Firebase.firestore

    var username = mutableStateOf("")
    var successMessage = mutableStateOf("")
    var errorMessage = mutableStateOf("")
    var countryName = mutableStateOf("")

    fun signinUser(name: String, email: String, pw: String, country: String) {
        fAuth
            .createUserWithEmailAndPassword(email, pw)
            .addOnSuccessListener {
                successMessage.value = "Registration completed successfully!"
                countryName.value = country
                username.value = name

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

                fireStore
                    .collection("userNames")
                    .document(it.user!!.uid)
                    .set( UserName(name) )
                    .addOnSuccessListener {
                        Log.d("********", "User name added")
                    }
                    .addOnFailureListener { error ->
                        Log.d("********", error.message.toString())
                    }
            }

        fAuth
            .signInWithEmailAndPassword(email, pw)
            .addOnSuccessListener {
                username.value = name
                errorMessage.value = ""
            }
            .addOnFailureListener {
                errorMessage.value = "Incorrect email or password"
            }
    }


    fun loginUser(name: String, email: String, pw: String) {
        fAuth
            .signInWithEmailAndPassword(email, pw)
            .addOnSuccessListener {
                username.value = name
                errorMessage.value = ""
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