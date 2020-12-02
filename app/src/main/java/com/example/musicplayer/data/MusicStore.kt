package com.example.musicplayer.data

import android.net.Uri
import com.example.musicplayer.Constants
import java.net.URI

class MusicStore {

    var mSongName = Constants().EMPTY
    var mArtistName = Constants().EMPTY
    var mDuration = Constants().EMPTY
    lateinit var mUri: Uri

    fun setSongName(songName: String) {
        this.mSongName = songName
    }
    fun setArtistName(artistName: String) {
        this.mArtistName = artistName
    }
    fun setDuration(duration: String) {
        this.mDuration = duration
    }
    fun setUri(uri: Uri) {
        this.mUri = uri
    }

    fun getSongName() : String {
        return this.mSongName;
    }
    fun getArtistName() : String {
        return this.mSongName;
    }
    fun getDuration() : String {
        return this.mSongName;
    }

 }