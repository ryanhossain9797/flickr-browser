package com.example.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlickrImageViewHolder(view: View): RecyclerView.ViewHolder(view){
    //------------Holds a view for the code to interact with
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

class FlickrRecyclerViewAdapter(private var photoList: List<Photo>): RecyclerView.Adapter<FlickrImageViewHolder>() {
    private val TAG = "lickrRecyclerViewA"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        //------------Called by layout manager when it needs a new view
        Log.d(TAG,"onCreateViewHolder: new view requested")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_entry,parent,false)
        return FlickrImageViewHolder(view)
    }

    fun loadNewData(newPhotos: List<Photo>){
        photoList = newPhotos
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): Photo?{
        return if(photoList.isNotEmpty()) photoList[position] else null;
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: called")
        return if(photoList.isNotEmpty()) photoList.size else 0
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}