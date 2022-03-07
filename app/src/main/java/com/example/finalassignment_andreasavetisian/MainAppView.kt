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

    NavHost(navController = navController, startDestination = REMINDER_ROUTE ){
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
fun DrawerLayoutView(navController: NavHostController, scState: ScaffoldState) {

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