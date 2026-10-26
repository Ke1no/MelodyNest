# Validation

Verified on 2026-10-26:
- GitHub Actions on Ubuntu / JDK 17 completed `./gradlew assembleDebug testDebugUnitTest` successfully.
- Debug APK uploaded as the `minispotify-debug-apk` workflow artifact.
- Local mock API returned three tracks and two albums with valid references.
- Generated demo WAV is four seconds of mono PCM audio.

[Successful code-build run](https://github.com/Ke1no/mini-spotify/actions/runs/40387046092) — code commit `bcd954532cfedacdb67adfa486072428d232f751`.

These are build, JVM unit and API checks. No Android emulator or physical-device UI, audio-focus or persistence instrumentation test was run. Test navigation, favorites after relaunch, playback, pause on backgrounding and mock-server connectivity in Android Studio before treating this as a finished app.
