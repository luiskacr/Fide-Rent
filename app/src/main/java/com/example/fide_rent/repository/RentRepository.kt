package com.example.fide_rent.repository

import androidx.lifecycle.MutableLiveData
import com.example.fide_rent.data.RentDao
import com.example.fide_rent.model.Rent

class RentRepository(private val rentDao: RentDao) {

    fun saveRent(rent: Rent){
        rentDao.saveRent(rent)
    }

    fun deleteRent(rent: Rent){
        rentDao.deleteRent(rent)
    }

    val getRents: MutableLiveData<List<Rent>> = rentDao.getRents()


}