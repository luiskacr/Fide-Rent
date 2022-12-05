package com.example.fide_rent.data

import androidx.lifecycle.MutableLiveData
import com.example.fide_rent.model.Car
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class CarDao {

    private var userCoder:String
    private var firestore: FirebaseFirestore

    init{
        userCoder = Firebase.auth.currentUser?.email.toString()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun getCars(): MutableLiveData<List<Car>>{
        val carList = MutableLiveData<List<Car>>()
        firestore.collection("Cars").
                addSnapshotListener{snapshot, e->
                    if(e != null){
                        return@addSnapshotListener
                    }
                    if(snapshot != null){
                        val lista = ArrayList<Car>()
                        val cars = snapshot.documents
                        cars.forEach {
                            val car = it.toObject(Car::class.java)
                            if(car !=null){
                                lista.add(car)
                            }
                        }
                        carList.value = lista
                    }
                }
        return carList
    }

}