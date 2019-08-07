package com.sbardyuk.sixtapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbardyuk.sixtapp.datasource.retrofit.ResultObject
import com.sbardyuk.sixtapp.datasource.retrofit.ResultType
import com.sbardyuk.sixtapp.domain.model.CarModel
import com.sbardyuk.sixtapp.domain.usecase.GetCarsUseCase
import com.sbardyuk.sixtapp.vm.mapper.CarModelToCarMapMapper
import com.sbardyuk.sixtapp.vm.model.CarMapModel
import kotlinx.coroutines.launch


class CarMapViewModel(private val getCarsUseCase: GetCarsUseCase) : ViewModel() {

    private val carsMapLiveData: MutableLiveData<List<CarMapModel>> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    val carsMap: LiveData<List<CarMapModel>>
        get() = carsMapLiveData

    val error: LiveData<String>
        get() = errorLiveData

    init {
        startLoad()
    }

    private fun startLoad() {
        // TODO: car list should probably be cached or set from list activity
        viewModelScope.launch {
            updateLiveData(
                getCarsUseCase.execute()
            )
        }
    }

    private fun updateLiveData(result: ResultObject<List<CarModel>>) {
        if (isResultSuccess(result)) {
            onResultSuccess(result.data)
        } else {
            onResultError(result.message)
        }
    }

    private fun onResultSuccess(carModel: List<CarModel>?) {
        val cars = CarModelToCarMapMapper.mapFrom(carModel)
        carsMapLiveData.postValue(cars)
        // TODO: handle case when cars are empty (show "no cars available message)
    }

    private fun onResultError(errorMessage: String?) {
        errorLiveData.postValue(errorMessage)
    }

    private fun isResultSuccess(result: ResultObject<List<CarModel>>) = result.resultType == ResultType.SUCCESS
}