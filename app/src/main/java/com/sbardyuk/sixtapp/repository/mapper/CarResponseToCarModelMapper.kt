package com.sbardyuk.sixtapp.repository.mapper

import com.sbardyuk.sixtapp.common.BaseMapper
import com.sbardyuk.sixtapp.datasource.model.CarResponse
import com.sbardyuk.sixtapp.datasource.retrofit.ResultObject
import com.sbardyuk.sixtapp.datasource.retrofit.ResultType
import com.sbardyuk.sixtapp.domain.model.CarModel

object CarResponseToCarModelMapper : BaseMapper<ResultObject<List<CarResponse>>?, ResultObject<List<CarModel>>> {

    override fun mapFrom(type: ResultObject<List<CarResponse>>?): ResultObject<List<CarModel>> = when {
        type?.resultType?.equals(ResultType.SUCCESS)!! -> map(type)
        else -> ResultObject(ResultType.ERROR, null, "Error when mapping")
    }

    private fun map(beersResponseResult: ResultObject<List<CarResponse>>): ResultObject<List<CarModel>> {
        val carModels: MutableList<CarModel> = mutableListOf()

        beersResponseResult.data?.forEach {
            carModels.add(
                CarModel(
                    id = it.id,
                    name = it.name,
                    modelIdentifier = it.modelIdentifier,
                    fuelLevel = it.fuelLevel,
                    color =  it.color,
                    latitude = it.latitude,
                    longitude =  it.longitude,
                    modelName = it.modelName,
                    transmission = it.transmission,
                    licensePlate =  it.licensePlate,
                    fuelType = it.fuelType,
                    carImageUrl = it.carImageUrl,
                    series = it.series,
                    innerCleanliness = it.innerCleanliness,
                    make = it.make,
                    group = it.group
                )
            )
        }

        return ResultObject(ResultType.SUCCESS, carModels)
    }
}
