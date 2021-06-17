package com.example.graduationapp.ui.paymentsummary

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.create_order.CreateOrderViewModel
import com.example.graduationapp.create_order.CreateOrderViewModelFactory
import com.example.graduationapp.data.*
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.example.graduationapp.databinding.ActivityPaymentSummaryBinding
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.RemoteDataSource
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.addressbook.AddressBookViewModelFactory
import com.example.graduationapp.ui.cart.CartActivity
import com.example.graduationapp.ui.checkoutAddress.CustomerDataActivity
import com.example.graduationapp.ui.search.SearchViewModel
import com.example.graduationapp.ui.search.SearchViewModelFactory
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams

class PaymentSummary : AppCompatActivity() {
    lateinit var binding: ActivityPaymentSummaryBinding
    private lateinit var createOrderViewModel: CreateOrderViewModel
    var createOrderLiveData = MutableLiveData<Orders?>()
    var credit =false
    var price=""
    var discount=false
    private lateinit var userId :String
    lateinit var repository: DefaultRepo
    lateinit var local: DefaultLocal
    lateinit var remote: DefaultRemote


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSummaryBinding.inflate(layoutInflater)


        local= LocalSource(this.application)
        remote=RemoteDataSource()
        repository= ApiRepository(this.application,local,remote)

        val factory = CreateOrderViewModelFactory(this.application,repository)
        createOrderViewModel = ViewModelProviders.of(this,factory).get(CreateOrderViewModel::class.java)



        //createOrderViewModel = ViewModelProvider(this).get(CreateOrderViewModel::class.java)

        setContentView(binding.root)
        userId = SharedPref.getUserID().toString()

        val intent=intent


        if (intent!=null){
            price= intent.getStringExtra("price").toString()
            Log.i("price",price)
        }
        Log.i("Menna","address id "+SharedPref.getAddressID().toString())
        createOrderViewModel.getDefaultAddress(userId,SharedPref.getAddressID().toString())
        createOrderViewModel.getDefaultAddLifeData.observe(this, Observer {
            Log.i("Menna","default address  "+it)
            it?.let {

                binding.phone.text= it.address?.phone ?: ""
                binding.address.text=it.address?.address1 ?: ""
                binding.province.text = it.address?.province ?: ""
            }
        })
        createOrderViewModel.orders?.observe(this, Observer {
            it?.let {
                var count = price
                Log.d("tag","count"+count)
                val email = SharedPref.getUserEmail().toString()
                val listOfOrder = createOrderApi(it)
                if (credit==false && discount==false){
                createOrderViewModel.createOrder(CreatedOrder(Order(email,null,"pending",count,listOfOrder,null,null)))
                Log.d("tag","list"+listOfOrder)
                }else{
                    Log.i("discount","............................. in the else")
                    var discountList= mutableListOf<DiscountCodes>()
                    discountList.add(DiscountCodes(SharedPref.getUserDiscount().toString(),10.00,"percentage"))
                    createOrderViewModel.createOrder(CreatedOrder(Order(email,null,"pending",count,listOfOrder,null
                        ,discountList)))
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

        binding.tvPrice.text= price+" LE"
        Log.i("price",price)
        binding.fabContinue.setOnClickListener{
            if (binding.cash.isChecked){
                createOrderViewModel.getAllOrderd(SharedPref.getUserID().toString())

            }else{

                val intent =Intent(this,CheckoutActivityJava::class.java)
                intent.putExtra("price",price)
                intent.putExtra("discount",discount)
                startActivity(intent)
               // goPayTab()

            }
        }

        binding.applyDiscount.setOnClickListener {
            if (SharedPref.getUserDiscount() != 0L) {
                binding.beforeDiscount.text =price+" LE"
                binding.beforeDiscount.paintFlags= binding.beforeDiscount.paintFlags
                price = ((price.toDouble())*.9).toString()
                binding.tvPrice.text=price+" LE"
                binding.applyDiscount.visibility= View.GONE
                binding.beforeDiscount.paintFlags = binding.beforeDiscount.paintFlags or  Paint.STRIKE_THRU_TEXT_FLAG
                binding.beforeDiscount.visibility = View.VISIBLE
                discount=true


            } else {
                binding.beforeDiscount.visibility = View.GONE
                noDiscountFound()
            }
        }
        binding.back.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }


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



        intent.putExtra(PaymentParams.CURRENCY_CODE, "LE")
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