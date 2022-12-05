package com.example.fide_rent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel : ViewModel() {

    val user = Firebase.auth.currentUser

    private val _text = MutableLiveData<String>().apply {
        value = user?.displayName.toString()
    }
    val text: LiveData<String> = _text


}