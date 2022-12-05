package com.example.fide_rent.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fide_rent.data.CarDao
import com.example.fide_rent.model.Car
import com.example.fide_rent.repository.CarRepository

class SelectCarViewModel(application: Application): AndroidViewModel(application){

    val getCars: MutableLiveData<List<Car>>
    private val repository: CarRepository = CarRepository(CarDao())

    init {
        getCars = repository.getCars
    }

}