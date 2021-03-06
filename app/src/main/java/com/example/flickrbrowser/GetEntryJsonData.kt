package com.example.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

interface OnDataAvailableRecepient{
    fun onDataAvailable(data: ArrayList<EntryModel>)
    fun onError(err: JSONException)
}

class GetFlickrJsonData(private val listener:
                        OnDataAvailableRecepient):AsyncTask<String,Void,ArrayList<EntryModel>>() {
    private val TAG = "GetFlickrJsonData"

    override fun doInBackground(vararg params: String): ArrayList<EntryModel> {
        Log.d(TAG,"doInBackground starts")
        val photoList = ArrayList<EntryModel>();
        try{
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")
            for(i in 0 until itemsArray.length()){
                val photoJSON = itemsArray.getJSONObject(i)
                val title = photoJSON.getString("title")
                val author = photoJSON.getString("author")
                val author_id = photoJSON.getString("author_id")
                val tags = photoJSON.getString("tags")

                val jsonMedia = photoJSON.getJSONObject("media")
                val smallLink = jsonMedia.getString("m")
                val fullLink = smallLink.replaceFirst("_m.jpg","_b.jpg")

                val photo = EntryModel(title,author,author_id,fullLink,tags,smallLink)

                photoList.add(photo)
                Log.d(TAG,"doInBackground $photo")
            }
        }
        catch(err: JSONException){
            Log.d(TAG,"doInBackground error parsing json ${err.message}")

            //-------------Stop async task on error, so onPostExecute isn't called
            cancel(true)
            listener.onError(err)
        }
        return photoList
    }

    override fun onPostExecute(result: ArrayList<EntryModel>) {
        Log.d(TAG,"onPostExecute: starts")
        listener.onDataAvailable(result)
        Log.d(TAG,"onPostExecute: finished")
    }
}