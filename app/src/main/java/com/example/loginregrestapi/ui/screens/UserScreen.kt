package com.example.loginregrestapi.ui.screens

import android.util.Log.d
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    val deleteCandidate = remember { mutableStateOf<UserDto?>(null)}

    LaunchedEffect(Unit) {
        vm.load()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Felhasználók")

        OutlinedButton(onClick = vm::load) {
            Text("Frissítés")
        }

        when (state) {
            UiState.Idle -> Text("Készen áll...")
            UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Hiba ${state.message}")
            is UiState.Data -> UsersList(users = state.value, onSwipeDelete = {user -> deleteCandidate.value = user})
        }
    }

    if (deleteCandidate.value != null){
        val u = deleteCandidate.value!!

        AlertDialog(
            onDismissRequest = {deleteCandidate.value = null},
            title = {Text("Törlés")},
            text = {Text("Biztos törlöd: ${u.name}?")},
            confirmButton = {
                Button(
                    onClick = {
                        vm.deleteUser(u.id) {
                            deleteCandidate.value = null
                        }
                    }
                ) {
                    Text("Igen")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {deleteCandidate.value = null}
                ) {
                    Text("Mégse")
                }
            }
        )
    }
}

@Composable
private fun UsersList(
    users: List<UserDto>,
    onSwipeDelete: (UserDto) -> Unit,
    //onSwipeEdit: (UserDto) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(users, key = { it.id }) { user ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {value ->
                    when(value){
                        SwipeToDismissBoxValue.EndToStart -> {
                            onSwipeDelete(user)
                            false
                        }

                       /* SwipeToDismissBoxValue.StartToEnd -> {
                            onSwipeEdit(user)
                            false
                        }*/
                        else -> false
                    }
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    SwipeBackground(value = dismissState)
                },
                content = {
                    UserRow(user)
                }
            )
        }
    }
}

@Composable
fun SwipeBackground(value: SwipeToDismissBoxState) {

}


@Composable
private fun UserRow(user: UserDto) {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        Text(user.name)
        Text(user.email)
        Text("Születés: ${user.birthDate}")
    }
}
