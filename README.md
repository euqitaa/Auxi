# Auxi

Auxi is a JavaFX productivity assistant application built in Java 21. The project uses a modular Java application structure with `javafx-controls`, `javafx-fxml`, and `FormsFX` for UI and form handling.

## Overview

The application appears to provide an integrated personal assistant experience with features such as:
- User registration and login
- Profile management
- Notes and note loading
- News feed display
- Chat and chat app functionality
- Pomodoro timer
- Flashcards and deck management
- Calendar and scheduling
- Activity tracking
- Server/chat networking support

## Project Structure

- `pom.xml` - Maven project configuration and dependencies
- `src/main/java` - Java source files
- `src/main/resources` - resources including FXML files and assets
- `src/main/java/module-info.java` - Java module declarations
- `mysql-connector-j-8.3.0/` - included MySQL connector source tree (external component)

## Requirements

- Java 21
- Maven
- JavaFX 21 runtime
- Windows, macOS, or Linux with a supported JDK and display environment for JavaFX

## Build and Run

Use Maven to build and run the application:

```bash
mvn clean javafx:run
```

If you want to build a normal Maven package first:

```bash
mvn clean package
```

## Main entry point

The main JavaFX application class is `com.example.auxi.HelloApplication`.

## Notes

- The project uses a modular setup with `module com.example.auxi` defined in `src/main/java/module-info.java`.
- Application launch is configured through the `javafx-maven-plugin` in `pom.xml`.
- Database connectivity is supported through `com.example.auxi.db.dbConnection` and requires a compatible SQL backend if enabled.

## Contribution

This README is intended to document the project structure and usage. If you want to extend the project, start by exploring the controller classes under `src/main/java/com/example/auxi/controller` and the FXML layouts in `src/main/resources`.
