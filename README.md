## Project Structure

| **Module/Directory**   | **Description**                                      |
|-------------------------|------------------------------------------------------|
| `LLoydSampleProject/`   | Root directory of the project.                       |
| `app/`                  | Main application module.                             |
| `core/`                 | Shared utilities and common functionality.           |
| `data/`                 | Data layer for the project.                          |
| ├── `api/`              | Contains Retrofit/OkHttp API definitions.            |
| ├── `repository/`       | Repository implementations for data sources.         |
| └── `models/`           | Data models representing the API and database.       |
| `domain/`               | Domain layer encapsulating business logic.           |
| ├── `usecases/`         | Contains use case implementations.                   |
| └── `repository/`       | Interfaces for repository abstractions.              |
| `presentation/`         | UI module housing all Jetpack Compose screens.       |
| ├── `viewmodel/`        | Contains ViewModels for UI logic.                    |
| └── `ui/`               | Composable functions for the UI layer.               |


Dividing the project into 4 modules:

**app**: Entry point, composes the modules.
**core**: Common utilities (e.g., encryption, network utils).
**data**: Data sources, repositories, API clients.
**domain**: Use cases and repository abstractions.
**presentation**: UI screens using Jetpack Compose.

A Clean Architecture App to show use of multi-module-architecture in a Jetpack Compose.

The modules are as follow:

* app: Presentation Layer

* domain: Business Logic Layer

* data: Data Access Layer

## --- Requirement ---

You should add api key to Network Module before build app.
https://developer.themoviedb.org/reference/intro/authentication

## Screenshots

<table style="padding:10px">
	<tr>
    	<td align="center">
			<img src="assets/home.jpeg" alt="Tv Shows" width="300"/>
    	</td>
		<td align="center">
			<img src="assets/details.jpeg" alt="Show Details" width="300"/>
    	</td>
        <td align="center">
			<img src="assets/details_bookmark.jpeg" alt="Show Details" width="300"/>
    	</td>
        <td align="center">
			<img src="assets/bookmarks.jpeg" alt="Bookmarks" width="300"/>
    	</td>
  	</tr>
</table>

## Tech Stack

* Multi-Module-Architecture
* Kotlin
* Jetpack Compose
* Material Design
* Hilt
* Coroutines
* Flow
* Paging
* Navigation
* AndroidX
* Retrofit
* Coil
* Solid Principles


