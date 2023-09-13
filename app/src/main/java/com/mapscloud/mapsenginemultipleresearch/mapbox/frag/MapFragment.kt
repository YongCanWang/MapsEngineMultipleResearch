package com.mapscloud.mapsenginemultipleresearch.mapbox.frag

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import com.mapscloud.mapsenginemultipleresearch.mapbox.act.MarkerActivity
import com.mapscloud.mapsenginemultipleresearch.R
import com.mapscloud.mapsenginemultipleresearch.mapbox.act.Fill3DActivity
import com.mapscloud.mapsenginemultipleresearch.mapbox.act.FillLayerActivity
import com.mapscloud.mapsenginemultipleresearch.mapbox.bean.SampleItem
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author Tom灿
 * @description:
 * @date :2023/9/13 9:28
 */
class MapFragment : Fragment() {

    lateinit var samples: ArrayList<SampleItem>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        samples = ArrayList(
            listOf(
                SampleItem(
                    getString(R.string.cover_point_city),
                    getString(R.string.cover_point_city_des),
                    MarkerActivity::class.java
                ),
                SampleItem(
                    getString(R.string.cover_fill_city),
                    getString(R.string.cover_fill_city_des),
                    FillLayerActivity::class.java
                ),
                SampleItem(
                    getString(R.string.cover_fill3D_city),
                    getString(R.string.cover_fill3D_city_des),
                    Fill3DActivity::class.java
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        var recyclerView = root.findViewById<RecyclerView>(R.id.map_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addOnItemTouchListener(SimpleOnItemTouchListener())
        recyclerView.setHasFixedSize(true)
        val adapter: CommonAdapter<SampleItem> = object : CommonAdapter<SampleItem>(
            context,
            R.layout.item_feature, samples
        ) {
            override fun convert(holder: ViewHolder?, sampleItem: SampleItem, position: Int) {
                holder!!.setText(R.id.nameView, sampleItem.name)
                holder.setText(R.id.descriptionView, sampleItem.description)
            }
        }
        adapter.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View, viewHolder: RecyclerView.ViewHolder, i: Int) {
                val intent = Intent(activity, samples[i].clazz)
                startActivity(intent)
            }

            override fun onItemLongClick(
                view: View,
                viewHolder: RecyclerView.ViewHolder,
                i: Int
            ): Boolean {
                return false
            }
        })
        recyclerView.adapter = adapter
        return root
    }


}