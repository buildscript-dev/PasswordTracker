package com.example.passwordtracker.data.local

import java.util.Objects

sealed class PasswordEvent {

    object SavePassword: PasswordEvent()

    data class SetPlaform(val platform: String): PasswordEvent()

    data class SetUserName(val userName: String): PasswordEvent()

    data class SetEmail(val email: String): PasswordEvent()

    data class SetPassword(val password: String): PasswordEvent()

    data class SetPhoneNumber(val phoneNumber: String): PasswordEvent()

    object ShowDialog: PasswordEvent()

    object HideDialog: PasswordEvent()

    data class SortPassword(val sortType: SortType): PasswordEvent()

    data class DeletePassword(val password: Password): PasswordEvent()
}
