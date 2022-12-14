package com.example.fide_rent.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fide_rent.data.RentDao
import com.example.fide_rent.model.Car
import com.example.fide_rent.model.Rent
import com.example.fide_rent.repository.RentRepository

class MyRentViewModel(application: Application): AndroidViewModel(application)  {

    val getRents: MutableLiveData<List<Rent>>
    private val repository: RentRepository = RentRepository(RentDao())

    init {
        getRents = repository.getRents
    }
}