package com.sss.myvideoplayer

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener, SurfaceHolder.Callback,
    MediaPlayer.OnPreparedListener {

    private var videolist: ArrayList<Int> = ArrayList()
    private var mediaController: MediaController? = null
    private var isVideoPaused: Boolean = false
    private var stopPosition: Int? = null

    private lateinit var surfaceView: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        surfaceView = findViewById(R.id.surface_view)
        surfaceHolder = surfaceView.holder

        surfaceView.keepScreenOn = true


        mediaController = MediaController(this)
    //    mediaController!!.setMediaPlayer(surfaceView)

        fast_forward_button.setOnClickListener(this)
        fast_back_button.setOnClickListener(this)
        play_button.setOnClickListener(this)
        pause_button.setOnClickListener(this)
        video_layout.setOnClickListener(this)
        screen_mode_button.setOnClickListener(this)

        videolist.add(R.raw.video1)
        videolist.add(R.raw.video1)

        surfaceHolder.addCallback(this)

    }

    private fun initializeVideoPlayer() {
      //  surfaceView.setVideoURI(Uri.parse(setVideo(videolist[0])))
    }

    private fun releaseVideoPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.release()
        }
      //  surfaceView.stopPlayback()
    }


    override fun onPause() {
        super.onPause()
        releaseVideoPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseVideoPlayer()
    }


    private fun setVideo(videoId: Int): String {
        var uriPath = "android.resource://" + getPackageName() + "/" + videoId.toString()

        return uriPath
    }

    override fun onStart() {
        super.onStart()

        initializeVideoPlayer()
     //   surfaceView.start()
    }

    override fun onStop() {
        super.onStop()

        releaseVideoPlayer()
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.video_layout -> {

                if (isVideoPaused) {

                    fast_back_button.visibility = View.VISIBLE
                    fast_forward_button.visibility = View.VISIBLE
                    play_button.visibility = View.VISIBLE
                    pause_button.visibility = View.GONE

                } else {
                    iniateAllButton()
                }

                hideAllButton()

            }

            R.id.fast_back_button -> {

              //  surfaceView.seekTo(surfaceView.currentPosition - 10000)

                iniateAllButton()

                hideAllButton()


            }
            R.id.fast_forward_button -> {

            //    surfaceView.seekTo(surfaceView.currentPosition + 10000)

                iniateAllButton()

                hideAllButton()

            }
            R.id.play_button -> {
             //   surfaceView.resume()

           //     stopPosition?.let { surfaceView.seekTo(it) }

              //  surfaceView.start()

                play_button.visibility = View.GONE
                pause_button.visibility = View.VISIBLE

                isVideoPaused = false
            }
            R.id.pause_button -> {
            //    surfaceView.pause()

             //   stopPosition = surfaceView.currentPosition

                isVideoPaused = true

            }

            R.id.screen_mode_button -> {
                Toast.makeText(this, "orientation", Toast.LENGTH_SHORT).show()
                val orientation = resources.configuration.orientation
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
                } else {
                    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
                }

            }
        }
    }

    private fun hideAllButton() {
        Handler().postDelayed({
            fast_back_button.visibility = View.GONE
            fast_forward_button.visibility = View.GONE
            play_button.visibility = View.GONE
            pause_button.visibility = View.GONE
        }, 2600)
    }

    private fun iniateAllButton() {

        fast_back_button.visibility = View.VISIBLE
        fast_forward_button.visibility = View.VISIBLE
        play_button.visibility = View.GONE
        pause_button.visibility = View.VISIBLE
    }


    /*override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
*/
    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDisplay(holder)
        try {
            mediaPlayer.setDataSource("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4")
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener(this)
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }catch (e:IOException){
            e.printStackTrace()
        }

    }

    override fun onPrepared(p0: MediaPlayer?) {
       mediaPlayer.start()
    }


}
