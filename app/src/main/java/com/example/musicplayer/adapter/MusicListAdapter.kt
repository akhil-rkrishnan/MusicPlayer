package com.example.musicplayer.adapter

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Constants
import com.example.musicplayer.R
import com.example.musicplayer.data.MusicStore

class MusicListAdapter(context: Context) : RecyclerView.Adapter<MusicListAdapter.ViewHolder>() {
    val mContext = context
    lateinit var musicDataList: ArrayList<MusicStore>
    var currentPlayIndex = -1;
    var mMediaPlayer = MediaPlayer()
    var lastView = LinearLayout(mContext)
    var mMediaHandler = Handler()
    lateinit var mMediaRunnable: Runnable
    var constants = Constants()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    fun setMusicList(musicDataList: ArrayList<MusicStore>) {
        this.musicDataList = musicDataList;
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val musicName: TextView
        val playPause: ImageView
        val seekNext: ImageView
        val seekPrevious: ImageView
        val parentLayout: FrameLayout
        val playBackContainer: LinearLayout
        val playSeekBar: SeekBar

        init {
            // Define click listener for the ViewHolder's View.
            musicName = view.findViewById(R.id.music_name)
            playPause = view.findViewById(R.id.play_back_pause)
            seekNext = view.findViewById(R.id.skip_next)
            seekPrevious = view.findViewById(R.id.skip_previous)
            parentLayout = view.findViewById(R.id.parent_layout)
            playBackContainer = view.findViewById(R.id.playback_control_layout)
            playSeekBar = view.findViewById(R.id.play_seekbar)
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.music_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val musicStoreItem = musicDataList.get(position)
        viewHolder.musicName.text = musicStoreItem.getSongName()
//        var mediaPlayer: MediaPlayer? = MediaPlayer.create(mContext, uri)

        viewHolder.parentLayout.setOnClickListener { view ->
            if (currentPlayIndex == position) {
                return@setOnClickListener
            }

            if (mMediaPlayer?.isPlaying) {
                mMediaPlayer.stop()
            }

            if (lastView != null) {
                lastView.visibility = View.GONE
            }

            lastView = viewHolder.playBackContainer
            lastView.visibility = View.VISIBLE
            mMediaPlayer = MediaPlayer().apply {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                }
                setDataSource(musicStoreItem.getMusicPath())
                prepare()
                start()
            }
            viewHolder.playSeekBar.max = mMediaPlayer.duration / 1000
            makePlayBlackHandler(viewHolder.playSeekBar)
            currentPlayIndex = position;
            mMediaPlayer.setOnCompletionListener {
                viewHolder.playSeekBar.setProgress(0)
            }
        }
        viewHolder.playPause.setOnClickListener { view ->
            if (mMediaPlayer?.isPlaying) {
                mMediaPlayer.pause()
                viewHolder.playPause.setImageResource(R.drawable.play)
            } else {
                mMediaPlayer.start()
                viewHolder.playPause.setImageResource(R.drawable.pause)
            }
        }

    }

    public fun makePlayBlackHandler(seekBar: SeekBar) {
        mMediaRunnable = Runnable {
            var currentDuration = mMediaPlayer.currentPosition / constants.TIME_MILLISECONDS
            seekBar.setProgress(currentDuration.toInt())
            println("Current duration: " + currentDuration)
            mMediaHandler.postDelayed(mMediaRunnable, constants.TIME_MILLISECONDS)

        }
        mMediaHandler.postDelayed(mMediaRunnable, constants.TIME_MILLISECONDS)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = this.musicDataList?.size
}

