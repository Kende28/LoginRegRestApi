package com.example.loginregrestapi

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginregrestapi.ui.screens.UserScreen
import com.example.loginregrestapi.vm.UsersViewModel

object Routes {
    const val LOGIN = "Login"
    const val REGISTER = "register"
    const val USERS = "users"
    const val EDIT = "edit"
}

// AppNav: Összeköti a képernyőket
// Itt döntjük el, hogy melyik képernyőről hova lehet navigálni
@Composable
fun AppNav(
    usersVm: UsersViewModel
){
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = Routes.USERS
    ){
        composable(Routes.USERS){
            UserScreen(
                vm = usersVm,
                {id -> }
            )
        }
    }
}