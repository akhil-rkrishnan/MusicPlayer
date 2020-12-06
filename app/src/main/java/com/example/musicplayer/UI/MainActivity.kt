package com.example.musicplayer.UI

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.MusicViewModel
import com.example.musicplayer.R

private const val PERMISSION_REQUEST = 10

class MainActivity : AppCompatActivity() {

    lateinit var mMusicViewModel: MusicViewModel
    private var mPermissionArray = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var mContext: Context
    private lateinit var mRecyclerView: RecyclerView
    private var LOADED = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recyclerView)
        mMusicViewModel = ViewModelProvider(this).get(MusicViewModel::class.java)
        mMusicViewModel.setContext(this)
    }


    override fun onStart() {
        super.onStart()
        var isPermissionGranted = checkPermission(mPermissionArray)
        if (isPermissionGranted) {
            fetchListFromStorage()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(mPermissionArray, PERMISSION_REQUEST)
            } else {
                Toast.makeText(mContext, "Please grant storage permisssion from AppInfo settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun fetchListFromStorage() {
        if (LOADED == 0) {
            mMusicViewModel.readFilesFromStorage()
            mRecyclerView.setHasFixedSize(true)
            mRecyclerView.layoutManager = LinearLayoutManager(this)
            mRecyclerView.adapter = mMusicViewModel.getListAdapter()
            LOADED = 1
        }
    }

    /*Checking permission for storage read*/
    fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        if (!allSuccess) {
            Toast.makeText(mContext, "You have to grant permissions to read files from your device",Toast.LENGTH_SHORT).show()
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    var requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        Toast.makeText(mContext, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(mContext, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess) {
                Toast.makeText(mContext, "Permissions Granted", Toast.LENGTH_SHORT).show()
                fetchListFromStorage()
            } else {
                onStart()
            }
        }
    }
}