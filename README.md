# Muddy Wheels: Final Project for CSC 325 Database Management

This project is the culmination of what we've learned in our CSC 325 Database Management class. The aim is to apply the concepts and skills developed throughout the course to build a functioning application.

**Muddy Wheels** is a simple travel application that allows users to book a flight or rent a car. The project is built using **Java**, and the program is text-based.

### DAO (Data Access Object) Pattern:  
This project uses the **DAO pattern** to interact with the database. DAO pattern separates the database interaction logic from the main application code, making the program cleaner and more **modular**. <br>
DAO abstracts the database interactions, so instead of embedding SQL queries directly into the main application code, we interact with the database through methods in the DAO classes. For example, instead of writing raw SQL queries in multiple places, we call methods like `createAccount()` or `getBookings()` from the DAO to handle these tasks. This separation makes it easier to manage the program as we can modify database-related code in one central location.

### Amazon RDS (Relational Database Service):  
We use **Amazon RDS**, which is a cloud-based database service provided by Amazon Web Services (AWS). This allows our program to connect to a database hosted in the cloud. RDS manages tasks like backups, scaling, and patching, freeing us from having to manage the database infrastructure ourselves.

### IDE:  
The IDE we used was **Codio Virtual Machine**, which was configured by Professor Bob Dugan. However, the program can no longer run as the environment and configuration were turned off after the course ended (the program can no longer establish a connection to the Amazon database).

## Replication / Next step
To continue with the project, we must recreate it:
1. **Find a New Database Host**: We would need to set up a new RDS instance or use an alternative database hosting solution to replace the previous one.
2. **Update Database Configuration**: After setting up the new host, we would update the necessary credentials and connection details in the program, as well as address any deprecated code parts.
3. **Test the Application**: Once the new database is connected, we will test the application to ensure everything functions as expected.

## Future Plans
Due to time constraints and a lack of knowledge in web development, we weren’t able to create a user interface. In the future, we can use **Flask** to create a web-based version of the application, providing an interactive interface for users to book flights and rent cars instead of text-based like what we currently have. Additionally, we would restructure the code to pull as much data as possible from the database, reducing hardcoded options and enhancing flexibility.

## Authors: 
- Alvin George
- Sean Sinclair
- Ula Nguyen

**Spring 2024**
