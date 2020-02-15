package com.example.flickrbrowser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.core.content.getSystemService

import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    private val TAG = "SearchActivity"

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate: starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activateToolbar(true)
        Log.d(TAG,"onCreate: ends")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //------------Using the created menu as the toolbar
        Log.d(TAG,"onCreateOptionsMenu: starts")
        menuInflater.inflate(R.menu.menu_search,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)

        //Sets the search text box open instead of just an icon
        searchView?.isIconified = false


        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG,"onQueryTextSubmit")

                //-----------Putting the search term in shared preferences
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharedPref.edit().putString(FLICKR_QUERY,query).apply()
                searchView?.clearFocus()

                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView?.setOnCloseListener{
            finish()
            true
        }
        Log.d(TAG,"onCreateOptionsMenu: ends")
        return true
    }
}
