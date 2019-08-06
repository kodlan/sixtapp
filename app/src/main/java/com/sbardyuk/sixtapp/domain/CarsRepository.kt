package com.sbardyuk.sixtapp.domain

import com.sbardyuk.sixtapp.datasource.retrofit.ResultObject
import com.sbardyuk.sixtapp.domain.model.CarModel

interface CarsRepository {

    suspend fun getCars(): ResultObject<List<CarModel>>

}