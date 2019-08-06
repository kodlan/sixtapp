package com.sbardyuk.sixtapp.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.sbardyuk.sixtapp.datasource.CarsDataSource
import com.sbardyuk.sixtapp.datasource.retrofit.RetrofitConfiguration
import com.sbardyuk.sixtapp.domain.CarsRepository
import com.sbardyuk.sixtapp.domain.usecase.GetCarsUseCase
import com.sbardyuk.sixtapp.repository.CarsRepositoryImpl

class KodeinContainers {

    companion object {
        val diSixtProject = Kodein {
            bind<RetrofitConfiguration>() with provider { RetrofitConfiguration() }
            bind<CarsDataSource>() with provider { CarsDataSource(instance()) }
            bind<CarsRepository>() with provider { CarsRepositoryImpl(instance()) }
            bind<GetCarsUseCase>() with provider { GetCarsUseCase(instance()) }
        }
    }
}