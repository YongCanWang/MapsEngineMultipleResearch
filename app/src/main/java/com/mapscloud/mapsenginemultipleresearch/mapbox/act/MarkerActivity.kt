package com.mapscloud.mapsenginemultipleresearch.mapbox.act

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.MultiPolygon
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapscloud.mapsenginemultipleresearch.R
import com.mapscloud.mapsenginemultipleresearch.mapbox.bean.StationBean
import com.mapscloud.mapsenginemultipleresearch.utils.Utils

/**
 * @author Tom灿
 * @description: 海量点数据
 * @date :2023/9/13 10:59
 */
class MarkerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)
        val mapView = findViewById<MapView>(R.id.mapView)
        mapView.getMapAsync {
            it.setStyle(Style.MAPBOX_STREETS, Style.OnStyleLoaded { it ->
                it.addImage(
                    "my-marker-image", BitmapFactory.decodeResource(
                        this@MarkerActivity.resources,
                        R.mipmap.red_marker
                    )
                )
                val station = Utils.loadGeoJsonFromAsset(this@MarkerActivity, "station.json")
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

                val geoJsonSource2 =
                    GeoJsonSource("station-source", getFeatureCollection())

                it.addSource(geoJsonSource)
//                it.addSource(geoJsonSource2)
//                it.addSource(getGeoJsonSource())
                val symbolLayer = SymbolLayer("station-layer", "station-source")
                symbolLayer.setProperties(
                    PropertyFactory.iconImage("my-marker-image"),
                )
                it.addLayer(symbolLayer)

            })
        }
    }


    /**
     * 把一个多边形MultiPolygon解析
     */
    private fun getGeoJsonSource(): GeoJsonSource {
        val features = ArrayList<Feature>()
        val geojson = Utils.loadGeoJsonFromAsset(this@MarkerActivity,
            "北京行政区划.geoJson")
        var featureCollection = FeatureCollection.fromJson(geojson!!)
        for (i in featureCollection.features()!!.indices) {
            var multiPolygons = (featureCollection.features()!![i].geometry()
                    as MultiPolygon).polygons()
            for (y in multiPolygons.indices) {
                var collection = multiPolygons[y].coordinates()
                for (z in collection.indices) {
                    for (l in collection[z]) {
                        val point = Point.fromLngLat(l.longitude(), l.latitude())
                        val feature = Feature.fromGeometry(point)
                        features.add(feature)
                    }
                }
            }
        }
        return GeoJsonSource("station-source", FeatureCollection.fromFeatures(features))
    }


    private fun getFeatureCollection(): FeatureCollection {
        val geojson = Utils.loadGeoJsonFromAsset(this@MarkerActivity,
            "北京行政区划.geoJson")
        return FeatureCollection.fromJson(geojson!!)
    }
}