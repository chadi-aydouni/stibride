# StibRide - Java/JavaFX

Application developed in Java/JavaFX as part of the ATLG4 course at HE2B ESI (2020-2021).

## Overview

StibRide is an application that allows you to search for **the shortest path** between two STIB metro stations.
It is based on an SQLite database containing lines, stations, and stopsof the network, and implements the **Dijkstra algorithm** for route calculation.

The graphical interface, built with JavaFX, provides the user with:
- The ability to select origin and destination stations via drop-down lists (with search functionality using `SearchableComboBox` from ControlsFX).
- A table display of the calculated path.
- A visualization of the metro network map.
- The ability to save, edit, and delete favorite routes.

## Prerequisites
* Java JDK 11 or later
* JavaFX SDK corresponding to your JDK version (downloadable from [https://openjfx.io](https://openjfx.io))
* Maven 3.x
* External dependency: ControlsFX (used for `SearchableComboBox`)

`pom.xml` excerpt:

``` xml
<dependency>
  <groupId>org.controlsfx</groupId>
  <artifactId>controlsfx</artifactId>
  <version>11.0.3</version>
</dependency>
```

## Installation

### Compile and run the project

Ensure Maven is installed and available in your system PATH. Then, open a terminal in the project root directory and run:

``` bash
mvn clean javafx:run
```

This command will clean the project, download dependencies, and launch the JavaFX application.

### Database

The application uses a SQLite database with three main tables:
- `Lines`: metro line numbers
- `Stations`: list of stations and their names
- `Stops`: stops of each line with their order

A database creation script is provided (see `data/sqlite` folder).
The application automatically connects to this database via JDBC.

## Usage

Once the application is launched, you can:

-   **Search for a path**:
    -   Choose an origin and a destination station.
    -   Launch the search with the provided button.
    -   View the calculated path in a table and on the metro map.
  
-   **Manage your favorite routes**:
    -   Save a route (name, origin, destination).
    -   Quickly retrieve your saved routes.
    -   Edit or delete a favorite route.

## Disclaimer
This repository was created as part of coursework. Some files (such as starter code, assignments, resources) were provided by the instructor or belong to third parties. All rights to these materials remain with their original authors and/or copyright holders.

My own contributions in this repository are shared under the MIT License. Files provided by instructors, external authors, or third parties are **not** covered by this license.

### Instructor-provided materials

This repository specifically includes the following materials provided by the instructor:

- **stib.db.sql** – Database creation and population script
- **ConfigManager.java** – Singleton giving access to all properties of the `model.config.properties` file
- **DBManager.java** – Helper class managing database connections
- **logo.png** – STIB logo (© STIB-MIVB). All rights reserved
- **metro.gif** – STIB metro map (© STIB-MIVB). All rights reserved