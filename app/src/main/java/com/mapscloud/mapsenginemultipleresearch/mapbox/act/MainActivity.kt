package com.mapscloud.mapsenginemultipleresearch.mapbox.act

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapscloud.mapsenginemultipleresearch.R
import com.mapscloud.mapsenginemultipleresearch.mapbox.frag.MapFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, MapFragment())
            .commit()
    }

}