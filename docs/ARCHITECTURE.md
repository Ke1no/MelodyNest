# Architecture

Compose screens → MusicViewModel → MusicRepository → Retrofit MusicApi / Room FavoriteDao.
MusicViewModel → PlaybackManager → Media3 ExoPlayer. Hilt supplies repositories, database and API; the player is ViewModel-scoped and released when the ViewModel is cleared.

Navigation contains feed, albums, album detail and favorites. A mini-player remains below all screens. Playback pauses when the Activity stops; a MediaSessionService is a future extension for background playback.
The bundled catalog and generated audio are explicitly demo fixtures. Remote refresh is user-triggered and errors are shown; there is no silent network-to-demo fallback.
Favorites store complete track metadata in Room so saved items remain available across catalog refreshes and process restarts.
