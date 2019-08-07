package com.sbardyuk.sixtapp.ui.maps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.sbardyuk.sixtapp.R

class MarkerInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {
    private val context: Context = context.applicationContext

    override fun getInfoWindow(arg0: Marker): View? {
        return null
    }

    override fun getInfoContents(arg0: Marker): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.map_marker_info_window, null)

        val carUi = (arg0.tag as MarkerTag).carMap;

        val nameView = v.findViewById(R.id.name_view) as TextView
        val modelView = v.findViewById(R.id.car_model_view) as TextView

        nameView.text = carUi.name
        modelView.text = carUi.fullModelName

        return v
    }
}