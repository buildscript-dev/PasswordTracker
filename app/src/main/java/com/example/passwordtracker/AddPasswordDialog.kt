package com.example.passwordtracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.passwordtracker.data.local.PasswordEvent
import com.example.passwordtracker.data.local.PasswordState

@Composable
fun AddPasswordDialog(
    state: PasswordState,
    onEvent: (PasswordEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(PasswordEvent.HideDialog)
        },
        confirmButton = {
            Button(onClick = {
                onEvent(PasswordEvent.SavePassword)
            }) {
                Text(text = "Save")
            }
        },
        title = {
            Text(text = "Add New Password")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = state.userName,
                    onValueChange = { onEvent(PasswordEvent.SetUserName(it)) },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { onEvent(PasswordEvent.SetEmail(it)) },
                    label = { Text("Email Address") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = { onEvent(PasswordEvent.SetPhoneNumber(it)) },
                    label = { Text("Phone Number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.platform,
                    onValueChange = { onEvent(PasswordEvent.SetPlaform(it)) },
                    label = { Text("Platform (e.g., Instagram, GitHub)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = { onEvent(PasswordEvent.SetPassword(it)) },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = modifier
    )
}
