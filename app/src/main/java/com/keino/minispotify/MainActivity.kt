package com.keino.minispotify
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.keino.minispotify.ui.MusicViewModel
import com.keino.minispotify.ui.MusicApp
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint class MainActivity : ComponentActivity() {
    private val model: MusicViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { MusicApp(model) } }
    override fun onStop() { model.pause(); super.onStop() }
}
