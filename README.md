# AroundTheCorner

## Overview

**AroundTheCorner** is an Android application designed for seamless interaction with Bluetooth Low Energy (BLE) devices. The app enables users to scan, discover, and interact with nearby BLE devices, offering a user-friendly interface to explore advertisements and device details. It also supports user personalization and private-mode toggles for controlled access to features.

---

## Features

### BLE Scanning
- Discovers nearby BLE devices in real-time.
- Displays device details:
  - Device Name
  - Address
  - RSSI (Signal Strength)
- Processes BLE advertisements and extracts detailed payload information.

### Modern User Interface
- Built using **Jetpack Compose**, providing a responsive and intuitive design.
- Includes the following screens:
  - **Home**: Lists discovered BLE devices.
  - **Advertisement Details**: Displays information about selected BLE devices.
  - **Settings**: Allows toggling privacy modes and managing preferences.
  - **About**: Information about the app and its functionalities.

### User Customization
- Stores user details (e.g., name, age, gender, and region) via `SharedPreferences`.
- Features a "Private Mode" for users to disable public interactions with BLE advertisements.

### Permissions Management
- Handles required permissions dynamically, including:
  - Bluetooth scanning and connectivity.
  - Location services for BLE functionality.
- Provides fallback for scenarios where permissions are denied.

### BLE Payload Decoding
- Extracts and decodes raw BLE advertisement data into a human-readable format for debugging and analysis.

---

## How It Works

1. **Permission Handling**:
   - When the app is launched, it checks for necessary permissions (e.g., Bluetooth scanning and location access).
   - If permissions are missing, it prompts the user to grant them dynamically.

2. **Initialize Bluetooth**:
   - The app initializes the **BluetoothLeScanner** and ensures Bluetooth is enabled on the device.
   - If Bluetooth is disabled, the app requests the user to enable it.

3. **Start Scanning**:
   - On the Home screen, the app begins scanning for BLE devices within range.
   - A filter is applied to limit results to specific devices if required (e.g., based on address or service UUID).

4. **Display Scan Results**:
   - The discovered devices are displayed in a list format with details such as:
     - Device Name
     - Address
     - Signal Strength (RSSI)
   - The user can tap a device to explore its details further.

5. **Decode Advertisement Data**:
   - For each scanned device, the app extracts the raw advertisement payload and decodes it into:
     - A hexadecimal string representation.
     - A human-readable format (if possible).

6. **Navigate Through Screens**:
   - **Home Screen**: Displays the list of discovered devices.
   - **Advertisement Screen**: Displays detailed information about a selected device.
   - **Settings Screen**: Allows the user to manage their preferences, including toggling Private Mode.
   - **About Screen**: Provides information about the app and its functionalities.

7. **User Modes**:
   - **Public Mode**: The app allows users to interact with BLE advertisements.
   - **Private Mode**: Restricts user interactions with advertisements while continuing to scan devices.

8. **Stop Scanning**:
   - When the app is closed or the scanning operation is manually stopped, the BLE scanner halts all operations to save resources.

---

## Getting Started

### Prerequisites
- Android device with BLE support.
- Android 6.0 (API level 23) or higher.

### Setup
1. Clone this repository to your local machine:
   ```bash
   git clone https://github.com/yourusername/aroundthecorner.git
