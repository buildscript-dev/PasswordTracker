package com.example.passwordtracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordtracker.data.local.PasswordEvent
import com.example.passwordtracker.data.local.PasswordState
import com.example.passwordtracker.data.local.SortType
import com.example.passwordtracker.ui.AddPasswordDialog

@Composable
fun PasswordScreen(
    state: PasswordState,
    onEvent: (PasswordEvent) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(PasswordEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Password"
                )
            }
        }
    ) { paddingValues ->

        if (state.isAddingPassword) {
            AddPasswordDialog(state = state, onEvent = onEvent)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            // 🔽 Sort Filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SortType.values().forEach { sortType ->
                    Row(
                        modifier = Modifier
                            .clickable { onEvent(PasswordEvent.SortPassword(sortType)) }
                            .padding(end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = state.sortType == sortType,
                            onClick = { onEvent(PasswordEvent.SortPassword(sortType)) }
                        )
                        Text(
                            text = sortType.name.replace("_", " "),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            // 🔐 Password List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.passwords) { password ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF4F4F4)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = password.platform.orEmpty(),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Username: ${password.userName.orEmpty()}",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Email: ${password.email}",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Password: ${password.password}",
                                    fontSize = 14.sp
                                )
                                password.phoneNumber?.let {
                                    Text(text = "Phone: $it", fontSize = 14.sp)
                                }
                            }

                            IconButton(
                                onClick = { onEvent(PasswordEvent.DeletePassword(password)) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Password",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
