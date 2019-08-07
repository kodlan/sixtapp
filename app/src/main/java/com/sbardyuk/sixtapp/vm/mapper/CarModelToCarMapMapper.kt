package com.sbardyuk.sixtapp.vm.mapper

import com.sbardyuk.sixtapp.common.BaseMapper
import com.sbardyuk.sixtapp.domain.model.CarModel
import com.sbardyuk.sixtapp.vm.model.CarMapModel

object CarModelToCarMapMapper : BaseMapper<List<CarModel>, List<CarMapModel>> {

    override fun mapFrom(type: List<CarModel>?): List<CarMapModel> {
        val result: MutableList<CarMapModel> = mutableListOf()

        type?.let { beers ->
            beers.forEach {
                result.add(
                    CarMapModel(
                        id = it.id,
                        name = it.name,
                        fullModelName = "${it.make} ${it.modelName}",
                        carImageUrl = it.carImageUrl,
                        latitude = it.latitude.toDouble(),
                        longitude = it.longitude.toDouble()
                    )
                )
            }
        }

        return result
    }
}