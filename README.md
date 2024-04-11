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

- **BODY_SENSORS_BACKGROUND:** Allows the app to access sensor data in the background, even when the app is not in the foreground.

- **BODY_SENSORS_BACKGROUND_LOCATION:** Allows the app to access sensor data in the background, including the device's location.

- **FOREGROUND_SERVICE:** Allows the app to run foreground services, which are services that the user is actively aware of and cannot be killed by the system.

- **FOREGROUND_SERVICE_LOCATION:** Allows the app to access the device's location while running foreground services.

