package com.example.graduationapp.ui.paymentsummary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.domain.core.feature.favoriteFeature.Favorite;
import com.example.graduationapp.MainActivity;
import com.example.graduationapp.R;
import com.example.graduationapp.SharedPref;
import com.example.graduationapp.create_order.CreateOrderViewModel;
import com.example.graduationapp.create_order.CreateOrderViewModelFactory;
import com.example.graduationapp.data.CreatedOrder;
import com.example.graduationapp.data.DiscountCodes;
import com.example.graduationapp.data.LineItems;
import com.example.graduationapp.data.Order;
import com.example.graduationapp.data.Transactions;
import com.example.graduationapp.local.DefaultLocal;
import com.example.graduationapp.local.LocalSource;
import com.example.graduationapp.remote.ApiRepository;
import com.example.graduationapp.remote.DefaultRemote;
import com.example.graduationapp.remote.RemoteDataSource;
import com.example.graduationapp.remote.retro.DefaultRepo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckoutActivityJava extends AppCompatActivity {
    private static final String BACKEND_URL = "https://desolate-ridge-15476.herokuapp.com/";
    private CreateOrderViewModel viewModel;
    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    private String price;
    private boolean discount;
    DefaultLocal local;
    DefaultRemote remote;
    DefaultRepo repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        local= new LocalSource(this.getApplication());
        remote= new RemoteDataSource();
        repository= new ApiRepository(this.getApplication(),local,remote);

        CreateOrderViewModelFactory factory = new CreateOrderViewModelFactory(this.getApplication(),repository);
        viewModel = ViewModelProviders.of(this,factory).get(CreateOrderViewModel.class);


        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51J1qETHm8J3ojgHQpJrwU552AqgiZLXj1PCfbZQ7XS1yMiYIn1PiJQCay46M9yLFBb6KYYtmtDBOaiK4JUai7zKw001dWm1O7T")
        );
        Intent intent=getIntent();
        if(intent!=null){
            price=intent.getStringExtra("price");
            discount=intent.getBooleanExtra("discount",false);
        }
        startCheckout();
        viewModel.getOrders().observe(this, favorites -> {
            String email = SharedPref.INSTANCE.getUserEmail();
            List<LineItems> listOfOrder = createOrderApi(favorites);
            List<Transactions> trans = new ArrayList<Transactions>();
            trans.add(new Transactions("sale","success",Double.parseDouble(price)));
            if(discount==true) {
                List<DiscountCodes> discountList= new ArrayList();
                discountList.add(new DiscountCodes(SharedPref.INSTANCE.getUserDiscount().toString(),10.00,"percentage"));
                viewModel.createOrder(new CreatedOrder(new Order(email, null, "paid", price, listOfOrder, trans, discountList)));
            }else{
                viewModel.createOrder(new CreatedOrder(new Order(email, null, "paid", price, listOfOrder, trans, null)));
            }
        });


        viewModel.getCreateOrderLiveData().observe(this, orders -> {
            viewModel.deleteListFromCart(SharedPref.INSTANCE.getUserID());
            orderDone();
        });

    }

    private void orderDone() {
        AlertDialog.Builder orderDialogBuilder =new  AlertDialog.Builder(this);
        orderDialogBuilder.setTitle(this.getString(R.string.order));
        orderDialogBuilder.setMessage(this.getString(R.string.order_created));
        orderDialogBuilder.setPositiveButton(this.getString(R.string.ok), (dialog, id) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            dialog.dismiss();
            finish();
        });
        orderDialogBuilder.setCancelable(false);
        orderDialogBuilder.show();
    }

    private List<LineItems> createOrderApi(List<Favorite> list){
        List<LineItems> lines  = new ArrayList<>();
        for (Favorite item:list) {
            LineItems lineObject  = new LineItems(item.getTitle(),""+item.getPrice(),item.getCount(),item.getVarient_id());
            lines.add(lineObject);
        }
        return lines;
    }

    private void startCheckout() {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        //Double.parseDouble()
       // double amountt = Double.parseDouble(amount)*100;
        double amountt =Double.parseDouble(price)*100;
        Log.i("help",amountt+"********");
        Map<String,Object> payMap = new HashMap<>();
        Map<String,Object> itemMap = new HashMap<>();
        List<Map<String,Object>> itemList = new ArrayList<>();
        payMap.put("currency","usd");
        itemMap.put("id","photo_subscription");
        itemMap.put("amount",amountt);
        itemList.add(itemMap);
        payMap.put("items",itemList);
        String json = new Gson().toJson(payMap);
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "create-payment-intent")
                .post(body)
                .build();
        httpClient.newCall(request)
                .enqueue(new PayCallback(this));

        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> {
            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);


            }
        });
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);

        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );

        paymentIntentClientSecret = responseMap.get("clientSecret");
    }

    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<CheckoutActivityJava> activityRef;

        PayCallback(@NonNull CheckoutActivityJava activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final CheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final CheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }

    private  final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<CheckoutActivityJava> activityRef;

        PaymentResultCallback(@NonNull CheckoutActivityJava activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final CheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {

                Toast.makeText(activity,"Ordered Successfully",Toast.LENGTH_LONG).show();
                viewModel.getAllOrderd(SharedPref.INSTANCE.getUserID());
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final CheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }


}
