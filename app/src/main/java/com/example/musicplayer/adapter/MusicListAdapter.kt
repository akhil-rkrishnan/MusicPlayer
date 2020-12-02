package com.example.musicplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.data.MusicStore
import java.io.File

class MusicListAdapter(context: Context): RecyclerView.Adapter<MusicListAdapter.ViewHolder>() {
    val mContext = context
    lateinit var musicDataList: ArrayList<MusicStore>
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    fun setMusicList(musicDataList: ArrayList<MusicStore>) {
        this.musicDataList = musicDataList;
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val musicName: TextView

        init {
            // Define click listener for the ViewHolder's View.
            musicName = view.findViewById(R.id.music_name)
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

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = this.musicDataList?.size
}
