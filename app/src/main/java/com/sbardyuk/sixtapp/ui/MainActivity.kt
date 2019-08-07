package com.sbardyuk.sixtapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.newInstance
import com.sbardyuk.sixtapp.R
import com.sbardyuk.sixtapp.di.KodeinContainers
import com.sbardyuk.sixtapp.ui.list.CarListAdapter
import com.sbardyuk.sixtapp.vm.CarListViewModel
import com.sbardyuk.sixtapp.vm.model.CarUIModel
import kotlinx.android.synthetic.main.activity_main.*
import com.sbardyuk.sixtapp.ui.list.OnCarClickListener


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CarListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = KodeinContainers.diSixtProject.newInstance { CarListViewModel(instance()) }

        viewModel.cars.observe(this, Observer(::onCarsReceived))
        viewModel.error.observe(this, Observer(::onError))
        viewModel.isLoading.observe(this, Observer(::onLoading))
    }

    private fun onCarsReceived(cars: List<CarUIModel>?) {
        cars?.let {
            recycler_cars.layoutManager = LinearLayoutManager(this)
            recycler_cars.adapter = CarListAdapter(it, this, object : OnCarClickListener {
                override fun onItemClick(item: CarUIModel) {
                    onClick(item)
                }
            })
        }
    }

    private fun onClick(car: CarUIModel) {
        // TODO: make some kind of navigation service and move to viewmodel ?
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun onLoading(isLoading: Boolean) {
        showSpinner(isLoading)
    }

    private fun showSpinner(isLoading: Boolean) {
        cars_spinner.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

}
