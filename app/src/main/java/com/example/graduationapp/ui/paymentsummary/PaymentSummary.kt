package com.example.graduationapp.ui.paymentsummary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.graduationapp.R
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.example.graduationapp.databinding.ActivityPaymentSummaryBinding
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams

class PaymentSummary : AppCompatActivity() {
    lateinit var binding: ActivityPaymentSummaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabContinue.setOnClickListener{

            goPayTab()

        }


    }

    fun goPayTab() {
        val intent = Intent(applicationContext, PayTabActivity::class.java)
        intent.putExtra(PaymentParams.MERCHANT_EMAIL, "nasreldeenmohamed@gmail.com")
        intent.putExtra(
            PaymentParams.SECRET_KEY,
            "ewlCLeAkBRN8sYFpvzZOO795Q3wPO0r3wMGi1hCC8pLBoq0TZ0IN7RTYUqOJqfMX2tf3KRGShruFRvzRvhFC8yB3ZpMpFzmH2CLM"
        )//Add your Secret Key Here


        //  "panneerselvam.kannan@gmail.com"
        // "LuqWuKoa19SQyMF4Z2XVgBYj32OWSadDEEA9pYjq8cgGYWLYYJUfkTynVmkOxyrcc1lB0E18RiPQfBOdkJsEC12xonmVVN77PsJQ"


        intent.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH)
        intent.putExtra(PaymentParams.TRANSACTION_TITLE, "Customer Name")
        intent.putExtra(PaymentParams.AMOUNT, 199.0)



        intent.putExtra(PaymentParams.CURRENCY_CODE, "EGP")
        intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733")
        intent.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com")
        intent.putExtra(PaymentParams.ORDER_ID, "123456")
        intent.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2")

        //Billing Address
        intent.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_BILLING, "Manama")
        intent.putExtra(PaymentParams.STATE_BILLING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_BILLING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_BILLING,
            "00973"
        ) //Put Country Phone code if Postal code not available '00973'

        //Shipping Address
        intent.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.STATE_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_SHIPPING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_SHIPPING,
            "00973"
        ) //Put Country Phone code if Postal code not available '00973'

        //Payment Page Style
        intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc")

        //Tokenization
        intent.putExtra(PaymentParams.IS_TOKENIZATION, true)

        ////PreAuth
        intent.putExtra(PaymentParams.IS_PREAUTH, true);

        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            data!!.getStringExtra(PaymentParams.RESPONSE_CODE)?.let { Log.e("Tag", it) }
            data.getStringExtra(PaymentParams.TRANSACTION_ID)?.let { Log.e("Tag", it) }
            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN)!!
                    .isEmpty()
            ) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN)!!)
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL)!!)
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD)!!)
            }

            var i = Intent(MainActivity@ this, PaymentResult::class.java)

            i.putExtra("response_code", data.getStringExtra(PaymentParams.RESPONSE_CODE))

            i.putExtra("transcation_id", data.getStringExtra(PaymentParams.TRANSACTION_ID))
            i.putExtra("customer_email", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL))
            i.putExtra("token", data.getStringExtra(PaymentParams.TOKEN));
            i.putExtra("customer_password", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD))

            startActivity(i)
            finish()

        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}