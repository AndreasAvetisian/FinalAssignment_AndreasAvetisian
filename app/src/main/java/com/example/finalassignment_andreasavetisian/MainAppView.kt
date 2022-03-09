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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


const val HOME_ROUTE = "home"
const val REMINDER_ROUTE = "reminder"
const val SETTINGS_ROUTE = "settings"

@Composable
fun MainView() {
    val userVM = viewModel<UserViewModel>()

    if(userVM.successMessage.value.isEmpty()){
        LoginView(userVM)
        userVM.errorMessage.value = ""
    }else {
        MainScaffoldView()
    }
}

@Composable
fun MainScaffoldView() {
    //val darkMode = remember { mutableStateOf(false)}
    val navController = rememberNavController()
    val scState = rememberScaffoldState( rememberDrawerState(DrawerValue.Closed) )

    Scaffold(
        scaffoldState = scState,
        topBar = { TopBarView(scState) },
        bottomBar = { BottomBarView(navController) },
        content = { MainContentView(navController) },
        drawerContent = { DrawerLayoutView(navController, scState) }
    )
}

@Composable
fun MainContentView(navController: NavHostController) {
    val reminderVM = viewModel<ReminderViewModel>()

    NavHost(navController = navController, startDestination = SETTINGS_ROUTE ){
        composable( route = HOME_ROUTE ){ HomeView() }
        composable( route = REMINDER_ROUTE){ ReminderView(reminderVM) }
        composable( route = SETTINGS_ROUTE){ SettingView() }
    }
}

@Composable
fun HomeView() {

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
fun SettingView() {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var isHiddenPw by remember { mutableStateOf(true) }
    var country by remember { mutableStateOf("") }
    val userVM = viewModel<UserViewModel>()


    var currentUserName by remember { mutableStateOf("") }
    var currentUserEmail = Firebase.auth.currentUser?.email
    var currentUserFlag by remember { mutableStateOf("") }

    val fireStore = Firebase.firestore

    fireStore
        .collection("userNames")
        .document(Firebase.auth.currentUser?.uid.toString())
        .get()
        .addOnSuccessListener {
            currentUserName = it.get("userName").toString()
        }

    fireStore
        .collection("flags")
        .document(Firebase.auth.currentUser?.uid.toString())
        .get()
        .addOnSuccessListener {
            currentUserFlag = it.get("countryFlag").toString()
        }

    //var darkMode by remember { mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = "Settings",
                fontSize = 24.sp,
                color = Color.Black
            )
        }

        Divider(thickness = 2.dp)
        
        Spacer(modifier = Modifier.height(10.dp))

        Column {

            OutlinedTextField(
                value = name ,
                onValueChange = { name = it },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_name),
                        contentDescription = "name icon",
                        tint = Color(0xFF4586E3)
                    )
                },
                placeholder = { Text(text = currentUserName) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = email ,
                onValueChange = { email = it },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_email),
                        contentDescription = "email icon",
                        tint = Color(0xFF4586E3)
                    )
                },
                placeholder = { Text(text = currentUserEmail.toString()) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = pw ,
                onValueChange = { pw = it },
                placeholder = { Text(text = "New password") },
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

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_flag),
                        contentDescription = "country icon",
                        tint = Color(0xFF4586E3)
                    )
                },
                placeholder = { Text(text = currentUserFlag) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {

                    if (name.isNotEmpty()) {
                        userVM.modifyUserName(name)
                        name = ""
                        userVM.successMessage.value = "Name updated"
                    }
                    if (email.isNotEmpty()) {
                        userVM.modifyUserEmail(email)
                        email = ""
                        userVM.successMessage.value = "Email updated"
                    }
                    if (pw.isNotEmpty()) {
                        userVM.modifyUserPassword(pw)
                        pw = ""
                        userVM.successMessage.value = "Password updated"
                    }
                    if (country.isNotEmpty()) {
                        userVM.modifyUserFlag(country)
                        country = ""
                        userVM.successMessage.value = "Country updated"
                    }

                },
                colors = ButtonDefaults
                    .buttonColors(backgroundColor = Color.Gray, contentColor = Color.White)
            ) {
                Text(text = "Update")
            }
        }

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

        // Password: 123456

//        Row(
//            modifier = Modifier
//                .padding(0.dp, 10.dp, 0.dp, 0.dp)
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

    }
}

@Composable
fun BottomBarView(navController: NavHostController) {

    //var darkMode by remember { mutableStateOf(false)}

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFF4586E3))
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
fun TopBarView(scState: ScaffoldState) {
    //var darkMode by remember { mutableStateOf(false)}
    val scope = rememberCoroutineScope()

    var currentUserName by remember { mutableStateOf("") }
    var currentUserFlag by remember { mutableStateOf("") }
    val fireStore = Firebase.firestore

    fireStore
        .collection("userNames")
        .document(Firebase.auth.currentUser?.uid.toString())
        .get()
        .addOnSuccessListener {
            currentUserName = it.get("userName").toString()
        }

    fireStore
        .collection("flags")
        .document(Firebase.auth.currentUser?.uid.toString())
        .get()
        .addOnSuccessListener {
            currentUserFlag = it.get("countryFlag").toString()
        }

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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://countryflagsapi.com/png/${currentUserFlag}",
                contentDescription = "",
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = currentUserName,
                color = Color.White,
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    }
}

@Composable
fun DrawerLayoutView(navController: NavHostController, scState: ScaffoldState) {

    val userVM = viewModel<UserViewModel>()
    val scope = rememberCoroutineScope()

    var currentUserName by remember { mutableStateOf("") }
    val fireStore = Firebase.firestore

    fireStore
        .collection("userNames")
        .document(Firebase.auth.currentUser?.uid.toString())
        .get()
        .addOnSuccessListener {
            currentUserName = it.get("userName").toString()
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "",
                tint = Color(0xFF4586E3)
            )

            Text(
                text = "ReminderApp.AA",
                color = Color(0xFF4586E3),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 30.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Welcome back, ",
                color = Color(0xFF4586E3),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = currentUserName,
                fontSize = 20.sp,
                color = Color(0xFF4586E3)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
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
}