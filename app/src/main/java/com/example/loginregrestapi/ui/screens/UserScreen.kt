package com.example.loginregrestapi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.loginregrestapi.data.UserDto
import com.example.loginregrestapi.ui.UiState
import com.example.loginregrestapi.vm.UsersViewModel

@Composable
fun UserScreen(
    vm: UsersViewModel,
    onEdit: (Long) -> Unit
) {
    val state = vm.state.collectAsState().value

    LaunchedEffect(Unit) {
        vm.load()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Felhasználók")

        when (state) {
            UiState.Idle -> Text("Készen áll...")
            UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Hiba ${state.message}")
            is UiState.Data -> UsersList
        }
    }

    @Composable
    private fun UsersList(
        users: List<UserDto>
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(users, key = {it.id}){ users ->
                val dismissState = rememberSwipeToDismissBoxState (
                )

                SwipeToDismissBox(
                    state = dismissState,
                    content = {
                        UserRow(user)
                    }
                )
            }
        }
    }

    @Composable
    private fun UserRow(user: UserDto){
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(user.name)
            Text(user.email)
            Text("Születés: ${user.birthDate}")
        }
    }
}