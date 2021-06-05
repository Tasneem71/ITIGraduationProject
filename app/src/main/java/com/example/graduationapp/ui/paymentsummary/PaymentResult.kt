package com.example.graduationapp.ui.paymentsummary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduationapp.R
import com.example.graduationapp.databinding.ActivityPaymentResultBinding
import com.example.graduationapp.databinding.ActivityPaymentSummaryBinding

class PaymentResult : AppCompatActivity() {

    lateinit var binding: ActivityPaymentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent: Intent = getIntent()

        var response_code = intent.getStringExtra("response_code")
        var transcation_id = intent.getStringExtra("transcation_id")
        var customer_email = intent.getStringExtra("customer_email")
        var token = intent.getStringExtra("token")
        var customer_password = intent.getStringExtra("customer_password")


        binding.textView.setText(
            "Response code : " + response_code + "\n" + "Transcation Id : " + transcation_id +
                    "\n" + "Customer Email : " + customer_email + "\n" + "Token : " + token + "\n" + "Customer Password : " + customer_password
        )
    }
}

