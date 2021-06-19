package com.example.graduationapp.ui.settingsFeature

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.preference.*
import com.example.graduationapp.MainActivity
import com.example.graduationapp.ui.login.LoginActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.ui.addressbook.AddressBook
import com.example.graduationapp.ui.me.MeFragment
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    private var alert: AlertDialog? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        darkMode()
        language()
        aboutMe()
        logout()
        back()
        goToAddress()
    }

    private fun openDialogue() {
        val inflater = LayoutInflater.from(context)
        val inflate_view = inflater.inflate(R.layout.dialog_contact_dark, null)
        val btn_close = inflate_view.findViewById<ImageButton>(R.id.bt_close)
        val facebook =
            inflate_view.findViewById<ImageView>(R.id.facebooky)
        val gmail =
            inflate_view.findViewById<ImageView>(R.id.gmaily)
        val linkedIn =
            inflate_view.findViewById<ImageView>(R.id.linkediny)
        btn_close.setOnClickListener { v: View? ->
            alert?.dismiss() }
        facebook.setOnClickListener { v: View? -> openFacebook() }
        linkedIn.setOnClickListener { v: View? -> openLinkedIn() }
        gmail.setOnClickListener { v: View? -> sendMail() }
        val builder = AlertDialog.Builder(context)
        builder.setView(inflate_view)
        builder.setCancelable(false)
        alert = builder.create()
        alert?.show()
    }
    private fun openFacebook() {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100006851673761"));
//        startActivity(browserIntent);
        val profile_url = "https://www.facebook.com/profile.php?id=100047805403025"
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(profile_url))
            intent.setPackage("com.facebook.android")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            //if the user doesn't have a LinkedIn account, open an intent to a browser
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(profile_url)))
        }
    }

    private fun openLinkedIn() {
        val profile_url = "https://www.linkedin.com/in/mohamed-abdallah-092973b0/"
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(profile_url))
            intent.setPackage("com.linkedin.android")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            //if the user doesn't have a LinkedIn account, open an intent to a browser
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(profile_url)))
        }
    }

    private fun sendMail() {
        val recipientList = "mohamed.abdallah8882@gmail.com"
        val recipients =
            recipientList.split(",".toRegex()).toTypedArray()
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.type = "message/rfc822"
        try {
            startActivity(Intent.createChooser(intent, "Pick an Email Client !"))
        } catch (e: ActivityNotFoundException) {
            Log.i("Tag", "sendMail: error")
        }
    }
    private fun aboutMe() {
        val notification = findPreference<androidx.preference.Preference>("about")
        notification?.setOnPreferenceClickListener {
            if (it.key=="about")
            {
                openDialogue()
                Log.i("TAG", "aboutMe: ddddddddddddddddddddddddddddddddddddddddddddddd")
            }
            Log.i("TAG", "aboutMe: hjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj")

            true
        }
    }
    private fun darkMode() {

        var darkMode = findPreference<CheckBoxPreference>("background_mode")
        darkMode?.setOnPreferenceChangeListener { prefs, obj ->
            val yes = obj as Boolean
            if (yes) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SharedPref.setNightMode(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SharedPref.setNightMode(false)
            }
            true
        }
    }

    private fun language() {
        val language = findPreference<ListPreference>("language")
        language?.setOnPreferenceChangeListener { prefs, obj ->
            val items = obj as String
            if (prefs.key == "language") {
                when (items) {
                    "en" -> setLocale("en")
                    "ar" -> setLocale("ar")
                    "fr" -> setLocale("fr")
                }
                ActivityCompat.recreate(requireActivity())
            }
            true
        }
    }
    private fun back() {
        val back = findPreference<Preference>("back")
        back?.setOnPreferenceClickListener {
            if (it.key=="back") {
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
            true
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


    private fun goToAddress()
    {
        val notification = findPreference<androidx.preference.Preference>("address_book")
        notification?.setOnPreferenceClickListener {
            if (it.key=="address_book")
            {
                val intent =Intent(requireContext(),AddressBook::class.java)
                startActivity(intent)
            };true
        }
    }
    private fun logout() {

        val notification = findPreference<androidx.preference.Preference>("logout")
        notification?.setOnPreferenceClickListener {
            if (it.key=="logout")
            {
                SharedPref.setUserState(false)
                SharedPref.setLogin(false)
                SharedPref.setUserDiscount(0L)
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                if (!SharedPref.checkLoginWithFirebase()!!) {
             signOut()
             LoginActivity.account = null
         }
         if (AccessToken.getCurrentAccessToken() != null) {
             GraphRequest(
                 AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE
             ) {
                 AccessToken.setCurrentAccessToken(null)
                 LoginManager.getInstance().logOut()
             }.executeAsync()
         }

            }
            true
        }
    }
    private fun signOut() {
        LoginActivity.mGoogleSignInClient?.signOut()
    }

}