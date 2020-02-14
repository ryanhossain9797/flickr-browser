package com.example.flickrbrowser

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


internal const val FLICKR_QUERY = "FLICKR_QUERY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"

abstract class BaseActivity : AppCompatActivity(){
    private val TAG = "BaseActivity"

    //------------set toolbar and enable/disable home
    internal fun activateToolbar(enableHome: Boolean){
        Log.d(TAG,"activateToolbar")

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}