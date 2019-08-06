package com.sbardyuk.sixtapp.datasource.model

import com.google.gson.annotations.SerializedName

data class CarResponse (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("modelIdentifier") val modelIdentifier: String,
    @SerializedName("fuelLevel") val fuelLevel: Float,
    @SerializedName("color") val color: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("modelName") val modelName: String,
    @SerializedName("transmission") val transmission: String,
    @SerializedName("licensePlate") val licensePlate: String,
    @SerializedName("fuelType") val fuelType: String,
    @SerializedName("carImageUrl") val carImageUrl: String,
    @SerializedName("series") val series: String,
    @SerializedName("innerCleanliness") val innerCleanliness: String,
    @SerializedName("make") val make: String,
    @SerializedName("group") val group: String,
    @SerializedName("longitude") val longitude: String
)