package com.example.musicplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import com.example.musicplayer.adapter.MusicListAdapter
import com.example.musicplayer.data.MusicStore
import java.io.File

class MusicViewModel: ViewModel() {
    var SongFilteredList = arrayListOf<MusicStore>()
    var mContext: Context? = null
    lateinit var musicListAdapter: MusicListAdapter

    fun setContext(context: Context) {
        this.mContext = context;
    }
    fun readFilesFromStorage() {
        SongFilteredList = filterSongs(Environment.getExternalStorageDirectory())
        if (!SongFilteredList.isEmpty()) {
            musicListAdapter = mContext?.let { MusicListAdapter(it) }!!
            musicListAdapter?.setMusicList(SongFilteredList)
        }
    }
    fun getListAdapter(): MusicListAdapter {
        return this.musicListAdapter
    }

    fun filterSongs(file: File) : ArrayList<MusicStore> {
        var fileList = file.listFiles()
        var arrayList = arrayListOf<MusicStore>()
        for (item in fileList) {
            if (item.isDirectory) {
                arrayList.addAll(filterSongs(item))
            } else {
                if (item.name.endsWith(".mp3")) {
                    var musicStore = MusicStore()
                    musicStore.setUri(Uri.parse(item.toString()))
                    musicStore.setSongName(item.name.replace(".mp3",""))
                    arrayList.add(musicStore)
                }
            }
        }
        return arrayList
    }
}