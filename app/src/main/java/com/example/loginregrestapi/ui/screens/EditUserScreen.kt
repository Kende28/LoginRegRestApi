package com.example.loginregrestapi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.loginregrestapi.ui.UiState
import com.example.loginregrestapi.vm.EditUserViewModel

@Composable
fun EditUserScreen(
    vm: EditUserViewModel,
    onDone: () -> Unit
) {
    val state = vm.state.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Felhasznáéó szerkesztése")

        when(state) {
            UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Hiba ${state.message}")
            is UiState.Data -> {
                val f = state.value

                OutlinedTextField(value = f.name, onValueChange = vm::onName, label = {Text("Név")})
                OutlinedTextField(value = f.email, onValueChange = vm::onEmail, label = {Text("E-mail")})
                OutlinedTextField(value = f.password, onValueChange = vm::onPassword, label = {Text("Jelszó")})
                OutlinedTextField(value = f.birthDate, onValueChange = vm::onBirthdate, label = {Text("Születés")})

                Button(onClick = {vm.save(onDone)}) { Text("Mentés") }
            }
            else -> Unit
        }
    }
}