package com.example.graduationapp.ui.login

import android.app.ActivityOptions
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.ui.Registration.RegistrationActivity
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.CreatedCustomer
import com.example.graduationapp.data.Customer
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import java.util.*


open class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding

    private lateinit var loginViewMode : LoginViewModel

    var progressDialog: ProgressDialog? = null
    var fAuth: FirebaseAuth? = null
    companion object{
        var mGoogleSignInClient: GoogleSignInClient? = null
        var account: GoogleSignInAccount? = null
    }
    var RC_SIGN_IN = 10
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewMode = ViewModelProvider(this).get(LoginViewModel::class.java)

        fAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Signing In please wait...")
        SharedPref.createPrefObject(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.googleSignInButton.setSize(SignInButton.SIZE_STANDARD)
        binding.googleSignInButton.setOnClickListener({ signIn() })



        FacebookSdk.sdkInitialize(getApplicationContext())
        callbackManager = CallbackManager.Factory.create()



        binding.loginButton.setOnClickListener {

            LoginManager.getInstance().logInWithReadPermissions(
                this@LoginActivity,
                Arrays.asList("public_profile", "email")
            )
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {

                        val request = GraphRequest.newMeRequest(
                            loginResult.accessToken
                        ) { `object`, response ->
                            Log.d("LoginActivity", response.toString())

                            val email = `object`.getString("email")
                            val fname = `object`.getString("first_name")
                            val lname = `object`.getString("last_name")

                            signInApi(email, fname, lname)
                            Log.d("LoginActivity", email)


                        }
                        val parameters = Bundle()
                        parameters.putString(
                            "fields",
                            "id,name,email,first_name,last_name,gender,birthday"
                        )
                        request.parameters = parameters
                        request.executeAsync()
                        SharedPref.setLogin(true)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        Log.d("LoginActivity", "Facebook Data."+ parameters)

                    }

                    override fun onCancel() {
                        Log.d("LoginActivity", "Facebook onCancel.")
                    }

                    override fun onError(error: FacebookException) {
                        Log.d("LoginActivity", "Facebook onError.")
                    }
                })
        }
        binding.btRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            val pairs =Pair<View, String>(binding.tvLogin, "tvLogin")
            val activityOptions =ActivityOptions.makeSceneTransitionAnimation(this, pairs)
            startActivity(intent, activityOptions.toBundle())
        }
        binding.loginBtn.setOnClickListener {
            loginViewMode.validate_login(
                binding.emailEdt.text.toString(),
                binding.passwordEdt.text.toString()
            )
        }

        loginViewMode.customerLiveData.observe(this) {
            it?.let {
                settingSharedPrefs(it.email, it.id, it.first_name)
            }
        }

    }
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient!!.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val personName = account.displayName
            val personEmail = account.email
            val personGivenName = account.givenName
            val personFamilyName = account.familyName
            val personId = account.id
            val personPhoto = account.photoUrl
            if (personEmail!=null&&personName!=null&&personFamilyName!=null){
                Log.i("tasneem", "nothing is null")
                signInApi(personEmail, personName, personFamilyName)
            }else{
                Log.i("tasneem", "something is null")
            }

           Log.d("user", personName + personEmail + personId)

        } else {
            Toast.makeText(this, "Please login with a valid Google account", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun signInApi(email: String, fname: String, lname: String) {
        loginViewMode.loadData(applicationContext)
        loginViewMode.allCustomersLiveData.observe(this) {

            val exist= it?.customers?.filter { it.email==email }
            if (!exist.isNullOrEmpty()){
                Log.i("tasneem", "in the if sign in")
                settingSharedPrefs(email, exist[0].id, fname)
            }else{
                Log.i("tasneem", "in the else sign in")
                //var list :List<Addresses> = mutableListOf<Addresses>(Addresses("","","","+201112518611","","","",""))
                val part = Customer(fname, lname, email, null, null, true, null, null, null, null)
                val costomerJsonc=CreatedCustomer(part)
                loginViewMode.createCustomer(costomerJsonc)
                loginViewMode.createCustomerLiveData.observe(this) {
                    Log.i("tasneem", "" + it)
                    it?.let {
                        settingSharedPrefs(email, it.id, fname)
                    }

                }

            }

        }
    }

    private fun settingSharedPrefs(email: String, id: String, fname: String){
        val intent = Intent(this, MainActivity::class.java)
        SharedPref.setUserEmail(email)
        SharedPref.setUserId(id)
        SharedPref.setUserInfo(fname)
        SharedPref.setUserState(true)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        callbackManager!!.onActivityResult(requestCode, resultCode, data);
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            updateUI(account)
        }
    }
    override fun onStart() {
        super.onStart()
        account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

}