package dev.pseudo.userapi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pseudo.userapi.data.model.User
import dev.pseudo.userapi.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private var isLoaded = false

    fun loadUsers(forceRefresh: Boolean = false) {
        if (isLoaded && !forceRefresh) return

        _uiState.value = UiState.Loading

        viewModelScope.launch {
            repository.getUsers(forceRefresh)
                .catch { e ->
                    val message = when (e) {
                        is IOException -> "Проблемы с сетью. Проверьте интернет и попробуйте снова."
                        is TimeoutException -> "Время ожидания ответа истекло."
                        else -> "Произошла неизвестная ошибка."
                    }
                    _uiState.value = UiState.Error(message)
                }
                .collect { users ->
                    _uiState.value = UiState.Success(users)
                    isLoaded = true
                }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val users: List<User>) : UiState()
        data class Error(val message: String) : UiState()
    }
}