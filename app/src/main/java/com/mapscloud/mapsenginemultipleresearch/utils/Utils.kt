package com.mapscloud.mapsenginemultipleresearch.utils

import android.content.Context
import java.nio.charset.Charset

/**
 * @author Tom灿
 * @description:
 * @date :2023/9/13 11:01
 */
object Utils {

     fun loadGeoJsonFromAsset(context: Context, filename: String): String? {
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