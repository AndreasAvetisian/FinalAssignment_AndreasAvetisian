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

    var successMessage = mutableStateOf("")
    var errorMessage = mutableStateOf("")


    fun signinUser(name: String, email: String, pw: String, country: String) {

        if (email.isNotEmpty() || pw.isNotEmpty()) {
            fAuth
                .createUserWithEmailAndPassword(email, pw)
                .addOnSuccessListener {
                    errorMessage.value = ""
                    successMessage.value = "Registration completed successfully!"

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
                    errorMessage.value = ""
                    successMessage.value = "Logged in successfully"
                }
                .addOnFailureListener {
                    errorMessage.value = "Something went wrong"
                    successMessage.value = ""
                }
        } else {
            errorMessage.value = "Please, fill email and password fields"
            successMessage.value = ""
        }
    }


    fun loginUser(email: String, pw: String) {
        if (email.isNotEmpty() || pw.isNotEmpty()) {
            fAuth
                .signInWithEmailAndPassword(email, pw)
                .addOnSuccessListener {
                    errorMessage.value = ""
                    successMessage.value = "Logged in successfully"
                }
                .addOnFailureListener {
                    errorMessage.value = "Incorrect email or password"
                    successMessage.value = ""
                }
        } else {
            errorMessage.value = "Please, fill email and password fields"
            successMessage.value = ""
        }
    }

    fun logoutUser() {
        Firebase.auth
            .signOut()
        errorMessage.value = ""
        successMessage.value = ""
    }

    fun modifyUserName(name: String) {

        if (name.isNotEmpty()) {
            val nameValue = hashMapOf(
                "userName" to name
            )

            fireStore
                .collection("userNames")
                .document(fAuth.currentUser!!.uid)
                .set(nameValue)
                .addOnSuccessListener {
                    Log.d("********", "User name updated")
                }
                .addOnFailureListener { error ->
                    Log.d("********", error.message.toString())
                }

            errorMessage.value = ""
        } else {
            errorMessage.value = "Please, fill name field"
            successMessage.value = ""
        }

    }

    fun modifyUserFlag(country: String) {

        if (country.isNotEmpty()) {
            val flagValue = hashMapOf(
                "countryFlag" to country
            )

            fireStore
                .collection("flags")
                .document(fAuth.currentUser!!.uid)
                .set(flagValue)
                .addOnSuccessListener {
                    Log.d("********", "Country flag updated")
                }
                .addOnFailureListener { error ->
                    Log.d("********", error.message.toString())
                }

            errorMessage.value = ""
        } else {
            errorMessage.value = "Please, fill country field"
            successMessage.value = ""
        }

    }

    fun modifyUserEmail(email: String) {

        if (email.isNotEmpty()) {
            fAuth
                .currentUser
                ?.updateEmail(email)

            errorMessage.value = ""
        } else {
            errorMessage.value = "Please, fill email field"
            successMessage.value = ""
        }

    }

    fun modifyUserPassword(pw: String) {

        if (pw.isNotEmpty()) {
            fAuth
                .currentUser
                ?.updatePassword(pw)

            errorMessage.value = ""
        } else {
            errorMessage.value = "Please, fill password field"
            successMessage.value = ""
        }

    }
}