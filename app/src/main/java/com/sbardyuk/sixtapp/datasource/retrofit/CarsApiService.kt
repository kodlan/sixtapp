package com.sbardyuk.sixtapp.datasource.retrofit

import com.sbardyuk.sixtapp.datasource.model.CarResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface CarsApiService {

    @GET("cars")
    fun getCars(): Deferred<List<CarResponse>>?
}