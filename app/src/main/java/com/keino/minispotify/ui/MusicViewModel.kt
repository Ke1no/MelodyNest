package com.keino.minispotify.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keino.minispotify.data.*
import com.keino.minispotify.playback.PlaybackManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MusicState(val tracks: List<Track> = DemoCatalog.tracks, val albums: List<Album> = DemoCatalog.albums, val loading: Boolean = false, val source: String = "Bundled demo catalog", val error: String? = null)
@HiltViewModel class MusicViewModel @Inject constructor(private val repository: MusicRepository, private val player: PlaybackManager) : ViewModel() {
    private val mutable = MutableStateFlow(MusicState())
    val state = mutable.asStateFlow()
    val favorites = repository.favorites.map { rows -> rows.map { it.track() } }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val playback = player.status
    fun refresh() { if (mutable.value.loading) return; viewModelScope.launch {
        mutable.update { it.copy(loading = true, error = null) }
        try { val (tracks, albums) = repository.remoteCatalog(); mutable.value = MusicState(tracks, albums, source = "Local mock API") }
        catch (e: CancellationException) { throw e }
        catch (e: Exception) { mutable.update { it.copy(loading = false, error = "Mock API unavailable or invalid. Start json-server on port 3001.") } }
    } }
    fun favorite(track: Track, enabled: Boolean) { viewModelScope.launch {
        try { repository.setFavorite(track, enabled) }
        catch (e: CancellationException) { throw e }
        catch (e: Exception) { mutable.update { it.copy(error = "Could not update favorites.") } }
    } }
    fun play(track: Track) = player.play(track)
    fun toggle() = player.toggle()
    fun pause() = player.pause()
    override fun onCleared() { player.release(); super.onCleared() }
}
