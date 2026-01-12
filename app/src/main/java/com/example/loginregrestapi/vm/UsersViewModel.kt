package com.example.loginregrestapi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginregrestapi.data.UserDto
import com.example.loginregrestapi.data.UsersApi
import com.example.loginregrestapi.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val api: UsersApi
): ViewModel() {
    private val _state = MutableStateFlow<UiState<List<UserDto>>>(UiState.Idle)

    val state: StateFlow<UiState<List<UserDto>>> = _state

    fun load() {


        viewModelScope.launch {
            try {
                val users = api.getUsers()
                _state.value = UiState.Data(users)
            } catch (t: Throwable) {
                _state.value = UiState.Error(t.message ?: "Hálózati hiba")
            }
        }
    }

    fun deleteUser(id: Long, onDone: () -> Unit) {
        viewModelScope.launch {
            try {
                api.deleteUser(id)
                load()

                onDone()
            } catch (t: Throwable) {
                _state.value = UiState.Error(t.message ?: "Nem sikerült törölni")
            }
        }
    }
}