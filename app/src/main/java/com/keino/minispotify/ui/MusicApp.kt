package com.keino.minispotify.ui
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.keino.minispotify.data.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun MusicApp(model: MusicViewModel) {
    val state by model.state.collectAsStateWithLifecycle()
    val favorites by model.favorites.collectAsStateWithLifecycle()
    val playback by model.playback.collectAsStateWithLifecycle()
    val nav = rememberNavController()
    val entry by nav.currentBackStackEntryAsState()
    val route = entry?.destination?.route
    MaterialTheme(colorScheme = darkColorScheme(primary = Color(0xFF79D39C), background = Color(0xFF101914), surface = Color(0xFF18231D))) {
        Scaffold(topBar = { TopAppBar(title = { Text("MelodyNest") }) }, bottomBar = {
            Column {
                Surface(tonalElevation = 4.dp) { Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(Modifier.weight(1f)) { Text(playback.title); playback.error?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
                    TextButton(onClick = model::toggle) { Text(if (playback.playing) "Pause" else "Play") }
                } }
                NavigationBar { listOf("feed" to "Feed", "albums" to "Albums", "favorites" to "Favorites").forEach { (path,label) ->
                    NavigationBarItem(selected = route == path || path == "albums" && route == "album/{id}", onClick = { nav.navigate(path) { popUpTo("feed") { saveState = true }; launchSingleTop = true; restoreState = true } }, icon = { Text(label.take(1)) }, label = { Text(label) })
                } }
            }
        }) { padding ->
            Column(Modifier.padding(padding).fillMaxSize().padding(horizontal = 18.dp)) {
                Text(state.source + " · original generated demo audio", style = MaterialTheme.typography.labelMedium)
                TextButton(onClick = model::refresh, enabled = !state.loading) { Text(if (state.loading) "Refreshing…" else "Refresh mock API") }
                state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                NavHost(navController = nav, startDestination = "feed", modifier = Modifier.weight(1f)) {
                    composable("feed") { TrackList("Made for your break", state.tracks, favorites.map { it.id }.toSet(), model::play, model::favorite) }
                    composable("favorites") { TrackList("Your favorites", favorites, favorites.map { it.id }.toSet(), model::play, model::favorite) }
                    composable("albums") { LazyColumn { items(state.albums, key = { it.id }) { album ->
                        Card(Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { nav.navigate("album/${Uri.encode(album.id)}") }) { Column(Modifier.padding(22.dp)) { Text(album.title, style = MaterialTheme.typography.titleLarge); Text(album.artist) } }
                    } } }
                    composable("album/{id}", arguments = listOf(navArgument("id") { type = NavType.StringType })) { back ->
                        val id = back.arguments?.getString("id")
                        TrackList(state.albums.find { it.id == id }?.title ?: "Album", state.tracks.filter { it.albumId == id }, favorites.map { it.id }.toSet(), model::play, model::favorite)
                    }
                }
            }
        }
    }
}

@Composable private fun TrackList(title: String, tracks: List<Track>, saved: Set<String>, play: (Track) -> Unit, favorite: (Track, Boolean) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(vertical = 16.dp)) {
        item { Text(title, style = MaterialTheme.typography.headlineMedium) }
        if (tracks.isEmpty()) item { Text("No tracks here yet. Add a favorite from Feed.") }
        items(tracks, key = { it.id }) { track -> Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(18.dp)) {
                Text(track.title, style = MaterialTheme.typography.titleMedium); Text(track.artist, style = MaterialTheme.typography.bodySmall)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    TextButton(onClick = { play(track) }) { Text("Play") }
                    TextButton(onClick = { favorite(track, track.id !in saved) }) { Text(if (track.id in saved) "Unfavorite" else "Favorite") }
                }
            }
        } }
    }
}
