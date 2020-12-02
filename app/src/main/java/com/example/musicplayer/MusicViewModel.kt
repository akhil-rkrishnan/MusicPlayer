package com.example.musicplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.os.EnvironmentCompat
import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.MusicStore
import java.io.File

class MusicViewModel: ViewModel() {
    var SongFilteredList = arrayListOf<File>()
    var MusicList = arrayListOf<MusicStore>()
    var mContext: Context? = null

    fun setContext(context: Context) {
        this.mContext = context;
    }
    fun readFilesFromStorage() {
        SongFilteredList = filterSongs(Environment.getExternalStorageDirectory())
        makeSongClass(SongFilteredList)
    }

    fun filterSongs(file: File) : ArrayList<File> {
        var fileList = file.listFiles()
        var arrayList = arrayListOf<File>()
        for (item in fileList) {
            if (item.isDirectory) {
                arrayList.addAll(filterSongs(item))
            } else {
                if (item.name.endsWith(".mp3")) {
                    arrayList.add(item)
                }
            }
        }
        return arrayList
    }

    fun  makeSongClass(songList: ArrayList<File>) {
        for (song in songList) {
            var musicStore = MusicStore()
            val uri = Uri.parse(song.toString())
            musicStore.setUri(uri)
            var mediaPlayer: MediaPlayer? = MediaPlayer.create(mContext, uri)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                println(mediaPlayer?.trackInfo.)
                break
            }

        }
    }
}