package com.example.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class EntryImageViewHolder(view: View): RecyclerView.ViewHolder(view){
    //------------Holds a view for the code to interact with
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

class EntryRecyclerViewAdapter(private var entryModelList: List<EntryModel>): RecyclerView.Adapter<EntryImageViewHolder>() {
    private val TAG = "lickrRecyclerViewA"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryImageViewHolder {
        //------------Called by layout manager when it needs a new view
        Log.d(TAG,"onCreateViewHolder: new view requested")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_entry,parent,false)
        return EntryImageViewHolder(view)
    }

    fun loadNewData(newEntryModels: List<EntryModel>){
        entryModelList = newEntryModels
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): EntryModel?{
        return if(entryModelList.isNotEmpty()) entryModelList[position] else null;
    }

    override fun getItemCount(): Int {
        //-------------Gives count of items in list
        Log.d(TAG, "getItemCount: called")
        return if(entryModelList.isNotEmpty()) entryModelList.size else 1
    }

    override fun onBindViewHolder(holder: EntryImageViewHolder, position: Int) {
        //-------------When an existing view needs to load new data
        if(entryModelList.isEmpty()){
            holder.thumbnail.setImageResource(R.drawable.placeholder)
            holder.title.text = "No photo found\n\nTry something else"
        }
        else {
            val photo = entryModelList[position]
            Log.d(TAG, "onBindViewHolder: called")
            Picasso.get()
                .load(photo.smallLink)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail)

            holder.title.text = photo.title
        }
    }
}