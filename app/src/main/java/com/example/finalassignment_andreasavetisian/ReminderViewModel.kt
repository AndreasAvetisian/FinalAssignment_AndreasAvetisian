package com.example.finalassignment_andreasavetisian

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReminderViewModel: ViewModel() {
    var reminders = mutableStateOf(listOf<Reminder>())

    fun addReminder(reminder: Reminder) {
        val newReminder = reminders.value.toMutableList()
        newReminder.add(reminder)
        reminders.value = newReminder
    }

    fun deleteReminder(id: String) {

        //val newReminder = reminders.value.toMutableList()
        //Log.d("*******", reminders.value.toString())
        //Log.d("*******", reminders.value[id.toInt()].toString())
        //newReminder.indexOf(id.toInt())
        //reminders.value = newReminder

        val numbers = mutableListOf(1,2,3,4,2,5)
        Log.d("*******", numbers.toString())
        Log.d("*******", numbers.lastIndexOf(2).toString())
        numbers.clear()
        Log.d("*******", numbers.toString())


//        val newReminder = reminders.value.toMutableList()
//        newReminder.retainAll { it.id_num >= it.id_num }
//        newReminder.clear()
//        //newReminder.remove(reminder)
//        reminders.value = newReminder
    }

    fun deleteAllReminders() {
        val newReminder = reminders.value.toMutableList()
        newReminder.clear()
        reminders.value = newReminder
    }
}