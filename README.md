## Android App

This is an Android application that allows users to input their name, surname, and grades, and calculates their average grade. 
Depending on the average grade, the application displays a different message and behavior.

## Features

- Input name, surname, and grades
- Calculate average grade
- Display different messages based on the average grade
- Close the application automatically after displaying the message

## Technologies Used

- Android Studio
- Java
- XML

## Getting Started

To get started with the application, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the application on an Android emulator or physical device.

## Usage

1. Launch the application on an Android emulator or physical device.
2. Enter your name, surname, and grades in the corresponding input fields.
3. The "Grades" button will become visible only when all input fields are filled correctly.
4. Tap the "Grades" button to open the "Oceny" activity.
5. The "Oceny" activity will display the calculated average grade based on the entered grades.
6. Depending on the average grade, a different message will be shown, and the behavior of the "Grades" button will change:
- If the average grade is 3.0 or higher, the button text will change to "SUPER!". Tapping the button will display a positive message and automatically close the application after 3 seconds.
- If the average grade is below 3.0, the button text will change to "Tym razem nie wyszło :(". Tapping the button will display a negative message and automatically close the application after 3 seconds.
