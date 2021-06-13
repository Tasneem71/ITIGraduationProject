package com.example.graduationapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.CheckBoxPreference
import com.example.graduationapp.welcome.WelcomActivity
import kotlinx.coroutines.*
import java.util.*

class SplashActivity : AppCompatActivity() {
    val activityScope = CoroutineScope(Dispatchers.Main)


    override fun onStart() {
        darkMode()
        language()
        super.onStart()

    }
    private fun darkMode() {
        SharedPref.createPrefObject(this)
        when(SharedPref.getNightMode()){
            true->    {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false->    {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
    private fun setLocale(lng: String) {
        val locale = Locale(lng)
        Locale.setDefault(locale)
        SharedPref.setLanguage(lng)
        val config = Configuration()
        //config.locales=locale
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    private fun language() {
        SharedPref.createPrefObject(this)
        when(SharedPref.getLanguage()){
            "en"->    {
                setLocale("en")
            }
            "ar"->    {
                setLocale("ar")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SharedPref.createPrefObject(this)
        val exceptionHandlerException = CoroutineExceptionHandler { _, throwable ->throwable.printStackTrace()
            Log.i("tasneem","exption")
        }
        activityScope.launch {
            delay(4000)
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