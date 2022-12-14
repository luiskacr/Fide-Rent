package com.example.fide_rent.model


import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Rent (
    @DocumentId
    var documentId: String,
    var startDate: String,
    var endDate: String,
    var days: Int,
    var total: Int,
    val car: Car,

    ): Parcelable{
    constructor():
            this("","","",0,0,Car())
}
