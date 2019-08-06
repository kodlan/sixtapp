package com.sbardyuk.sixtapp.domain.usecase

import com.sbardyuk.sixtapp.domain.CarsRepository

class GetCarsUseCase(private val carsRepository: CarsRepository) {

    suspend fun execute() = carsRepository.getCars()

}