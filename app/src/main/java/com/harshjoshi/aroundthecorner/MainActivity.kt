package com.harshjoshi.aroundthecorner

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.harshjoshi.aroundthecorner.apiRequest.data.Person
import com.harshjoshi.aroundthecorner.userInterfaces.AboutScreen
import com.harshjoshi.aroundthecorner.userInterfaces.AdvertisementScreen
import com.harshjoshi.aroundthecorner.userInterfaces.HomeScreen
import com.harshjoshi.aroundthecorner.userInterfaces.UserSettingsScreen

class MainActivity : ComponentActivity() {
    private lateinit var bluetoothLeScanner: BluetoothLeScanner
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val scanResults = mutableStateOf<List<ScanResult>>(emptyList())
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val TAG = "BLEScanner"
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }

    // Launcher for permission requests
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (allGranted) {
            Log.d(TAG, "All permissions granted")
            startComprehensiveScan()
        } else {
            Log.d(TAG, "Some permissions denied")
        }
    }

    // Launcher for Bluetooth enable request
    private val enableBluetoothLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            startComprehensiveScan()
        } else {
            Log.d(TAG, "Bluetooth not enabled")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val prevname:String = sharedPreferences.getString("name", null)?:""
        val prevgender:String = sharedPreferences.getString("gender", null)?:""
        val prevregion:String = sharedPreferences.getString("region", null)?:""
        val prevage:String = sharedPreferences.getString("age", null)?:""

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothLeScanner = bluetoothManager.adapter.bluetoothLeScanner
        bluetoothAdapter = bluetoothManager.adapter

        setContent {
            MaterialTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                val globalViewModel: GlobalViewModel = viewModel()

                globalViewModel.setUser(
                    user = Person(
                        name = prevname,
                        gender = prevgender,
                        region = prevregion,
                        age = prevage
                    ),
                    context = context
                )

                val scanStartFunction = {
                    // Check and request permissions
                    if (hasAllPermissions()) {
                        startComprehensiveScan()
                    } else {
                        permissionLauncher.launch(REQUIRED_PERMISSIONS)
                    }
                }

                NavHost(
                    navController = navController as NavHostController,
                    startDestination = "main"
                ){
                    composable("settings"){
                        UserSettingsScreen(
                            navController = navController,
                            globalViewModel = globalViewModel
                        )
                    }

                    composable(
                        route = "ad/{name}",
                        arguments = listOf(navArgument("name") { type = NavType.StringType })
                    ){ backStackEntry->
                        val name = backStackEntry.arguments?.getString("name")
                        AdvertisementScreen(string = name, navController = navController, globalViewModel = globalViewModel)
                    }

                    composable("about"){
                        AboutScreen(navController = navController, globalViewModel = globalViewModel)
                    }

                    composable("main"){
                        HomeScreen(
                            refresh = scanStartFunction,
                            settings = {
                                navController.navigate("settings")
                            },
                            about = {
                                navController.navigate("about")
                            }
                        ) { paddingValues ->
                            val context = LocalContext.current
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {
                                items(scanResults.value) { result ->
                                    Card(
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .padding(top = 8.dp),
                                        onClick = { /*TODO*/ }
                                    ) {
                                        Box(modifier = Modifier) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp)
                                            ) {
                                                Text(
                                                    color = MaterialTheme.colorScheme.primary,
                                                    text = result.device.name ?: "Unknown",
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.titleMedium
                                                )

                                                Text("Address: ${result.device.address}")
                                                Text("Name: ${result.device.name ?: "Unknown"}")
                                                Text("RSSI: ${result.rssi}")

                                                Spacer(modifier = Modifier.height(8.dp))
                                            }

                                            ElevatedButton(
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .align(Alignment.TopEnd),
                                                onClick = {
                                                    if(globalViewModel.mode.value) {
                                                        val name = result.device.name ?: "Unknown"
                                                        navController.navigate("ad/$name")
                                                    } else {
                                                        Toast.makeText(
                                                            context,
                                                            "You are in Private-Mode, switch to public mode by going to settings to use our feature!",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            ) {
                                                Text(text = "Visit..")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hasAllPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun startComprehensiveScan() {
        Log.d(TAG, "Starting comprehensive scan")
        val scanFilter = ScanFilter.Builder()
            .setDeviceAddress("DC:A6:32:7C:94:A9")
            .build()

        // Ensure Bluetooth is on
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            enableBluetoothLauncher.launch(enableBtIntent)
            return
        }

        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setReportDelay(0)
            .build()

        try {
            bluetoothLeScanner.startScan(
                listOf(scanFilter),
                scanSettings,
                scanCallback
            )
        } catch (e: SecurityException) {
            Log.e(TAG, "Security exception during scan", e)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error during scan", e)
        }
    }

    // Separate scan callback to update UI state
    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            // Existing device logging
            Log.d(TAG, "Device found: ${result.device.address} - ${result.device.name}")

            // Extract and decode advertisement payload
            val scanRecord = result.scanRecord
            val rawBytes = scanRecord?.bytes

            rawBytes?.let { bytes ->
                // Convert byte array to hex string for readability
                val hexPayload = bytes.joinToString("") { "%02X".format(it) }

                // Optional: More human-readable interpretation
                val payloadString = bytes.map { it.toChar() }.joinToString("")

                Log.d(TAG, "Raw Hex Payload: $hexPayload")
                Log.d(TAG, "Payload as String: $payloadString")
            }

            // Existing result update logic remains the same
            val updatedResults = scanResults.value.toMutableList()
            val existingDeviceIndex = updatedResults.indexOfFirst { it.device.address == result.device.address }

            if (existingDeviceIndex != -1) {
                updatedResults[existingDeviceIndex] = result
            } else {
                updatedResults.add(result)
            }

            scanResults.value = updatedResults
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            Log.d(TAG, "Batch scan results: ${results.size} devices")
            // Similar logic to onScanResult, but for batch results
            val updatedResults = scanResults.value.toMutableList()
            results.forEach { result ->
                val existingDeviceIndex = updatedResults.indexOfFirst { it.device.address == result.device.address }

                if (existingDeviceIndex != -1) {
                    updatedResults[existingDeviceIndex] = result
                } else {
                    updatedResults.add(result)
                }
            }

            scanResults.value = updatedResults
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e(TAG, "Scan failed with error code: $errorCode")
        }
    }

    // Stop scanning when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        try {
            bluetoothLeScanner.stopScan(scanCallback)
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping scan", e)
        }
    }
}