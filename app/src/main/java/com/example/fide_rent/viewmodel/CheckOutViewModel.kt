package com.example.fide_rent.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.fide_rent.data.RentDao
import com.example.fide_rent.model.Rent
import com.example.fide_rent.repository.RentRepository

class CheckOutViewModel(application: Application): AndroidViewModel(application) {

    private val repository: RentRepository = RentRepository(RentDao())

    fun saveRent(rent: Rent){
        repository.saveRent(rent)
    }

}