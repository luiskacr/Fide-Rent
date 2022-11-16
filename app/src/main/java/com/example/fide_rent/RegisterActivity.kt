package com.example.fide_rent


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fide_rent.customs.CustomAlerts
import com.example.fide_rent.databinding.ActivityRegisterBinding
import com.example.fide_rent.model.loginModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding



    val alerts: CustomAlerts = CustomAlerts()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth


        setup()
    }

    private fun setup(){

        binding.btnRegister.setOnClickListener {
            if(validateFields()){
                createNewAccount()
            }
        }
        binding.txtRegisterBack.setOnClickListener {
            goToLogin()
        }
    }

    private fun createNewAccount(){
        val name = binding.inputName.text.toString()
        val lastName = binding.inputLastname.text.toString()
        val mail = binding.inputMail.text.toString()
        val password = binding.inputLoginPassword.text.toString()

        auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener (this){ task ->
            if(task.isSuccessful){
                val user = auth.currentUser
                var userProfile: UserProfileChangeRequest = UserProfileChangeRequest.Builder().apply {
                    displayName = (name + " " + lastName)
                }.build()

                user?.updateProfile(userProfile)

                goHome(user)
            }else{
                alerts.showBasicAlert(getString(R.string.error),getString(R.string.error_register),this)
                goHome(null)
            }

        }

    }

    private fun goHome(user: FirebaseUser?) {
        if (user!=null) {
            //Go to the home
            val intent= Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToLogin(){
        val intent= Intent(this, loginActivity::class.java)
        startActivity(intent)
    }

    private fun validateFields():Boolean{

        //Validate the Name Field
        if(binding.inputName.text.isEmpty()){

            alerts.showBasicAlert(
                getString(R.string.error),
                getString(R.string.error_register_empty,getString(R.string.register_name)),
                this)

            binding.inputName.requestFocus()
            return false
        }

        //Validate the Last Name Field
        if(binding.inputLastname.text.isEmpty()){

            alerts.showBasicAlert(
                getString(R.string.error),
                getString(R.string.error_register_empty,getString(R.string.register_lastname)),
                this)

            binding.inputName.requestFocus()
            return false
        }

        //Validate the Mail Field
        if(binding.inputMail.text.isEmpty()){

            alerts.showBasicAlert(
                getString(R.string.error),
                getString(R.string.error_register_empty,getString(R.string.register_mail)),
                this)

            binding.inputMail.requestFocus()
            return false
        }

        //Validate the Password Field
        if(binding.inputLoginPassword.text.isEmpty() ){

            alerts.showBasicAlert(
                getString(R.string.error),
                getString(R.string.error_register_empty,getString(R.string.register_password)),
                this)

            binding.inputLoginPassword.requestFocus()
            return false
        }

        //Validate the Confirm Password Field
        if(binding.inputConfirmPass.text.isEmpty() ){

            alerts.showBasicAlert(
                getString(R.string.error),
                getString(R.string.error_register_empty,getString(R.string.register_confirm)),
                this)

            binding.inputConfirmPass.requestFocus()
            return false
        }

        //Validate that the password field and the confirmation field are the same.
        val password = binding.inputLoginPassword.text.toString()
        val confirmPassword = binding.inputConfirmPass.text.toString()
        if(password != confirmPassword){
            alerts.showBasicAlert(
                getString(R.string.error),
                getString(R.string.error_register_password),
                this)

            binding.inputConfirmPass.requestFocus()
            return false
        }

        if(binding.inputLoginPassword.text.length < 6){

            alerts.showBasicAlert(
                getString(R.string.error),
                getString(R.string.error_register_password_lent),
                this)

            binding.inputLoginPassword.requestFocus()
            return false
        }

        return true
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        goHome(user)
    }

}