package com.sbardyuk.sixtapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.newInstance
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.sbardyuk.sixtapp.R
import com.sbardyuk.sixtapp.di.KodeinContainers
import com.sbardyuk.sixtapp.ui.maps.MarkerInfoWindowAdapter
import com.sbardyuk.sixtapp.ui.maps.MarkerTag
import com.sbardyuk.sixtapp.ui.maps.PicassoMarker
import com.sbardyuk.sixtapp.vm.CarMapViewModel
import com.sbardyuk.sixtapp.vm.model.CarMapModel
import com.squareup.picasso.Picasso

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var viewModel: CarMapViewModel
    private lateinit var map: GoogleMap

    //private val mapReadyLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = KodeinContainers.diSixtProject.newInstance { CarMapViewModel(instance()) }

        viewModel.carsMap.observe(this, Observer(::onCarsReceived))
        viewModel.error.observe(this, Observer(::onError))

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val markerInfoWindowAdapter = MarkerInfoWindowAdapter(applicationContext)
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter)
    }

    private fun onCarsReceived(cars: List<CarMapModel>?) {
        val builder = LatLngBounds.Builder()

        cars?.forEach {
            val position = LatLng(it.latitude, it.longitude)
            val marker = map.addMarker(MarkerOptions().position(position))
            val picassoMarker = PicassoMarker(marker)

            // picasso holds only weak ref
            marker.tag = MarkerTag(picassoMarker, it)

            // TODO: fix placeholder scaling somehow
            Picasso.with(this)
                .load(it.carImageUrl)
                .resize(dpToPx(70f).toInt(), 0)
                .placeholder(R.drawable.placeholder_car)
                .error(R.drawable.placeholder_car)
                .into(picassoMarker)

            builder.include(position)
        }

        val bounds = builder.build()
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, 0)
        map.animateCamera(cu)
    }

    private fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun dpToPx(dp: Float): Float {
        return dp * this.resources.getDisplayMetrics().density
    }
}
