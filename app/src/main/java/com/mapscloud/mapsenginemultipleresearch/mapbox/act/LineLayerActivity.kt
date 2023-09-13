package com.mapscloud.mapsenginemultipleresearch.mapbox.act

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineOpacity
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth
import com.mapbox.mapboxsdk.style.layers.PropertyValue
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapscloud.mapsenginemultipleresearch.R
import com.mapscloud.mapsenginemultipleresearch.utils.Utils
import java.net.URL


/**
 * @author Tom灿
 * @description:
 * @date :2023/9/13 11:14
 */
class LineLayerActivity : AppCompatActivity() {

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

//                style.addSource(geoJsonSource)
                style.addSource(getGeoJsonSourceURL())

                val lineLayer = LineLayer("line-layer-id", "source-id")
                lineLayer.setProperties(
                    lineColor(Color.RED),  // 线颜色
                    lineWidth(2.0f), // 线宽度
                    lineOpacity(0.8f) // 线透明度
                )
                style.addLayer(lineLayer)
            })
        }
    }



    private fun getGeoJsonSourceURL(): GeoJsonSource {
        return GeoJsonSource(
            "source-id",
//            URL("https://geo.datav.aliyun.com/areas_v3/bound/110000.json")  // 北京区域，不包含子区域
//            URL("https://geo.datav.aliyun.com/areas_v3/bound/geojson?code=110000")  // 北京区域，不包含子区域
//            URL("https://geo.datav.aliyun.com/areas_v3/bound/110000_full.json")// 北京区域，包含子区域
            URL("https://geo.datav.aliyun.com/areas_v3/bound/geojson?code=110000_full")// 北京区域，包含子区域
//            URL("https://geo.datav.aliyun.com/areas_v2/bound/110000.json")// 北京区域，不包含子区域
        )
    }
}