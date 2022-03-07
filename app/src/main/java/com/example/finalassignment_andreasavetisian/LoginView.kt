package com.example.finalassignment_andreasavetisian

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginView(userVM: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var darkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkMode) Color(0xFF1C1C1E) else Color.White)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = email ,
            onValueChange = { email = it },
            label = { Text(text = "Email") }
        )

        OutlinedTextField(
            value = pw ,
            onValueChange = { pw = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedButton(
            onClick = { userVM.loginUser(email,pw) },
            modifier = Modifier
                .padding(10.dp),
            colors = ButtonDefaults
                .buttonColors(backgroundColor = Color.Gray, contentColor = Color.White)
        ) {
            Text(text = "Login")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Checkbox(
                checked = darkMode,
                onCheckedChange = { darkMode = !darkMode }
            )
            Text(
                text = "Dark mode",
                color = if (darkMode) Color.White else Color.Black
            )
        }

        if (userVM.errorMessage.value.isNotEmpty()) {
            Text(
                text = userVM.errorMessage.value,
                fontSize = 18.sp,
                color = Color.Red
            )
        }
    }
}