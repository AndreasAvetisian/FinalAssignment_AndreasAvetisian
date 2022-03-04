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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


const val HOME_ROUTE = "home"
const val REMINDER_ROUTE = "reminder"

@Composable
fun MainView() {
    val userVM = viewModel<UserViewModel>()

    if(userVM.username.value.isEmpty()){
        LoginView(userVM)
        userVM.errorMessage.value = ""
    }else {
        MainScaffoldView()
    }
}

@Composable
fun MainScaffoldView() {

    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBarView() },
        bottomBar = { BottomBarView(navController) },
        content = { MainContentView(navController) }
    )
}

@Composable
fun MainContentView(navController: NavHostController) {
    val reminderVM = viewModel<ReminderViewModel>()

    NavHost(navController = navController, startDestination = HOME_ROUTE ){
        composable( route = HOME_ROUTE ){ HomeView() }
        composable( route = REMINDER_ROUTE){ NoteView(reminderVM) }
    }
}

@Composable
fun HomeView() {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFD8BF75))) {

    }
}

@Composable
fun NoteView(reminderVM: ReminderViewModel) {

    var reminderTitle by remember { mutableStateOf("") }
    var reminderNotes by remember { mutableStateOf("") }
    var reminderDate by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFEDFF8A))
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = reminderTitle ,
            onValueChange = { reminderTitle = it },
            label = { Text(text = "Todo note") }
        )
        OutlinedTextField(
            value = reminderNotes ,
            onValueChange = { reminderNotes = it },
            label = { Text(text = "Todo note") }
        )
        OutlinedTextField(
            value = reminderDate ,
            onValueChange = { reminderDate = it },
            label = { Text(text = "Todo note") }
        )
        OutlinedTextField(
            value = reminderTime ,
            onValueChange = { reminderTime = it },
            label = { Text(text = "Todo note") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { reminderVM.addReminder( Reminder(reminderTitle, reminderNotes, reminderDate, reminderTime) ) }
        ) {
            Text(text = "Add todo note")
        }

        Spacer(modifier = Modifier.height(10.dp))

        reminderVM.reminders.value.forEach {
            Divider(thickness = 2.dp)
            Text(text = it.title)
            Text(text = it.notes)
            Text(text = it.date)
            Text(text = it.time)
        }
        Divider(thickness = 2.dp)
    }
}

@Composable
fun BottomBarView(navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFF7CA43)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "home",
            modifier = Modifier.clickable {  navController.navigate(HOME_ROUTE)  })
        Icon(
            painter = painterResource(id = R.drawable.ic_reminder),
            contentDescription = "note",
            modifier = Modifier.clickable {  navController.navigate(REMINDER_ROUTE)  })
    }
}

@Composable
fun TopBarView() {
    val userVM = viewModel<UserViewModel>()

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFF7CA43))
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = userVM.username.value)
        OutlinedButton(onClick = { userVM.logoutUser() }) {
            Text(text = "Log out")
        }
    }
}


@Composable
fun LoginView(userVM: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email ,
            onValueChange = { email = it },
            label = { Text(text = "Email") })
        OutlinedTextField(
            value = pw ,
            onValueChange = { pw = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation())
        OutlinedButton(onClick = { userVM.loginUser(email,pw) }) {
            Text(text = "Login")
        }
    }
    if (userVM.errorMessage.value.isNotEmpty()) {
        Text(
            text = userVM.errorMessage.value,
            fontSize = 18.sp,
            color = Color.Red
        )
    }
}