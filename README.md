# Muddy Wheels: Final Project for CSC 325 Database Management

This project is the culmination of what we've learned in our CSC 325 Database Management class. The aim is to apply the concepts and skills developed throughout the course to build a functioning application.

**Muddy Wheels** is a simple travel application that allows users to book a flight or rent a car. The project is built using **Java**, and the program is text-based.

**DAO (Data Access Object) Pattern**:  
This project uses the **DAO pattern** to interact with the database. DAO files separate the database logic from the main application code, making the program cleaner and more modular. DAO abstracts the database interactions, so instead of writing SQL queries throughout the application, we use methods in the DAO layer to perform operations like retrieving bookings or saving user information.

**Amazon RDS (Relational Database Service)**:  
We use **Amazon RDS**, which is a cloud-based database service provided by Amazon Web Services (AWS). This allows our program to connect to a database hosted in the cloud. RDS manages tasks like backups, scaling, and patching, freeing us from having to manage the database infrastructure ourselves.

**IDE**:  
The IDE we used was **Codio Virtual Machine**, configured by professor Bob Dugan. While the program works within Codio, it's not guaranteed to run in other IDEs without modifications.

## How to Run:
To run the main program, use the following command in the terminal:

```
sh compilerun.sh
```

## Future Plans:
Due to time constraints, we weren’t able to create a graphical user interface (GUI), so the current version is text-based. Future improvements would include developing a GUI and refactoring the code to pull as much data from the database as possible, reducing hardcoded options.

## Authors: 
- Alvin George
- Sean Sinclair
- Ula Nguyen

**Spring 2024**
