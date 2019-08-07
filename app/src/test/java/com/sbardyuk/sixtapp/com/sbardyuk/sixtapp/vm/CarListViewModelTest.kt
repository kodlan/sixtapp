package com.sbardyuk.sixtapp.com.sbardyuk.sixtapp.vm

import com.sbardyuk.sixtapp.CoroutinesTestRule
import com.sbardyuk.sixtapp.datasource.retrofit.ResultObject
import com.sbardyuk.sixtapp.datasource.retrofit.ResultType
import com.sbardyuk.sixtapp.domain.usecase.GetCarsUseCase
import com.sbardyuk.sixtapp.vm.CarListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(JUnit4::class)
class CarListViewModelTest{

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

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

            assertEquals("error message", carListViewModel.error.value)
        }
    }
}
