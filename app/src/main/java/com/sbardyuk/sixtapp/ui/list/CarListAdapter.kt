package com.sbardyuk.sixtapp.ui.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sbardyuk.sixtapp.R
import com.sbardyuk.sixtapp.vm.model.CarUIModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_car.view.*

class CarListAdapter(private var cars: List<CarUIModel>, private val context: Context, private val carClickListener: OnCarClickListener) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_car, parent, false))

    override fun getItemCount(): Int = cars.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {

            val car = cars[position]

            carNameView.text   = car.name
            fuelLevelView.text = "%1.2f".format(car.fuelLevel) + car.fuelType
            modelNameView.text = car.fullModelName

            Picasso.with(context)
                .load(cars[position].carImageUrl)
                .placeholder(R.drawable.progress_bar_drawable)
                .error(R.drawable.placeholder_car)
                .into(carImageView)

            cardView.setOnClickListener { carClickListener.onItemClick(car) }
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val fuelLevelView: AppCompatTextView = view.item_list_fuel_level
    val carNameView: AppCompatTextView = view.item_list_car_name
    val modelNameView: AppCompatTextView = view.item_list_model_name
    val carImageView: AppCompatImageView = view.item_list_car_image
    val cardView: View = view.item_list_card_view
}
