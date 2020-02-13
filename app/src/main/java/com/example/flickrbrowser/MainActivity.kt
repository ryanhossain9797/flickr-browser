package com.example.flickrbrowser

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class MainActivity : AppCompatActivity(),OnDownloadCompleteRecepient,OnDataAvailableRecepient{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //--------------Step 1, request raw json from url
        val getRawData = GetRawData(this)
        getRawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?&format=json&nojsoncallback=1&tags=android")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG,"onCreateOptionsMenu called")
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG,"onOptionsItemSelected called")
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //-------Callback function for getting raw json
    override fun onDownloadComplete(data: String, status: DownloadStatus){
        if(status == DownloadStatus.OK){
            Log.d(TAG, "onDownloadComplete: ok")

            //----------------Step 2, get actual photo items from json
            val getData = GetFlickrJsonData(this)
            getData.execute(data)
        }
        else{
            Log.e(TAG,"error: status is: $status")
        }
    }

    //-------Callback function for getting photo list using raw data
    override fun onDataAvailable(data: ArrayList<Photo>){
        Log.d(TAG, "onDataAvailable: called")
    }
    //-------Error if getting photo list fails
    override fun onError(err: JSONException){
        Log.d(TAG, "onError: error parsing json ${err.message}")
    }

    companion object{
        private const val TAG = "MainActivity"
    }

}
