package com.example.loginregrestapi

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.loginregrestapi.vm.UsersViewModel

object Routes {
    const val LOGIN = "Login"
    const val REGISTER = "register"
    const val  USERS = "users"
    const val  EDIT = "edit"
}

@Composable
fun AppNav(
    usersVm: UsersViewModel
) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = Routes.USERS
    ) {

    }
}