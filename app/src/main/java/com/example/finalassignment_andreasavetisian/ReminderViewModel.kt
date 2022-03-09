package com.example.finalassignment_andreasavetisian

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReminderViewModel: ViewModel() {
    //var reminders = mutableStateOf(listOf<Reminder>())
    private var fireStore = Firebase.firestore

    fun addReminder(title: String, notes: String, date: String, time: String) {

        val reminderInfo = hashMapOf(
            "id" to Firebase.auth.currentUser!!.uid,
            "title" to title,
            "notes" to notes,
            "date" to date,
            "time" to time
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

    fun deleteReminder() {

    }

    fun deleteAllReminders() {

    }
}