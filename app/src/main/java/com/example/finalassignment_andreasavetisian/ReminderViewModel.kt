package com.example.finalassignment_andreasavetisian

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReminderViewModel: ViewModel() {
    var reminders = mutableStateOf(listOf<Reminder>())

    fun addReminder(reminder: Reminder) {
        var newReminder = reminders.value.toMutableList()
        newReminder.add(reminder)
        reminders.value = newReminder
    }
}