# MiniSpotify

Android music/favorites **scaffold / work in progress**, based on the supplied project overview.

Kotlin · Jetpack Compose · Navigation · MVVM · Hilt · Retrofit · Room · Media3 ExoPlayer

## Included
- Feed, album and favorites navigation with a shared ViewModel.
- Hilt dependency injection, Retrofit catalog interface and a json-server mock catalog.
- Room-backed favorites and lifecycle-aware state collection.
- App-wide foreground Media3 player with play/pause and error state.
- Three synthetic tracks and an original generated demo tone bundled with the app; no commercial music or Spotify API credentials.

The app starts with a clearly labeled bundled demo catalog. **Refresh mock API** fetches the local json-server catalog and surfaces errors. Playback uses the generated tone when `audioUrl` is null. Background playback, notifications, authentication, real music feeds and a polished full player are future work.

## Open in Android Studio
Open the repository root. Install SDK Platform 35 and Build Tools 35.0.0 using Android Studio, select a JDK 17+ compatible Gradle JVM, sync, and run `app` on an API 26+ emulator/device. The project pins AGP 8.9.2, Kotlin/Compose compiler 2.1.20 and Gradle 8.11.1.

```sh
./gradlew assembleDebug testDebugUnitTest
# Windows PowerShell: .\gradlew.bat assembleDebug testDebugUnitTest
```

## Optional mock server
```sh
cd mock-api
npm ci
npm start
```

The Android Emulator uses `http://10.0.2.2:3001/`. For a physical device, change the debug base URL to a reachable LAN host. Cleartext HTTP is enabled only in the debug manifest. The release URL is an explicit invalid placeholder until a real HTTPS API is configured.

## Status and next steps
See [中文开发清单](docs/NEXT_STEPS.zh-CN.md), [architecture](docs/ARCHITECTURE.md), and [validation](docs/VALIDATION.md). This scaffold has no Spotify integration, signing key, Play Store release or cloud deployment.
