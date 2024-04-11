# Sensor Foreground Service

This Android project demonstrates how to create a foreground service that listens to sensor data and saves it to a CSV file. The service runs with high priority and a persistent notification to ensure it continues running even when the app is in the background or the screen is off.

## Features

- Listens to accelerometer sensor data.
- Saves sensor data to a CSV file.
- Runs as a foreground service to ensure continuous operation.

## Prerequisites

- Android Studio installed on your development machine.
- An Android device or emulator running Android 8.0 (API level 26) or higher for foreground service compatibility.

## Getting Started

1. Clone this repository to your local machine:

    ```
    git clone https://github.com/b-elamine/RunningDataCollection.git
    ```

2. Open the project in Android Studio.

3. Build and run the project on your Android device or emulator.

## Usage

- When click on start button and the applicaion will automatically start the sensor foreground service.
- The service will continuously listen to accelerometer sensor data and save it to a CSV file named "sensor_data.csv" in the app's external files directory.
- You can modify the service behavior or add additional sensor listeners as needed in the `SensorForegroundService.java` file.

# App Permissions

The app requires the following permissions to function properly:

- **WAKE_LOCK:** Allows the app to acquire wake locks to ensure the service continues running even when the screen is off.

- **WRITE_EXTERNAL_STORAGE:** Allows the app to write sensor data to external storage.

- **BODY_SENSORS:** Allows the app to access accelerometer sensor data.

## Why these permissions are required:

- **WAKE_LOCK:** The app needs to acquire wake locks to ensure continuous operation of its background service, even when the device screen is turned off. This ensures uninterrupted packet capture functionality.

- **WRITE_EXTERNAL_STORAGE:** The app needs permission to write sensor data to the external storage of the device. This is necessary for storing captured packet data for later analysis or logging.

- **BODY_SENSORS:** This permission allows the app to access accelerometer sensor data, which may be used for certain packet capture functionalities or analysis purposes.

By granting these permissions, you enable the app to provide its intended functionality effectively. Rest assured that the app respects your privacy and does not misuse the data collected.

