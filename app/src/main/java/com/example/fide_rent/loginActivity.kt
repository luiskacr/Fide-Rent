package com.example.fide_rent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fide_rent.customs.CustomAlerts
import com.example.fide_rent.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class loginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInCliente: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding

    val alerts: CustomAlerts = CustomAlerts()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_login)
        setContentView(binding.root)

        // No Show the Top Action Bar
        supportActionBar?.hide();

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        //Google Sign
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().requestProfile().build()

        googleSignInCliente = GoogleSignIn.getClient(this,gso)

        setup()
    }

    private fun setup(){
        //Login Button
        binding.btnLogin.setOnClickListener {
            doLogin()
        }

        //Register Button
        binding.btnRegisterView.setOnClickListener {
            goRegister()
        }

        binding.btnGoogle.setOnClickListener {
            goSignInGoogle()
        }
    }

    private fun doLogin(){
        if(validEmptyFields()){
            var email = binding.inputEmail.text.toString()
            var password = binding.inputLoginPassword.text.toString()

            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        goHome(user)
                    }else{
                        alerts.showBasicToast(getString(R.string.error_bad_login),this)
                        goHome(null)
                    }
                }

        }
    }

    /*
    Function to validate that the user or password fields are not empty.
    */
    private fun validEmptyFields(): Boolean{
        if( binding.inputEmail.text.isNotEmpty() && binding.inputLoginPassword.text.isNotEmpty() ){
            return true
        }else{
            alerts.showBasicAlert(getString(R.string.error),getString(R.string.error_empty_fields),this)
            return false
        }
    }

    private fun goHome(user: FirebaseUser?){
        if (user!=null) {
            //Go to the home
            val intent= Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    private fun goRegister(){
        val registerIntent: Intent = Intent(this,RegisterActivity::class.java).apply {

        }
        startActivity(registerIntent)
    }


    private fun goSignInGoogle(){
        val signInIntent = googleSignInCliente.signInIntent

        launcher.launch(signInIntent)

    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            alerts.showBasicToast(task.exception.toString(),this)
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val intent : Intent = Intent(this , HomeActivity::class.java)
                intent.putExtra("email" , account.email)
                intent.putExtra("name" , account.displayName)
                startActivity(intent)
            }else{
                alerts.showBasicToast(it.exception.toString(),this)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        goHome(user)
    }

}