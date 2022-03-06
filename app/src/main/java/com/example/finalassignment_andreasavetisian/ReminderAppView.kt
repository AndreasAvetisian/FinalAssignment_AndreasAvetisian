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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


const val HOME_ROUTE = "home"
const val REMINDER_ROUTE = "reminder"
const val SETTINGS_ROUTE = "settings"

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
    val darkMode = remember { mutableStateOf(false)}
    val navController = rememberNavController()
    val scState = rememberScaffoldState( rememberDrawerState(DrawerValue.Closed) )

    Scaffold(
        scaffoldState = scState,
        topBar = { TopBarView(scState, darkMode) },
        bottomBar = { BottomBarView(navController, darkMode) },
        content = { MainContentView(navController, darkMode) },
        drawerContent = { DrawerLayoutView(navController, scState, darkMode) }
    )
}

@Composable
fun MainContentView(navController: NavHostController, darkMode: MutableState<Boolean>) {
    val reminderVM = viewModel<ReminderViewModel>()

    NavHost(navController = navController, startDestination = REMINDER_ROUTE ){
        composable( route = HOME_ROUTE ){ HomeView(darkMode) }
        composable( route = REMINDER_ROUTE){ ReminderView(reminderVM, darkMode) }
        composable( route = SETTINGS_ROUTE){ SettingView(darkMode) }
    }
}

@Composable
fun HomeView(darkMode: MutableState<Boolean>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(text = "HOME", color = Color.Black)
    }
}

@Composable
fun ReminderView(reminderVM: ReminderViewModel, darkMode: MutableState<Boolean>) {

    var reminderTitle by remember { mutableStateOf("") }
    var reminderNotes by remember { mutableStateOf("") }
    var reminderDate by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }

    var isHidden by remember { mutableStateOf(false) }

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
                        .buttonColors(backgroundColor = Color(0xFF76FF03), contentColor = Color.Black)
                ) {
                    Text(
                        text = "Add reminder",
                        fontSize = 16.sp
                    )
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
                            .background(Color(0xFF76FF03))
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
fun SettingView(darkMode: MutableState<Boolean>) {

    var darkMode by remember { mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (!darkMode) Color.White else Color.Black)
            .padding(10.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = "Settings",
                fontSize = 24.sp,
                color = if (!darkMode) Color.Black else Color.White
            )
        }

        Divider(thickness = 2.dp)

        Row(
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
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

    }
}

@Composable
fun BottomBarView(navController: NavHostController, darkMode: MutableState<Boolean>) {

    var darkMode by remember { mutableStateOf(false)}

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(if (darkMode) Color(0xFF1C1C1E) else Color(0xFF4586E3))
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "home",
            modifier = Modifier.clickable { navController.navigate(HOME_ROUTE) },
            tint = Color.White
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_reminder),
            contentDescription = "reminder",
            modifier = Modifier.clickable { navController.navigate(REMINDER_ROUTE) },
            tint = Color.White
        )
    }
}

@Composable
fun TopBarView(scState: ScaffoldState, darkMode: MutableState<Boolean>) {

    //var darkMode by remember { mutableStateOf(false)}
    val userVM = viewModel<UserViewModel>()
    val scope = rememberCoroutineScope()

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFF4586E3))
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_drawer),
            contentDescription = "",
            modifier = Modifier.clickable {
                scope.launch {
                    scState.drawerState.open()
                }
            },
            tint = Color.White
        )
        Text(text = userVM.username.value, color = Color.White)
    }
}

@Composable
fun DrawerLayoutView(navController: NavHostController, scState: ScaffoldState, darkMode: MutableState<Boolean>) {

    val userVM = viewModel<UserViewModel>()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Welcome back, ${userVM.username.value}", color = Color.Black)


        OutlinedButton(
            onClick = {
                navController.navigate(SETTINGS_ROUTE)
                scope.launch {
                    scState.drawerState.close()
                }
            },
            colors = ButtonDefaults
                .buttonColors(backgroundColor = Color.Gray, contentColor = Color.White)
        ) {
            Text(text = "Settings")
        }


        OutlinedButton(
            onClick = { userVM.logoutUser() },
            colors = ButtonDefaults
                .buttonColors(backgroundColor = Color.Red, contentColor = Color.White)
        ) {
            Text(text = "Sign out")
        }
    }
}


@Composable
fun LoginView(userVM: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var darkMode by remember { mutableStateOf(false)}

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