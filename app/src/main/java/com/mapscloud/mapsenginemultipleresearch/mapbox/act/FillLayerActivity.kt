package com.mapscloud.mapsenginemultipleresearch.mapbox.act

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity
import com.mapbox.mapboxsdk.style.layers.PropertyValue
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapscloud.mapsenginemultipleresearch.R
import com.mapscloud.mapsenginemultipleresearch.utils.Utils


/**
 * @author Tom灿
 * @description:
 * @date :2023/9/13 11:14
 */
class FillLayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)
        val mapView = findViewById<MapView>(R.id.mapView)
        mapView.getMapAsync {
            it.setStyle(Style.MAPBOX_STREETS, Style.OnStyleLoaded { style ->
                var geojson = Utils.loadGeoJsonFromAsset(
                    this,
                    "北京行政区划.geoJson"
                )
                var featureCollection = FeatureCollection.fromJson(geojson!!)
                var geoJsonSource = GeoJsonSource(
                    "source-id",
                    featureCollection
                )
                style.addSource(geoJsonSource)

                val fillLayer = FillLayer("fill-layer-id", "source-id")
                fillLayer.setProperties(
                    fillColor(Color.BLUE),  // 填充颜色
                    fillOpacity(0.8f) // 填充透明度
                )
                style.addLayer(fillLayer)
            })
        }
    }
}