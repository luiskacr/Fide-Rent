package com.example.fide_rent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fide_rent.customs.CustomAlerts
import com.example.fide_rent.databinding.ActivityForgotPasswordBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityForgotPasswordBinding

    val alerts: CustomAlerts = CustomAlerts()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_forgot_password)
        setContentView(binding.root)

        // No Show the Top Action Bar
        supportActionBar?.hide();

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        setup()
    }

    private fun setup(){

        binding.btnForgotPassword.setOnClickListener {
            goForgotPasseword()
        }

        binding.txtBackLogin.setOnClickListener {
            goToLogin()
        }
    }

    private fun goForgotPasseword(){
        if(validEmptyFields()){
            val email = binding.resetMail.text.toString()
            auth.sendPasswordResetEmail(email).addOnCompleteListener(this) {task->
                if(task.isSuccessful){
                    alerts.showBasicToast(getString(R.string.forgot_successful),this)
                    goToLogin()
                }else{
                    alerts.showBasicAlert(getString(R.string.error),getString(R.string.forgot_reset_error),this)
                }
            }
        }
    }


    private fun goToLogin(){
        val intent= Intent(this, loginActivity::class.java)
        startActivity(intent)
    }

    private fun validEmptyFields():Boolean {
        if(binding.resetMail.text.isEmpty()){
            alerts.showBasicAlert(getString(R.string.error),getString(R.string.forgot_error),this)
            return false
        }else{
            return true
        }
    }
}