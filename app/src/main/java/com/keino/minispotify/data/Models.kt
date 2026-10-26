package com.keino.minispotify.data
data class Track(val id: String, val albumId: String, val title: String, val artist: String, val durationSeconds: Int, val audioUrl: String? = null)
data class Album(val id: String, val title: String, val artist: String)
object DemoCatalog {
    val tracks = listOf(
        Track("demo-1", "focus", "Morning Notes", "Generated demo audio", 4),
        Track("demo-2", "focus", "Quiet Study", "Generated demo audio", 4),
        Track("demo-3", "break", "A Small Break", "Generated demo audio", 4)
    )
    val albums = listOf(Album("focus", "Focus Session", "Demo collection"), Album("break", "Take a Break", "Demo collection"))
}
fun validCatalog(tracks: List<Track>): Boolean = tracks.isNotEmpty() && tracks.map { it.id }.distinct().size == tracks.size && tracks.all { it.id.isNotBlank() && it.title.isNotBlank() && it.albumId.isNotBlank() && it.durationSeconds >= 0 }
