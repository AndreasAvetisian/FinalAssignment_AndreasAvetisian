package com.example.finalassignment_andreasavetisian

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReminderView(reminderVM: ReminderViewModel) {

    var isHidden by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFFFFFF))
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!isHidden) {
            ReminderInputs(reminderVM)
        } else {
            Column {}
        }

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painter = painterResource( if (!isHidden) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down ),
                contentDescription = "",
                modifier = Modifier.clickable {
                    isHidden = !isHidden
                }
            )
        }

        Divider(thickness = 2.dp)

        LazyColumn {
            items(reminderVM.reminders.value) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF2377A1))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Title: ${item.title}",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Notes: ${item.notes}",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Date: ${item.date}",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Time: ${item.time}",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReminderInputs(reminderVM: ReminderViewModel) {

    var reminderTitle by remember { mutableStateOf("") }
    var reminderNotes by remember { mutableStateOf("") }
    var reminderDate by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = reminderTitle ,
                onValueChange = { reminderTitle = it },
                label = { Text(text = "Title") },
                modifier = Modifier.width(150.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp)
            )

            OutlinedTextField(
                value = reminderDate ,
                onValueChange = { reminderDate = it },
                label = { Text(text = "Date") },
                modifier = Modifier.width(150.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = reminderNotes ,
                onValueChange = { reminderNotes = it },
                label = { Text(text = "Notes") },
                modifier = Modifier.width(150.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp)
            )

            OutlinedTextField(
                value = reminderTime ,
                onValueChange = { reminderTime = it },
                label = { Text(text = "Time") },
                modifier = Modifier.width(150.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp)
            )
        }

        OutlinedButton(
            onClick = {
                reminderVM.addReminder(
                    Reminder(
                        reminderTitle,
                        reminderNotes,
                        reminderDate,
                        reminderTime
                    )
                )
            },
            modifier = Modifier.padding(0.dp, 10.dp),
            colors = ButtonDefaults
                .buttonColors(backgroundColor = Color(0xFF2377A1), contentColor = Color.Black)
        ) {
            Text(
                text = "Add reminder",
                fontSize = 16.sp
            )
        }
    }
}