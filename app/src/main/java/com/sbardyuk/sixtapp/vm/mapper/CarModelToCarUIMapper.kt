package com.sbardyuk.sixtapp.vm.mapper

import com.sbardyuk.sixtapp.common.BaseMapper
import com.sbardyuk.sixtapp.domain.model.CarModel
import com.sbardyuk.sixtapp.vm.model.CarUIModel

object CarModelToCarUIMapper : BaseMapper<List<CarModel>, List<CarUIModel>> {

    override fun mapFrom(type: List<CarModel>?): List<CarUIModel> {
        val result: MutableList<CarUIModel> = mutableListOf()

        type?.let { beers ->
            beers.forEach {
                result.add(
                    CarUIModel(
                        name = it.name,
                        fullModelName = "${it.make} ${it.modelName}",
                        carImageUrl = it.carImageUrl,
                        fuelLevel = it.fuelLevel,
                        fuelType = it.fuelType
                    )
                )
            }
        }

        return result
    }
}