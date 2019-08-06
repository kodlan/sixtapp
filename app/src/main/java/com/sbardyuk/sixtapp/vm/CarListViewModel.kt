package com.sbardyuk.sixtapp.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbardyuk.sixtapp.datasource.retrofit.ResultObject
import com.sbardyuk.sixtapp.datasource.retrofit.ResultType
import com.sbardyuk.sixtapp.domain.model.CarModel
import com.sbardyuk.sixtapp.domain.usecase.GetCarsUseCase
import com.sbardyuk.sixtapp.vm.mapper.CarModelToCarUIMapper
import com.sbardyuk.sixtapp.vm.model.CarUIModel
import kotlinx.coroutines.launch

class CarListViewModel(private val getCarsUseCase: GetCarsUseCase) : ViewModel() {

    private val carsLiveData: MutableLiveData<List<CarUIModel>> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    val cars: MutableLiveData<List<CarUIModel>>
        get() = carsLiveData

    val isLoading: MutableLiveData<Boolean>
        get() = isLoadingLiveData

    val error: MutableLiveData<String>
        get() = errorLiveData

    init {
        startLoad()
    }

    private fun startLoad() {
        viewModelScope.launch {
            isLoadingLiveData(true)
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

        isLoadingLiveData(false)
    }

    private fun onResultSuccess(carModel: List<CarModel>?) {
        val cars = CarModelToCarUIMapper.mapFrom(carModel)
        carsLiveData.postValue(cars)

        // TODO: handle case when cars are empty (show "no cars available message)
    }

    private fun onResultError(errorMessage: String?) {
        errorLiveData.postValue(errorMessage)
    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this.isLoadingLiveData.postValue(isLoading)
    }

    private fun isResultSuccess(result: ResultObject<List<CarModel>>) = result.resultType == ResultType.SUCCESS
}