package com.example.finalassignment_andreasavetisian

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginView(userVM: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var isHiddenPw by remember { mutableStateOf(true) }
    var country by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    var logIn by remember { mutableStateOf(true) }
    var signIn by remember { mutableStateOf(false) }

    //var darkMode by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
            //.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(Color(0xFF4586E3))
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ReminderApp.AA",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "",
                tint = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Spacer(modifier = Modifier.height(20.dp))

            if (signIn) {
                OutlinedTextField(
                    value = name ,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_name),
                            contentDescription = "name icon",
                            tint = Color(0xFF4586E3)
                        )
                    }
                )
            }

            OutlinedTextField(
                value = email ,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_email),
                        contentDescription = "email icon",
                        tint = Color(0xFF4586E3)
                    )
                }
            )

            OutlinedTextField(
                value = pw ,
                onValueChange = { pw = it },
                label = { Text(text = "Password") },
                visualTransformation = if (isHiddenPw) PasswordVisualTransformation() else VisualTransformation.None ,
                trailingIcon = {
                    Icon(
                        painter = painterResource(if (isHiddenPw) R.drawable.ic_eye else R.drawable.ic_close_eye),
                        contentDescription = "password icon",
                        tint = Color(0xFF4586E3),
                        modifier = Modifier.clickable {
                            isHiddenPw = !isHiddenPw
                        }
                    )
                }
            )
            
            if (logIn) {
                OutlinedButton(
                    onClick = { userVM.loginUser(name, email, pw) },
                    modifier = Modifier
                        .padding(10.dp),
                    colors = ButtonDefaults
                        .buttonColors(backgroundColor = Color.Gray, contentColor = Color.White)
                ) {
                    Text(text = "Login")
                }
            } else if (signIn) {
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    label = { Text(text = "Country") },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_flag),
                            contentDescription = "country icon",
                            tint = Color(0xFF4586E3)
                        )
                    }
                )
                OutlinedButton(
                    onClick = {
                        userVM.signinUser(name, email, pw, country)
                        email = ""
                        pw = ""
                        country = ""
                        name = ""
                    },
                    modifier = Modifier
                        .padding(10.dp),
                    colors = ButtonDefaults
                        .buttonColors(backgroundColor = Color.Gray, contentColor = Color.White)
                ) {
                    Text(text = "Sign in")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Checkbox(
                        checked = logIn,
                        onCheckedChange = {
                            logIn = !logIn
                            signIn = !signIn
                            userVM.successMessage.value = ""
                        }
                    )
                    Text(text = "Log in")
                }
                Row {
                    Checkbox(
                        checked = signIn,
                        onCheckedChange = {
                            signIn = !signIn
                            logIn = !logIn
                            userVM.errorMessage.value = ""
                        }
                    )
                    Text(text = "Sign in")
                }
            }

//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//                .padding(10.dp),
//            horizontalArrangement = Arrangement.End
//        ) {
//            Checkbox(
//                checked = darkMode,
//                onCheckedChange = { darkMode = !darkMode }
//            )
//            Text(
//                text = "Dark mode",
//                color = if (darkMode) Color.White else Color.Black
//            )
//        }

            if (userVM.errorMessage.value.isNotEmpty()) {
                Text(
                    text = userVM.errorMessage.value,
                    fontSize = 18.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                )
            } else if (userVM.successMessage.value.isNotEmpty()) {
                Text(
                    text = userVM.successMessage.value,
                    fontSize = 18.sp,
                    color = Color.Green,
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                )
            }
        }

    }
}