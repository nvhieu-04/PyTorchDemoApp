# Plant Disease Identifier

"Plant Disease Identifier" is a mobile application on the Android platform, allowing users to detect and identify diseases on plants through images taken from the phone's camera. The project uses Java for the front-end of the application and JavaScript for the back-end, using NodeJS and ExpressJS to build the API and MongoDB to store data.

## Features

- Allows users to take pictures of plants to detect and identify diseases on them
- Displays the results of disease detection on the image, including the name of the disease and how to handle it
- Allows users to view the history of pictures taken and their detection results

## Technologies Used

- Front-end: Android Java
- Back-end: JavaScript using NodeJS and ExpressJS
- Storage system: MongoDB
- Uses PyTorch and machine learning models for disease detection and identification on plants

## Project Structure

- `android`: source code for the front-end of the application, written in Java and developed using Android Studio
- `backend`: source code for the back-end of the application, written in JavaScript and using NodeJS and ExpressJS to build the API and connect to MongoDB
- `models`: contains the machine learning models used for disease detection and identification on plants
- `docs`: contains the documentation on how to use the application and the steps to install and run the project

## How to Install and Run the Project

To install and run the project, you need to follow these steps:

1. Clone the project from Github to your computer
2. Install and configure MongoDB on your computer
3. Open Terminal and navigate to the `backend` directory of the project
4. Run the command `npm install` to install the necessary modules for the back-end of the application
5. Run the command `npm start` to start the server
6. Open Android Studio and open the `android` directory of the project
7. Edit the `Constants.java` file and modify the API URL to point to the server started in the previous step
8. Connect your Android phone to the computer and run the application on the phone to use it
