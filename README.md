## Project Structure

| **Module/Directory**  | **Description**                                |
|-----------------------|------------------------------------------------|
| `LLoydSampleProject/` | Root directory of the project.                 |
| `app/`                | Main application module.                       |
| `data/`               | Data layer for the project.                    |
| ├── `api/`            | Contains Retrofit/OkHttp API definitions.      |
| ├── `repository/`     | Repository implementations for data sources.   |
| └── `models/`         | Data models representing the API and database. |
| `domain/`             | Domain layer encapsulating business logic.     |
| ├── `usecases/`       | Contains use case implementations.             |
| └── `repository/`     | Interfaces for repository abstractions.        |
| `presentation/`       | UI module housing all Jetpack Compose screens. |
| ├── `viewmodel/`      | Contains ViewModels for UI logic.              |
| └── `ui/`             | Composable functions for the UI layer.         |

Dividing the project into 3 modules:

**app/presentation**: Entry point, composes the modules,UI screens using Jetpack Compose..
**data**: Data sources, repositories, API clients.
**domain**: Use cases and repository abstractions.

A Clean Architecture App to show use of multi-module-architecture in a Jetpack Compose.

The modules are as follow:

* app: Presentation Layer

* domain: Business Logic Layer

* data: Data Access Layer

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
			<img src="assets/no_bookmarks.jpeg" alt="No Bookmarks" width="300"/>
    	</td>
        <td align="center">
			<img src="assets/details_bookmark.jpeg" alt="Show Details" width="300"/>
    	</td>
        <td align="center">
			<img src="assets/bookmarks.jpeg" alt="Bookmarks" width="300"/>
    	</td>
  	</tr>
</table>

## Video

<table style="padding:10px">
	<tr>
    	<td align="center">
			<img src="assets/tvshow_app.gif" alt="Tv Shows" width="300"/>
    	</td>
  	</tr>
</table>

## Tech Stack
* [Multi-Module-Architecture](https://developer.android.com/topic/modularization) - Guide to Android app modularization.
* [Kotlin](https://kotlinlang.org/) - Primary programming language for Android development.
* [Jetpack Compose](https://developer.android.com/compose) -  Modern toolkit for building native UI.
* [Material Design](https://developer.android.com/develop/ui/views/theming/look-and-feel) - Implements visual, motion, and interaction design principles.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency injection framework for Android.
* [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - For asynchronous or non-blocking programming.
* [Flows](https://developer.android.com/kotlin/flow) - Reactive streams for handling asynchronous data
* [Navigation-Jetpack](https://developer.android.com/jetpack/androidx/releases/navigation) - Simplifies app navigation between destinations.
* [AndroidX](https://developer.android.com/jetpack/androidx) - Libraries for backward-compatible Android components.
* [Retrofit](https://square.github.io/retrofit/) -  Type-safe HTTP client for API calls.
* [Solid Principles](https://medium.com/the-android-caf%C3%A9/solid-principles-the-kotlin-way-ff717c0d60da) - Ensures scalable and maintainable code.


