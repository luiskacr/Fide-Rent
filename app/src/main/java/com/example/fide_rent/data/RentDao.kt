package com.example.fide_rent.data


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fide_rent.model.Rent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RentDao {

    private var userCoder:String
    private var firestore: FirebaseFirestore

    init{
        userCoder = Firebase.auth.currentUser?.email.toString()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun saveRent(rent: Rent){
        val document: DocumentReference
        if(rent.documentId.isEmpty()){
            document = firestore
                .collection("Rents")
                .document(userCoder)
                .collection("MyRents")
                .document()

            rent.documentId = document.id
        }else{
            document = firestore
                .collection("Rents")
                .document(userCoder)
                .collection("MyRents")
                .document(rent.documentId)
        }

        document.set(rent).addOnCompleteListener {
            Log.e("CreateRent","Creada con Exito")
        }.addOnCanceledListener {
            Log.e("CreateRent","Error al crear")
        }
    }

    fun deleteRent(rent: Rent){
        if(rent.documentId.isNotEmpty()){
            firestore
                .collection("Rents")
                .document(userCoder)
                .collection("MyRents")
                .document(rent.documentId)
                .delete()
                .addOnCompleteListener {

                }.addOnCanceledListener {

                }
        }
    }

    fun getRents(): MutableLiveData<List<Rent>>{
        val rentList = MutableLiveData<List<Rent>>()
        firestore
            .collection("Rents")
            .document(userCoder)
            .collection("MyRents")
            .addSnapshotListener{snapshot,e ->
                if(e != null){
                    return@addSnapshotListener
                }
                if(snapshot != null){
                    val list = ArrayList<Rent>()
                    val rents = snapshot.documents
                    rents.forEach {
                        val rent = it.toObject(Rent::class.java)
                        if(rent != null){
                            list.add(rent)
                        }
                    }
                    rentList.value = list
                }
            }

        return rentList
    }

}