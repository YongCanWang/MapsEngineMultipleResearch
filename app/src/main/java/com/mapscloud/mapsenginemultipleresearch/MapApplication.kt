package com.mapscloud.mapsenginemultipleresearch

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

/**
 * @author Tom灿
 * @description:
 * @date :2023/9/13 11:03
 */
class MapApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(
            this,
            "pk.eyJ1IjoiZGFuemlzZSIsImEiOiJjamJwdmo2a2YyY20xMndxa295YXJlZWV5In0._DzAMKkWtDGUufHazS6aYQ"
        )
    }
}