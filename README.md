# Student Management System

A desktop application built with **Java Swing** and **MySQL** to manage student records through a simple graphical interface.

## Features

- Add, update, and delete student records
- View student records in a table
- Search students by name
- Filter students by course
- Display total students and unique courses
- Export visible student records to CSV
- Validate form inputs and email addresses
- Handle duplicate email addresses with friendly error messages
- Store database credentials in an environment variable

## Tech Stack

- **Language:** Java
- **GUI:** Java Swing
- **Database:** MySQL
- **Database connectivity:** JDBC
- **Version control:** Git

## Project Structure

```text
StudentManagementSystem/
├── Main.java
├── README.md
├── .gitignore
├── lib/
│   └── mysql-connector-j-26.10.0.jar
└── src/
    ├── dao/
    │   └── StudentDAO.java
    ├── model/
    │   └── Student.java
    ├── ui/
    │   └── StudentManagementGUI.java
    └── util/
        └── DBConnection.java
```

## Requirements

- Java JDK
- MySQL Server
- MySQL Connector/J

## Database Setup

Create the database and table in MySQL:

```sql
CREATE DATABASE student_db;
USE student_db;

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE
);
```

If the database and table already exist, do not recreate them.

## Configuration

Set the database password through the `STUDENT_DB_PASSWORD` environment variable. Do not hardcode credentials in source code or commit secrets to GitHub.

For the default macOS Zsh shell, add the following line to `~/.zshrc`, replacing the placeholder locally with your actual password:

```bash
export STUDENT_DB_PASSWORD='your_actual_password'
```

Reload your shell configuration:

```bash
source ~/.zshrc
```

Never share your actual password in this README or upload it to GitHub.

## Compile and Run

From the project root:

```bash
javac -cp ".:lib/mysql-connector-j-26.10.0.jar" -d . Main.java src/model/Student.java src/util/DBConnection.java src/dao/StudentDAO.java src/ui/StudentManagementGUI.java
```

Launch the GUI:

```bash
java -cp ".:lib/mysql-connector-j-26.10.0.jar" ui.StudentManagementGUI
```

## Future Improvements

- Add unit tests
- Improve exception handling and logging
- Add pagination for larger datasets
- Add role-based authentication
- Improve automated testing of database operations

## Author

Developed as a Java and MySQL learning project.
