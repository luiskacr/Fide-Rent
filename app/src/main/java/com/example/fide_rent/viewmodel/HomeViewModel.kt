package com.example.fide_rent.viewmodel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fide_rent.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel(application: Application): AndroidViewModel(application) {

    val user = Firebase.auth.currentUser


    private val _text = MutableLiveData<String>().apply {
        value = "A donde vamos a ir Hoy?"  //user?.displayName.toString()
    }
    val text: LiveData<String> = _text


}