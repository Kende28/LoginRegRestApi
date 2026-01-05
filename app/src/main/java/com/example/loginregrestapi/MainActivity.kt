package com.example.loginregrestapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.loginregrestapi.data.RetrofitProvider
import com.example.loginregrestapi.data.UsersApi
import com.example.loginregrestapi.ui.theme.LoginRegRestApiTheme
import com.example.loginregrestapi.vm.UsersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = RetrofitProvider.create()

        val api = retrofit.create(UsersApi::class.java)

        val usersVm = UsersViewModel(api)
        enableEdgeToEdge()
        setContent {
            AppNav(usersVm)
        }
    }
}

