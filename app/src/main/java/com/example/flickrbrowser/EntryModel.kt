package com.example.flickrbrowser

import android.util.Log
import java.io.*

class EntryModel(var title: String,
                 var author: String,
                 var authorId: String,
                 var fullLink: String,
                 var tags: String,
                 var smallLink: String):Serializable
{

    companion object{
        private const val serialVersionUID = 1L
    }
    override fun toString(): String {
        return "Photo(title='$title',author= '$author' authorId='$authorId', link='$fullLink', tags='$tags', image='$smallLink')\n"
    }

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream){
        Log.d("Photo","writeObject: called")
        out.writeUTF(title)
        out.writeUTF(author)
        out.writeUTF(authorId)
        out.writeUTF(fullLink)
        out.writeUTF(tags)
        out.writeUTF(smallLink)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(inStream: ObjectInputStream){
        Log.d("photo", "readObject: called")
        title = inStream.readUTF()
        author = inStream.readUTF()
        authorId = inStream.readUTF()
        fullLink = inStream.readUTF()
        tags = inStream.readUTF()
        smallLink = inStream.readUTF()
    }

    @Throws(ObjectStreamException::class)
    private fun readObjectNoData(){
        Log.d("photo", "readObjectNoData: called")
    }
}
