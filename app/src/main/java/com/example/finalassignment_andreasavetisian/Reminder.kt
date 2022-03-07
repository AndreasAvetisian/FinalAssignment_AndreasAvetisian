package com.example.finalassignment_andreasavetisian

import java.util.*

class Reminder(val title: String, val notes: String, val date: String, val time: String) {
    var reminderId = UUID.randomUUID().toString()
}