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
import com.google.android.gms.maps.model.*
import com.sbardyuk.sixtapp.R
import com.sbardyuk.sixtapp.di.KodeinContainers
import com.sbardyuk.sixtapp.ui.maps.MarkerInfoWindowAdapter
import com.sbardyuk.sixtapp.ui.maps.MarkerTag
import com.sbardyuk.sixtapp.ui.maps.PicassoMarker
import com.sbardyuk.sixtapp.vm.CarMapViewModel
import com.sbardyuk.sixtapp.vm.model.CarMapModel
import com.squareup.picasso.Picasso

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val SELECTED_CAR_PARAM = "selected_car"
    }

    private lateinit var viewModel: CarMapViewModel
    private lateinit var map: GoogleMap

    private var selectedCarId: String? = null
    private lateinit var carList: List<CarMapModel>

    private val mapReadyLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        initSelectedCar()

        initMap()

        initViewModel()
    }

    private fun initSelectedCar() {
        selectedCarId = intent.getStringExtra(SELECTED_CAR_PARAM)
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initViewModel() {
        viewModel = KodeinContainers.diSixtProject.newInstance { CarMapViewModel(instance()) }

        mapReadyLiveData.observe(this, Observer {
            // map is ready - create markers/show error
            viewModel.carsMap.observe(this, Observer(::onCarsReceived))
            viewModel.error.observe(this, Observer(::onError))
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val markerInfoWindowAdapter = MarkerInfoWindowAdapter(applicationContext)
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter)

        mapReadyLiveData.postValue(true)
    }

    private fun onCarsReceived(cars: List<CarMapModel>) {
        val builder = LatLngBounds.Builder()
        carList = cars

        cars.forEach {
            val position = LatLng(it.latitude, it.longitude)
            val marker = map.addMarker(MarkerOptions().position(position))

            loadMarkerImage(it, marker)

            builder.include(position)
        }

        zoom(builder)
    }

    private fun loadMarkerImage(carMapModel: CarMapModel, marker: Marker) {
        val picassoMarker = PicassoMarker(marker)

        // picasso holds only weak ref
        marker.tag = MarkerTag(picassoMarker, carMapModel)

        // TODO: fix placeholder scaling somehow
        Picasso.with(this)
            .load(carMapModel.carImageUrl)
            .resize(dpToPx(70f).toInt(), 0)
            .placeholder(R.drawable.placeholder_car)
            .error(R.drawable.placeholder_car)
            .into(picassoMarker)
    }

    private fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun dpToPx(dp: Float): Float {
        return dp * this.resources.getDisplayMetrics().density
    }

    private fun zoom(builder: LatLngBounds.Builder) {
        if (selectedCarId != null) {
            // zoom to car selected in the list
            zoomToCar()
        } else {
            // zoom to show all cars on the map
            zoomToBounds(builder)
        }
    }

    private fun zoomToBounds(builder: LatLngBounds.Builder) {
        val bounds = builder.build()
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))
    }

    private fun zoomToCar() {
        val carMapModel = findCarMapModel()

        carMapModel?.let {
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(it.latitude, it.longitude))
                .zoom(15f)
                .build()
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    private fun findCarMapModel(): CarMapModel? {
        return carList.asSequence().find { it.id == selectedCarId }
    }
}
