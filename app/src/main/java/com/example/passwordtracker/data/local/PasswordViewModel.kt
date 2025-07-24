package com.example.passwordtracker.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PasswordViewModel(
    private val dao: PasswordDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.USERNAME)

    private val _passwords = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.PLATFORM -> dao.getContactsOrderedByPlatform()
                SortType.EMAIL -> dao.getContactsOrderedByEmail()
                SortType.USERNAME -> dao.getContactsOrderedByUserName()
                SortType.PHONE_NUMBER -> dao.getContactsOrderedByPhoneNumber()
                SortType.DATA_ADDED -> dao.getContactsOrderedByRecentDate()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(PasswordState())

    val state = combine(_state, _sortType, _passwords) { state, sortType, passwords ->
        state.copy(
            passwords = passwords,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PasswordState())

    fun onEvent(event: PasswordEvent) {
        when (event) {
            is PasswordEvent.DeletePassword -> {
                viewModelScope.launch {
                    dao.deletePassword(event.password)
                }
            }

            PasswordEvent.HideDialog -> {
                _state.update {
                    it.copy(isAddingPassword = false)
                }
            }

            PasswordEvent.SavePassword -> {
                val currentState = _state.value

                val userName = currentState.userName
                val email = currentState.email
                val phoneNumber = currentState.phoneNumber
                val platform = currentState.platform
                val passwordValue = currentState.password

                if (email.isBlank() || passwordValue.isBlank() || platform.isBlank()) {
                    return
                }

                val newPassword = Password(
                    userName = userName,
                    platform = platform,
                    phoneNumber = phoneNumber,
                    email = email,
                    password = passwordValue,
                    recentDate = System.currentTimeMillis().toString()
                )

                viewModelScope.launch {
                    dao.updatePassword(newPassword)
                }

                _state.update {
                    it.copy(
                        isAddingPassword = false,
                        userName = "",
                        email = "",
                        phoneNumber = "",
                        password = "",
                        platform = ""
                    )
                }
            }

            is PasswordEvent.SetUserName -> {
                _state.update {
                    it.copy(userName = event.userName)
                }
            }

            is PasswordEvent.SetPlaform -> {
                _state.update {
                    it.copy(platform = event.platform)
                }
            }

            is PasswordEvent.SetEmail -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }

            is PasswordEvent.SetPhoneNumber -> {
                _state.update {
                    it.copy(phoneNumber = event.phoneNumber)
                }
            }

            is PasswordEvent.SetPassword -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }

            PasswordEvent.ShowDialog -> {
                _state.update {
                    it.copy(isAddingPassword = true)
                }
            }

            is PasswordEvent.SortPassword -> {
                _sortType.value = event.sortType
            }
        }
    }
}
