package com.harshjoshi.aroundthecorner.userInterfaces

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.harshjoshi.aroundthecorner.MainActivity.Companion.REQUIRED_PERMISSIONS

@Composable
fun HomeScreen(
    refresh: () -> Unit,
    settings: () -> Unit,
    about: () -> Unit,
    view: @Composable (PaddingValues) -> Unit
) {
    Scaffold (
        topBar = { MyTopAppBar(refresh, settings) },
        bottomBar = { MyBottomBar(refresh = refresh, settings = settings, about = about) },
        content = { view(it) }
    )
}

@Composable
fun MyBottomBar(
    refresh: () -> Unit,
    settings: () -> Unit,
    about: () -> Unit
) {
    BottomAppBar(
        actions = {
            IconButton(onClick = about) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
            }
            IconButton(onClick = settings) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = refresh,
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Refresh, null, tint = MaterialTheme.colorScheme.onPrimary)
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = "Refresh", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    refresh: () -> Unit,
    settings: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Bluetooth Scanner", color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        },
        actions = {
            IconButton(onClick = refresh) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
            IconButton(onClick = settings) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        }
    )
}