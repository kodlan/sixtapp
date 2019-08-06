package com.sbardyuk.sixtapp.repository

import com.sbardyuk.sixtapp.datasource.CarsDataSource
import com.sbardyuk.sixtapp.datasource.retrofit.ResultObject
import com.sbardyuk.sixtapp.datasource.retrofit.ResultType
import com.sbardyuk.sixtapp.domain.CarsRepository
import com.sbardyuk.sixtapp.domain.model.CarModel
import com.sbardyuk.sixtapp.repository.mapper.CarResponseToCarModelMapper

class CarsRepositoryImpl constructor(private val carsDataSource: CarsDataSource) : CarsRepository {

    override suspend fun getCars(): ResultObject<List<CarModel>> {
        val cars = mutableListOf<CarModel>()

        carsDataSource.getCars()
            .let {
                // check for errors
                if (it == null || it.resultType != ResultType.SUCCESS) {
                    return ResultObject(ResultType.ERROR, null, it?.message)
                }

                // no errors continue with mapping
                CarResponseToCarModelMapper.mapFrom(it).let { carModelList ->
                    carModelList.data?.forEach { carModel ->
                        cars.add(carModel)
                    }
                }
            }

        return ResultObject(ResultType.SUCCESS, cars)
    }

}
