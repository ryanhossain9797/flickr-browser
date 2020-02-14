package com.example.flickrbrowser

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.io.*

class EntryModel(var title: String,
                 var author: String,
                 var authorId: String,
                 var fullLink: String,
                 var tags: String,
                 var smallLink: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun toString(): String {
        return "Photo(title='$title',author= '$author' authorId='$authorId', link='$fullLink', tags='$tags', image='$smallLink')\n"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(authorId)
        parcel.writeString(fullLink)
        parcel.writeString(tags)
        parcel.writeString(smallLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntryModel> {
        override fun createFromParcel(parcel: Parcel): EntryModel {
            return EntryModel(parcel)
        }

        override fun newArray(size: Int): Array<EntryModel?> {
            return arrayOfNulls(size)
        }
    }

}
