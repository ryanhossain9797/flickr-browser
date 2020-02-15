package com.example.flickrbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONException

class MainActivity : BaseActivity(),OnDownloadCompleteRecepient,
    OnDataAvailableRecepient, OnRecyclerClickListener{


    private val TAG = "MainActivity"

    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())//start with no data

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate: starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        recycler_view.layoutManager = LinearLayoutManager(this)

        //Needs the entire recyclerview cause it uses touch x,y to find the item number
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,recycler_view,this))
        recycler_view.adapter = flickrRecyclerViewAdapter



        val url = createUri("https://api.flickr.com/services/feeds/photos_public.gne","android,oreo","en-us", true)
        //--------------Step 1, request raw json from url
        val getRawData = GetRawData(this)
        getRawData.execute(url)
        Log.d(TAG,"onCreate: ends")
    }

    override fun onResume() {
        Log.d(TAG,"onResume: called")
        super.onResume()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val searchTerm = sharedPref.getString(FLICKR_QUERY,"")
        if(searchTerm!!.isNotEmpty()){
            val url = createUri("https://api.flickr.com/services/feeds/photos_public.gne",searchTerm.replace(" ",","),"en-us", true)
            val getRawData = GetRawData(this)
            getRawData.execute(url)
        }
        Log.d(TAG,"onResume: ended")
    }

    private fun createUri(baseUri:String,searchCriteria:String,lang:String,matchAll:Boolean):String{
        Log.d(TAG,"createUri: start")
        return Uri.parse(baseUri)
            .buildUpon()
            .appendQueryParameter("tags", searchCriteria)
            .appendQueryParameter("lang", lang)
            .appendQueryParameter("tagmode", if(matchAll) "ALL" else "ANY")
            .appendQueryParameter("tags", searchCriteria)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .build().toString()
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
            R.id.action_search -> {
                startActivity(Intent(this,SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    //------------------------------------------
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
    //------------------------------------------



    //------------------------------------------
    //-------Callback function for getting photo list using raw data, callback
    override fun onDataAvailable(data: ArrayList<EntryModel>){
        Log.d(TAG, "onDataAvailable: called")
        flickrRecyclerViewAdapter.loadNewData(data)
        Log.d(TAG, "onDataAvailable: ended")
    }
    //-------Error if getting photo list fails
    override fun onError(err: JSONException){
        Log.d(TAG, "onError: error parsing json ${err.message}")
    }
    //------------------------------------------



    //------------------------------------------
    //-------What happens when an item in the list gets clicked or long clicked, callback
    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG,"onItemClick: starts")
        //Toast.makeText(this,"tap at $position",Toast.LENGTH_SHORT).show()
        val photo = flickrRecyclerViewAdapter.getPhoto(position)
        if(photo!=null){
            val intent = Intent(this,DetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER,photo)
            startActivity(intent)
        }
    }
    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG,"onItemLongClick: starts")
        Toast.makeText(this,"long tap at $position",Toast.LENGTH_SHORT).show()
    }
    //------------------------------------------
}
