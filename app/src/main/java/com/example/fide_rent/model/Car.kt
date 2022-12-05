package com.example.fide_rent.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car (
    val id: String,
    val name: String,
    val type: String,
    val price: String,
    val photo: String,
): Parcelable{
    constructor():
        this("","","","","")
}