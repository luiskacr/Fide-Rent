package com.example.fide_rent.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car (
    @DocumentId
    val documentId: String,
    val name: String,
    val type: String,
    val price: String,
    val photo: String,
): Parcelable{
    constructor():
        this("","","","","")
}