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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ReminderView() {

    var reminderTitle by remember { mutableStateOf("") }

    var isHidden by remember { mutableStateOf(false) }

    val reminderVM = viewModel<ReminderViewModel>()

    var titlesList by remember { mutableStateOf(mutableListOf<String>()) }

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
                        label = { Text(text = "Reminder") },
                        modifier = Modifier
                            .width(370.dp)
                            .padding(24.dp, 0.dp),
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp)
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            reminderVM.addReminder(reminderTitle)
                            reminderTitle = ""
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

        val fireStore = Firebase.firestore
        fireStore
            .collection("reminders")
            .whereEqualTo("id", Firebase.auth.currentUser!!.uid) // IMPORTANT!!!
            .get()
            .addOnSuccessListener {

                val titles = mutableListOf<String>()
                for (document in it) {

                    titles.add( document.get("title").toString() )
                    titlesList = titles

                }

            }

        LazyColumn {
            items(titlesList) {
                if (it.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .width(370.dp)
                            .padding(24.dp, 12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF2377A1))
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = it,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        } // Row
                    } // Card
                } // if statement
            } // items
        } // Lazy Column
    } //  Main Column
} // Composable