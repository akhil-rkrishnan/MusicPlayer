package com.example.musicplayer.data

import android.net.Uri
import com.example.musicplayer.Constants

class MusicStore {
    var constants = Constants()
    var mSongName = constants.EMPTY
    var mArtistName = constants.EMPTY
    var mDuration = constants.EMPTY
    var mPath = constants.EMPTY
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
    fun setPath(path: String) {
        this.mPath = path;
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
    fun getUri() : Uri {
        return this.mUri
    }
    fun getMusicPath() : String {
        return this.mPath
    }
 }