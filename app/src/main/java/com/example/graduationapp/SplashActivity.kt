package com.example.graduationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.graduationapp.welcome.WelcomActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    val activityScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SharedPref.createPrefObject(this)
        val exceptionHandlerException = CoroutineExceptionHandler { _, throwable ->throwable.printStackTrace()
            Log.i("tasneem","exption")
        }
        activityScope.launch {
            delay(2000)
            if(SharedPref.getEver()){
            var intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            }else{
                var intent = Intent(this@SplashActivity, WelcomActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}