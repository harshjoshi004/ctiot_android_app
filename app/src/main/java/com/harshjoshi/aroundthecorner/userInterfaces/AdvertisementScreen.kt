package com.harshjoshi.aroundthecorner.userInterfaces

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.harshjoshi.aroundthecorner.GlobalViewModel
import com.harshjoshi.aroundthecorner.apiRequest.data.Person
import com.harshjoshi.aroundthecorner.apiRequest.presentation.LaundryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementScreen(string: String?, navController: NavController, globalViewModel: GlobalViewModel) {
    val laundryViewModel: LaundryViewModel = viewModel()
    val person: Person = globalViewModel.user.value
    val backPress = {
        navController.popBackStack()
    }
    val getData = {
        if(string == "laundry") {
            laundryViewModel.fetchLaundryMessage(person)
        } else if(string == "baba") {
            laundryViewModel.fetchBabaJuiceMessage(person)
        }
    }

    LaunchedEffect(key1 = Unit) {
        getData()
    }

    //UserInterface
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Ad by $string") },
                navigationIcon = {
                    IconButton(onClick = {backPress()}) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {getData()}) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if(laundryViewModel.message.value.isNotEmpty()) {
                item {
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium,
                        text = laundryViewModel.message.value,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .height(350.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleSmall,
                            text = "No Advertisements by $string",
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}