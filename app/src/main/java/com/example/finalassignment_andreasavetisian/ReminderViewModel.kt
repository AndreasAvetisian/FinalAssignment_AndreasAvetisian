package com.example.finalassignment_andreasavetisian

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReminderViewModel: ViewModel() {
    var reminders = mutableStateOf(listOf<Reminder>())

    fun addReminder(reminder: Reminder) {
        val newReminder = reminders.value.toMutableList()
        newReminder.add(reminder)
        reminders.value = newReminder
    }

    fun deleteReminder(reminder: Reminder) {
        val newReminder = reminders.value.toMutableList()
        newReminder.retainAll { it.id_num != it.id_num }
        newReminder.remove(reminder)
        reminders.value = newReminder
    }
}