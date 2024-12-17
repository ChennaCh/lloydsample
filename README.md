LLoydSampleProject/
├── app/                  // Main app module
├── core/                 // Shared utilities
├── data/                 // Data layer
│   ├── api/              // Retrofit/OkHttp APIs
│   ├── repository/       // Repository implementations
│   └── models/           // Data models
├── domain/               // Domain layer
│   ├── usecases/         // Business logic
│   └── repository/       // Repository interfaces
└── presentation/         // UI Module (Compose screens)
├── viewmodel/        // ViewModels
└── ui/               // Composables


Dividing the project into 4 modules:

**app**: Entry point, composes the modules.
**core**: Common utilities (e.g., encryption, network utils).
**data**: Data sources, repositories, API clients.
**domain**: Use cases and repository abstractions.
**presentation**: UI screens using Jetpack Compose.
