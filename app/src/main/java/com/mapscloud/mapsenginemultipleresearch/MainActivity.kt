package com.mapscloud.mapsenginemultipleresearch

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapscloud.mapsenginemultipleresearch.mapbox.bean.StationBean
import java.lang.annotation.RetentionPolicy
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO Auto-generated method stub
        Mapbox.getInstance(
            this,
            "pk.eyJ1IjoiZGFuemlzZSIsImEiOiJjamJwdmo2a2YyY20xMndxa295YXJlZWV5In0._DzAMKkWtDGUufHazS6aYQ"
        )
        setContentView(R.layout.activity_main)
        val mapView = findViewById<MapView>(R.id.mapView)
        mapView.getMapAsync {
            it.setStyle(Style.MAPBOX_STREETS, Style.OnStyleLoaded { it ->
                it.addImage("my-marker-image", BitmapFactory.decodeResource(
                    this@MainActivity.resources,
                    R.mipmap.red_marker
                ))
                val station = loadGeoJsonFromAsset(this@MainActivity, "station.json")
                val stationBeans = Gson().fromJson<ArrayList<StationBean>>(
                    station,
                    object : TypeToken<ArrayList<StationBean?>?>() {}.type
                )
                val features = ArrayList<Feature>()
                for (i in stationBeans.indices) {
                    val point = Point.fromLngLat(stationBeans[i].lng, stationBeans[i].lat)
                    val feature = Feature.fromGeometry(point)
                    features.add(feature)
                }
                val geoJsonSource =
                    GeoJsonSource("station-source", FeatureCollection.fromFeatures(features))
                it.addSource(geoJsonSource)
                val symbolLayer = SymbolLayer("station-layer", "station-source")
                symbolLayer.setProperties(
                    PropertyFactory.iconImage("my-marker-image"),
                )
                it.addLayer(symbolLayer)
            })

        }
    }


    private fun loadGeoJsonFromAsset(context: Context, filename: String): String? {
        return try {
            // Load GeoJSON file from local asset folder
            val `is` = context.assets.open(filename)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (exception: Exception) {
            throw RuntimeException(exception)
        }
    }

}