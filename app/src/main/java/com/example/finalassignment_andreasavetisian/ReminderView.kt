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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ReminderView() {

    var reminderTitle by remember { mutableStateOf("") }
    var reminderNotes by remember { mutableStateOf("") }
    var reminderDate by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }

    var isHidden by remember { mutableStateOf(false) }

    val reminderVM = viewModel<ReminderViewModel>()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFFFFFF))
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!isHidden) {
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            reminderVM
                                .addReminder(
                                    reminderTitle,
                                    reminderNotes,
                                    reminderDate,
                                    reminderTime
                                )


                            reminderTitle = ""
                            reminderNotes = ""
                            reminderDate = ""
                            reminderTime = ""
                        },
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults
                            .buttonColors(backgroundColor = Color(0xFF2377A1), contentColor = Color.Black)
                    ) {
                        Text(
                            text = "Add reminder",
                            fontSize = 16.sp
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            /* TODO */
                        },
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults
                            .buttonColors(backgroundColor = Color.Red, contentColor = Color.Black)
                    ) {
                        Text(
                            text = "Delete all",
                            fontSize = 16.sp
                        )
                    }
                }
            }
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

//---------------------------------------------------------------------------------

        var reminderTitleList by remember {
            mutableStateOf(mutableListOf<String>())
        }
        var reminderNotesList by remember {
            mutableStateOf(mutableListOf<String>())
        }
        var reminderDateList by remember {
            mutableStateOf(mutableListOf<String>())
        }
        var reminderTimeList by remember {
            mutableStateOf(mutableListOf<String>())
        }

        val fireStore = Firebase.firestore
        fireStore
            .collection("reminders")
            .get()
            .addOnSuccessListener {

                var titleValue = mutableListOf<String>()
                var notesValue = mutableListOf<String>()
                var dateValue = mutableListOf<String>()
                var timeValue = mutableListOf<String>()
                for (doc in it) {

                    titleValue.add(doc.get("title").toString())
                    reminderTitleList = titleValue

                    notesValue.add(doc.get("notes").toString())
                    reminderNotesList = notesValue

                    dateValue.add(doc.get("date").toString())
                    reminderDateList = dateValue

                    timeValue.add(doc.get("time").toString())
                    reminderTimeList = timeValue

                }

            }

        reminderTitleList.forEach { 
            Text(text = it)
        }
        reminderNotesList.forEach {
            Text(text = it)
        }
        reminderDateList.forEach {
            Text(text = it)
        }
        reminderTimeList.forEach {
            Text(text = it)
        }

//----------------------------------------------------------------------------------

//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(155.dp)
//                .padding(24.dp, 12.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFF2377A1))
//                    .padding(10.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                ) {
//                    Text(
//                        text = "Title: ",
//                        fontSize = 20.sp,
//                        color = Color.Black
//                    )
//                    Text(
//                        text = "Notes: ",
//                        fontSize = 20.sp,
//                        color = Color.Black
//                    )
//                    Text(
//                        text = "Date: ",
//                        fontSize = 20.sp,
//                        color = Color.Black
//                    )
//                    Text(
//                        text = "Time: ",
//                        fontSize = 20.sp,
//                        color = Color.Black
//                    )
//                }
//                Column(
//                    modifier = Modifier
//                        .fillMaxHeight(),
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Checkbox(
//                        checked = false,
//                        onCheckedChange = {  }
//                    )
//                    Text(text = "Id: ")
//                    Icon(
//                        painter = painterResource(R.drawable.ic_delete),
//                        contentDescription = "",
//                        tint = Color.Red,
//                        modifier = Modifier.clickable {
//                        /* TODO */
//                        } // Modifier.clickable
//                    ) // Icon
//                } // Column
//            } // Row
//        } // Card


    } //  Main Column
} // Composable

// https://countryflagapi.com/png/Vientam