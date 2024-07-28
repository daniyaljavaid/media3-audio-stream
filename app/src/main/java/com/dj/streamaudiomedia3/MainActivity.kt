package com.dj.streamaudiomedia3

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.dj.streamaudiomedia3.ui.theme.StreamAudioMedia3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StreamAudioMedia3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AudioPlayer(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AudioPlayer(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val player = ExoPlayer.Builder(context).build()
    val url =
        "https://file-examples.com/storage/fe3f15b9da66a36baa1b51a/2017/11/file_example_MP3_700KB.mp3"
    val mediaItem =
        MediaItem.fromUri(url)
    player.setMediaItem(mediaItem)

    player.playWhenReady = true

    player.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            Log.e("DJ_TEST", "$playbackState")
            when (playbackState) {
                Player.STATE_IDLE -> {
                    Toast.makeText(context, "Initial State", Toast.LENGTH_SHORT).show()
                }

                Player.STATE_BUFFERING -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }

                Player.STATE_READY -> {
                    Toast.makeText(context, "Ready", Toast.LENGTH_SHORT).show()
                }

                Player.STATE_ENDED -> {
                    Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show()
                }

            }
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            Log.e("DJ_TEST", error.message ?: "")
        }
    })

    player.prepare()
}