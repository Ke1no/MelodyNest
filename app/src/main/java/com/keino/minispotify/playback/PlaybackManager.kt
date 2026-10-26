package com.keino.minispotify.playback
import android.content.Context
import android.net.Uri
import androidx.media3.common.*
import androidx.media3.exoplayer.ExoPlayer
import com.keino.minispotify.R
import com.keino.minispotify.data.Track
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class PlaybackState(val title: String = "Choose a demo track", val playing: Boolean = false, val error: String? = null)
@ViewModelScoped class PlaybackManager @Inject constructor(@ApplicationContext context: Context) {
    private val state = MutableStateFlow(PlaybackState())
    val status = state.asStateFlow()
    private val demoUri = Uri.parse("android.resource://${context.packageName}/${R.raw.demo_tone}")
    private val player = ExoPlayer.Builder(context).build().apply {
        setAudioAttributes(AudioAttributes.Builder().setUsage(C.USAGE_MEDIA).setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).build(), true)
        setHandleAudioBecomingNoisy(true)
        addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) { state.value = state.value.copy(playing = isPlaying) }
            override fun onPlayerError(error: PlaybackException) { state.value = state.value.copy(error = "Playback failed: ${error.errorCodeName}", playing = false) }
        })
    }
    fun play(track: Track) {
        val uri = track.audioUrl?.takeIf { it.isNotBlank() }?.let(Uri::parse) ?: demoUri
        if (uri != demoUri && uri.scheme != "https") { state.value = PlaybackState(track.title, false, "Remote audio must use HTTPS"); return }
        state.value = PlaybackState(track.title + if (uri == demoUri) " · demo tone" else "")
        player.setMediaItem(MediaItem.fromUri(uri)); player.prepare(); player.play()
    }
    fun toggle() { if (player.isPlaying) player.pause() else if (player.mediaItemCount > 0) { if (player.playbackState == Player.STATE_ENDED) player.seekTo(0); player.play() } }
    fun pause() { player.pause() }
    fun release() { player.release() }
}
