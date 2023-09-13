package com.mapscloud.mapsenginemultipleresearch.mapbox.act

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.FillExtrusionLayer
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillExtrusionBase
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillExtrusionColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillExtrusionHeight
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillExtrusionOpacity
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.textLineHeight
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapscloud.mapsenginemultipleresearch.R
import com.mapscloud.mapsenginemultipleresearch.utils.Utils
import java.net.URL


/**
 * @author Tom灿
 * @description: 3D行政区划
 * @date :2023/9/13 11:14
 */
class Fill3DActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)
        val mapView = findViewById<MapView>(R.id.mapView)
        mapView.getMapAsync {
            var latLng = LatLng(39.6, 116.46)
            var cameraPosition =
                CameraPosition.Builder().target(latLng).zoom(10.0).tilt(30.0).build()
            val update = CameraUpdateFactory.newCameraPosition(cameraPosition)
            it.easeCamera(update)
            it.setStyle(Style.MAPBOX_STREETS, Style.OnStyleLoaded { style ->
                var geojson = Utils.loadGeoJsonFromAsset(
                    this,
                    "北京行政区划.geoJson"
                )
                var featureCollection = FeatureCollection.fromJson(geojson!!)

                var geoJsonSource = GeoJsonSource(
                    "source-id", featureCollection
                )
                style.addSource(geoJsonSource)

                val fillExtrusionLayer = FillExtrusionLayer("extrusion-layer-id", "source-id")
                fillExtrusionLayer.setProperties(
//                    fillExtrusionBase(14000.0f),
                    visibility(Property.VISIBLE),
                    fillExtrusionColor(Color.BLUE),    // 填充颜色
                    fillExtrusionHeight(15000.0f),  // 填充高度
                    fillExtrusionOpacity(0.8f),    // 填充透明度
                )
                style.addLayer(fillExtrusionLayer)

                // TODO 高亮边界线，无法给LineLayer加高度，效果不行
                addBorderLine(style)
            })
        }
    }

    private fun addBorderLine(style: Style) {
        style.addSource(
            GeoJsonSource(
                "line-source-id",
                URL("https://geo.datav.aliyun.com/areas_v3/bound/geojson?code=110000")// 北京区域，包含子区域
            )
        )
        val lineLayer = LineLayer("line-layer-id", "line-source-id")
        lineLayer.setProperties(
            lineColor(Color.RED),  // 线颜色
            lineWidth(2.0f), // 线宽度
            PropertyFactory.lineOpacity(0.8f), // 线透明度
//            textLineHeight(15000.0f)

        )
        style.addLayer(lineLayer)
    }
}