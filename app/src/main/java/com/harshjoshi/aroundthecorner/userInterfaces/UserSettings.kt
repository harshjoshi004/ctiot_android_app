package com.harshjoshi.aroundthecorner.userInterfaces

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.harshjoshi.aroundthecorner.GlobalViewModel
import com.harshjoshi.aroundthecorner.apiRequest.data.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingsScreen(navController: NavController, globalViewModel: GlobalViewModel) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(globalViewModel.user.value.name) }
    var gender by remember { mutableStateOf(globalViewModel.user.value.gender) }
    var region by remember { mutableStateOf(globalViewModel.user.value.region) }
    var age by remember { mutableStateOf(globalViewModel.user.value.age) }
    val backPress = {
        navController.popBackStack()
    }
    val editClick = {
        if(name.isNotEmpty() && gender.isNotEmpty() && region.isNotEmpty() && age.isNotEmpty()) {
            val newPerson = Person(
                name = name,
                gender = gender,
                region = region,
                age = age
            )

            globalViewModel.setUser(newPerson, context)
            navController.popBackStack()
        } else {
            Toast.makeText(context, "Invalid Details", Toast.LENGTH_SHORT).show()
        }
    }

    //UserInterface
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "User Settings")},
                navigationIcon = {
                    IconButton(onClick = {backPress()}) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Privacy Mode: ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                    Spacer(modifier = Modifier.size(8.dp))

                    Switch(checked = globalViewModel.mode.value, onCheckedChange = {
                        globalViewModel.toggleMode(context)
                        Toast.makeText(context, "Privacy Mode toggled!", Toast.LENGTH_SHORT).show()
                    })
                }
            }

            if(globalViewModel.mode.value) {
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Name") },
                        placeholder = { Text(text = "Name") }
                    )
                }

                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        value = gender,
                        onValueChange = { gender = it },
                        label = { Text(text = "Gender") },
                        placeholder = { Text(text = "Gender") }
                    )
                }

                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        value = region,
                        onValueChange = { region = it },
                        label = { Text(text = "Region") },
                        placeholder = { Text(text = "Region") }
                    )
                }

                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        value = age,
                        onValueChange = { age = it },
                        label = { Text(text = "Age") },
                        placeholder = { Text(text = "Age") }
                    )
                }

                item {
                    Button(onClick = { editClick() }, modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = "Save Changes..")
                        }
                    }
                }
            } else {
                item {
                    Box(modifier = Modifier.fillMaxSize().heightIn(400.dp, Dp.Unspecified), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No Data is Shared!"
                        )
                    }
                }
            }
        }
    }
}