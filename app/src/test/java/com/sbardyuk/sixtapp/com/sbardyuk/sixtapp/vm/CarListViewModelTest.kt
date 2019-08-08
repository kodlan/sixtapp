package com.sbardyuk.sixtapp.com.sbardyuk.sixtapp.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sbardyuk.sixtapp.CoroutinesTestRule
import com.sbardyuk.sixtapp.LiveDataTestUtil
import com.sbardyuk.sixtapp.datasource.retrofit.ResultObject
import com.sbardyuk.sixtapp.datasource.retrofit.ResultType
import com.sbardyuk.sixtapp.domain.model.CarModel
import com.sbardyuk.sixtapp.domain.usecase.GetCarsUseCase
import com.sbardyuk.sixtapp.vm.CarListViewModel
import com.sbardyuk.sixtapp.vm.model.CarUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class CarListViewModelTest{

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var carListViewModel: CarListViewModel

    val mockGetCarsUseCase = Mockito.mock(GetCarsUseCase::class.java)

    @Before
    fun setUp() {
        carListViewModel = CarListViewModel(mockGetCarsUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            Mockito.`when`(mockGetCarsUseCase.execute()).thenReturn(ResultObject(ResultType.ERROR, null, "error message"))

            carListViewModel.startLoad()

            Mockito.verify(mockGetCarsUseCase).execute()

            val errorValue = LiveDataTestUtil.getValue(carListViewModel.error)
            assertEquals("error message", errorValue)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDataReturned() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val mockCars = getMockData()

            Mockito.`when`(mockGetCarsUseCase.execute()).thenReturn(ResultObject(ResultType.SUCCESS, mockCars, null))

            carListViewModel.startLoad()

            Mockito.verify(mockGetCarsUseCase).execute()

            val cars = LiveDataTestUtil.getValue(carListViewModel.cars)
            assertNotNull(cars)
            assertEquals(2, cars.size)
            assertNotNull(findWithId("id1", cars))
            assertNotNull(findWithId("id2", cars))
        }
    }

    private fun findWithId(id: String, cars: List<CarUIModel>): CarUIModel? {
        return cars.find { it.id == id }
    }

    private fun getMockData(): List<CarModel> {
        val car1 = CarModel("model1", 0.5f, "red", "11.11", "mini",
            "m", "xxxx", "D", "http://image1.jpg", "series", "clean",
            "name1", "id1", "bmw", "group1", "22.22")

        val car2 = CarModel("model2", 0.1f, "green", "33.33", "R8",
            "m", "yyyy", "P", "http://image2.jpg", "series", "clean",
            "name2", "id2", "audi", "group12", "22.22")

        return listOf(car1, car2)
    }
}
