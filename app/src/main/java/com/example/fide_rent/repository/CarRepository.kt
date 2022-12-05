package com.example.fide_rent.repository

import androidx.lifecycle.MutableLiveData
import com.example.fide_rent.data.CarDao
import com.example.fide_rent.model.Car

class CarRepository(private val carDao: CarDao) {

    val getCars: MutableLiveData<List<Car>> = carDao.getCars()
}