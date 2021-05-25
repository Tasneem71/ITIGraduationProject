package com.example.graduationapp

import android.app.ActivityOptions
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


open class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding

    var progressDialog: ProgressDialog? = null
    var fAuth: FirebaseAuth? = null
    companion object{
        var mGoogleSignInClient: GoogleSignInClient? = null
        var account: GoogleSignInAccount? = null
    }

    var RC_SIGN_IN = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_login)
        //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        binding.btRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            val pairs =
                Pair<View, String>(binding.tvLogin, "tvLogin")
            val activityOptions =
                ActivityOptions.makeSceneTransitionAnimation(this, pairs)
            startActivity(intent, activityOptions.toBundle())
        }
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
            val intent = Intent(this, MainActivity::class.java)
            SharedPref.setUserEmail(account.email)
            startActivity(intent)
            finish()
           Log.d("user", personName + personEmail + personId)

        } else {
            Toast.makeText(this, "Please login with a valid Google account", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
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