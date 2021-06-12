package com.example.graduationapp.ui.paymentsummary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.create_order.CreateOrderViewModel
import com.example.graduationapp.data.CreatedOrder
import com.example.graduationapp.data.LineItems
import com.example.graduationapp.data.Order
import com.example.graduationapp.data.Transactions
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.example.graduationapp.databinding.ActivityPaymentSummaryBinding
import com.example.graduationapp.ui.cart.CartActivity
import com.example.graduationapp.ui.checkoutAddress.CustomerDataActivity
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams

class PaymentSummary : AppCompatActivity() {
    lateinit var binding: ActivityPaymentSummaryBinding
    private lateinit var createOrderViewModel: CreateOrderViewModel
    var createOrderLiveData = MutableLiveData<Orders?>()
    var credit =false
    var price=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSummaryBinding.inflate(layoutInflater)
        createOrderViewModel = ViewModelProvider(this).get(CreateOrderViewModel::class.java)

        setContentView(binding.root)

        val intent=intent
        var province=""
        var phone=""
        var address1=""

        if (intent!=null){
            province= intent.getStringExtra("province").toString()
            phone= intent.getStringExtra("phone").toString()
            address1= intent.getStringExtra("address1").toString()
            price= intent.getStringExtra("price").toString()

        }

        createOrderViewModel.orders?.observe(this, Observer {
            it?.let {
                var count = price
                Log.d("tag","count"+count)
                val email = SharedPref.getUserEmail().toString()
                val listOfOrder = createOrderApi(it)
                if (credit==false){
                createOrderViewModel.createOrder(CreatedOrder(Order(email,null,count,listOfOrder,null)))
                Log.d("tag","list"+listOfOrder)
                }else{
                    var trans: MutableList<Transactions> = mutableListOf<Transactions>()
                    trans.add(Transactions("sale","success",count.toDouble()))
                    createOrderViewModel.createOrder(CreatedOrder(Order(email,null,count,listOfOrder,trans)))
                }

            }
        })
        createOrderViewModel.createOrderLiveData.observe(this, Observer{
            it?.let {
                credit=false
                createOrderViewModel.deleteListFromCart(SharedPref.getUserID().toString())
                orderDone()
            }
        })

        binding.phone.text= phone
        binding.address.text=address1
        binding.province.text = province
        binding.tvPrice.text= price+" EGP"

        binding.fabContinue.setOnClickListener{
            if (binding.cash.isChecked){
                createOrderViewModel.getAllOrderd(SharedPref.getUserID().toString())

            }else{
                goPayTab()

            }
        }

        binding.applyDiscount.setOnClickListener {
            if (SharedPref.getUserDiscount() != 0L) {

                price = ((price.toDouble())*.9).toString()
                binding.tvPrice.text=price
                binding.applyDiscount.visibility= View.GONE

            } else {

                noDiscountFound()
            }
        }
        binding.back.setOnClickListener({
            finish()
        })


    }

    private fun createOrderApi(list:List<Favorite>) : List<LineItems> {
        val lines : MutableList<LineItems> = mutableListOf<LineItems>()
        for (item in list){
            val lineObject : LineItems = LineItems(item.title,item.price.toString(),item.count,item.varient_id)
            lines.add(lineObject)
        }
        return lines
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
        intent.putExtra(PaymentParams.AMOUNT, price.toDouble())



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
        intent.putExtra(PaymentParams.POSTAL_CODE_BILLING,"00973") //Put Country Phone code if Postal code not available '00973'

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
            when(PaymentParams.RESPONSE_CODE as? Int){
                100 -> {
                    credit=true
                    createOrderViewModel.getAllOrderd(SharedPref.getUserID().toString())
                }
                else ->{
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

        }

    }
    private fun orderDone() {
        val orderDialogBuilder = AlertDialog.Builder(this)
        orderDialogBuilder.setTitle(this.getString(R.string.order))
        orderDialogBuilder.setMessage(this.getString(R.string.order_created))
        orderDialogBuilder.setPositiveButton(this.getString(R.string.ok)) { dialog, which ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
            finish()
        }
        orderDialogBuilder.setCancelable(false)
        orderDialogBuilder.show()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun noDiscountFound() {
        val orderDialogBuilder = AlertDialog.Builder(this)
        orderDialogBuilder.setTitle(this.getString(R.string.discount))
        orderDialogBuilder.setMessage(this.getString(R.string.nocode))
        orderDialogBuilder.setPositiveButton(this.getString(R.string.ok)) { dialog, which ->
            dialog.dismiss()
        }
        orderDialogBuilder.setCancelable(false)
        orderDialogBuilder.show()
    }

}