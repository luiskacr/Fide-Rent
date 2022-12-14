package com.example.fide_rent.customs

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class CustomAlerts {

    val accept: String = "Aceptar"
    val cancel: String = "Cancelar"

    /*
    Function to display a basic alert
    */
    fun showBasicAlert(title: String, message: String, context: AppCompatActivity){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(accept,null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    fun showBasicToast(message: String,context: AppCompatActivity){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

    fun fragmentToast(message: String,context: Context){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}