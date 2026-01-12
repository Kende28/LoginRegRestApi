package com.example.loginregrestapi.vm


import com.example.loginregrestapi.data.UpdateUserRequest
import com.example.loginregrestapi.data.UsersApi
import com.example.loginregrestapi.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EditForm(
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val birthDate: String = ""
)

class EditUserViewModel(
    private val api: UsersApi,
    private  val userId: Long
) {
    private val _state = MutableStateFlow<UiState<EditForm>>(UiState.Idle)

    val state: StateFlow<UiState<EditForm>> = _state

    init {
        load()
    }

    private fun load() {
        _state.value = UiState.Loading

        viewModelScope.launch {
            try {
                val u = api.getUser(userId)

                _state.value = UiState.Data(
                    EditForm(
                        id = u.id,
                        name = u.name,
                        email = u.email,
                        password = u.password,
                        birthDate = u.birthDate
                    )
                )
            } catch (t: Throwable) {
                _state.value = UiState.Error(t.message ?: "Nem sikerült betölteni")
            }
        }
    }

    fun onName(v: String) = update {it.copy(name = v)}
    fun onEmail(v: String) = update {it.copy(email = v)}
    fun onPassword(v: String) = update {it.copy(password = v)}
    fun onBirthdate(v: String) = update {it.copy(birthDate = v)}

    fun save(onDone: () -> Unit) {
        val form = (_state.value as? UiState.Data)?.value ?: return
        _state.value = UiState.Loading

        viewModelScope.launch {
            try {
                api.updateUser(
                    id = form.id,
                    req = UpdateUserRequest(
                        name = form.name,
                        email = form.email,
                        password = form.password,
                        birthDate = form.birthDate
                    )
                )
            } catch (t: Throwable) {
                _state.value = UiState.Error(t.message ?: "Nem sikerült menteni")
            }
        }
    }

    private fun update(block: (EditForm) -> EditForm) {
        val current = (_state.value as? UiState.Data)?.value ?: return
        _state.value = UiState.Data(block(current))
    }
}