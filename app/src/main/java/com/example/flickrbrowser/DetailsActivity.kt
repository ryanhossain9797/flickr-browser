package com.example.flickrbrowser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_details.*

class DetailsActivity : BaseActivity() {

    private val TAG = "DetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate: starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        activateToolbar(true)
        Log.d(TAG,"onCreate: ends")

        val photo = intent.getSerializableExtra(PHOTO_TRANSFER) as EntryModel
        photo_author.text = photo.author
        photo_title.text = photo.title
        photo_tags.text = photo.tags
        Picasso.get()
            .load(photo.fullLink)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(photo_image)
    }

}
