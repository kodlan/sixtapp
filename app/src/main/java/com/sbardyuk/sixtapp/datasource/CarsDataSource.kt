package com.sbardyuk.sixtapp.datasource

import com.sbardyuk.sixtapp.datasource.model.CarResponse
import com.sbardyuk.sixtapp.datasource.retrofit.CarsApiService
import com.sbardyuk.sixtapp.datasource.retrofit.ResultObject
import com.sbardyuk.sixtapp.datasource.retrofit.ResultType
import com.sbardyuk.sixtapp.datasource.retrofit.RetrofitConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CarsDataSource(private val retrofitConfiguration: RetrofitConfiguration) {

    suspend fun getCars(): ResultObject<List<CarResponse>>? {
        var result: ResultObject<List<CarResponse>>? = null

        withContext(Dispatchers.IO) {
            try {
                val retrofitInstance = retrofitConfiguration.getRetrofitInstance()

                val carsApiService = retrofitInstance.create(CarsApiService::class.java)
                val request = carsApiService.getCars()

                val response = request?.await()

                request?.let {
                    if (it.isCompleted) {
                        result = ResultObject(ResultType.SUCCESS, response)
                    } else if (it.isCancelled) {
                        result = ResultObject(ResultType.ERROR, null,"Can't fetch cars")
                    }
                }
            } catch (ex: Exception) {
                result = ResultObject(ResultType.ERROR, null,"Can't fetch cars")
            }
        }

        return result
    }
}
