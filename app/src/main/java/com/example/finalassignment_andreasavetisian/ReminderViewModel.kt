package com.example.finalassignment_andreasavetisian

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReminderViewModel: ViewModel() {
    private var fireStore = Firebase.firestore

    fun addReminder(title: String) {

        val reminderInfo = hashMapOf(
            "id" to Firebase.auth.currentUser!!.uid,
            "title" to title
        )

        fireStore
            .collection("reminders")
            .document()
            .set(reminderInfo)
            .addOnSuccessListener {
                Log.d("********", "Reminder added")
            }
            .addOnFailureListener { error ->
                Log.d("********", error.message.toString())
            }
    }
}