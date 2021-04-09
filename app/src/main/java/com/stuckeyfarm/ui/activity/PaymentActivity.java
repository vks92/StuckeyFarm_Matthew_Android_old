package com.stuckeyfarm.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.squareup.moshi.Json;
import com.stuckeyfarm.R;
import com.stuckeyfarm.Util.ConnectivityReceiver;
import com.stuckeyfarm.Util.Parameters;
import com.stuckeyfarm.Util.util;
import com.stuckeyfarm.parser.AllStuckeyAPIS;
import com.stuckeyfarm.parser.GetAsyncPost;
import com.stuckeyfarm.pojo.MyTicketPojo;
import com.stuckeyfarm.utills.MySharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import sqip.Callback;
import sqip.CardEntry;
import sqip.CardEntryActivityResult;


public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    PaymentActivity context;
    private final Handler handler = new Handler(Looper.getMainLooper());
    ProgressDialog dialog;
    Integer selectedPosition;

    Integer TotalQuntity = 0;
    String TotalPrice;
    String android_id = "", email = "";
    ArrayList<MyTicketPojo> myTicketPojoArrayList = new ArrayList<>();

      String squareLocationId = "5D595DFJ84R4R";
   // String squareLocationId = "WE0BTSA3790B8";
    MySharedPreference mySharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        Iniui();

        startCardEntryActivity();


    }

    private void Iniui() {

        context = this;
        mySharedPreference = new MySharedPreference(context);
        myTicketPojoArrayList = getIntent().getParcelableArrayListExtra("list");
        selectedPosition = getIntent().getIntExtra("position", -1);

        email = getIntent().getStringExtra("email");
        TotalPrice = getIntent().getStringExtra("totalPrice");
        TotalQuntity = getIntent().getIntExtra("totalQuantity", 0);

        android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);


    }

    private void startCardEntryActivity() {

        CardEntry.startCardEntryActivity(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        CardEntry.handleActivityResult(data, new Callback<CardEntryActivityResult>() {
            @Override
            public void onResult(CardEntryActivityResult cardEntryActivityResult) {

                if (cardEntryActivityResult.isSuccess()) {

                    if (ConnectivityReceiver.isConnected()) {

                        ProcessPayment(cardEntryActivityResult.getSuccessValue().getNonce());

                    } else {

                        util.Message(context, getString(R.string.no_internet), 1);

                    }

                } else if (cardEntryActivityResult.isCanceled()) {

                    Resources res = PaymentActivity.this.getResources();

                    //  int delayMs = res.getInteger(R.integer.card_entry_activity_animation_duration_ms);
                    // handler.postDelayed(PaymentActivity.this, delayMs);

                }
            }
        });
    }

    private void ProcessPayment(String nonce) {

        String ticket_id = String.valueOf(myTicketPojoArrayList.get(selectedPosition).id);
        String quntity = String.valueOf(myTicketPojoArrayList.get(selectedPosition).quntity);

        JSONArray buyDict = new JSONArray();

        JSONObject obj=new JSONObject();

//        {\"62\":\"1\"}
//
          String json = "{"+"\\"+ticket_id+"\\"+":"+quntity+"\\"+"}";
          String json_string = "{"+"\""+ticket_id+"\":\""+quntity+"\""+"}";

        dialog = new ProgressDialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
        dialog.setContentView(R.layout.my_progress);
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add(Parameters.CARD_NONCE, nonce);
        formBuilder.add(Parameters.TOTAL_PRICE, TotalPrice);
        formBuilder.add(Parameters.EVENT_ID, String.valueOf(myTicketPojoArrayList.get(selectedPosition).event_id));
        formBuilder.add(Parameters.TICKET_INFO, json_string);
        formBuilder.add(Parameters.QUANTITY, String.valueOf(TotalQuntity));
        formBuilder.add(Parameters.EMAIL, email);
        formBuilder.add(Parameters.Loacation, squareLocationId);
        formBuilder.add(Parameters.MOBILE_ID, mySharedPreference.getIMEI_NO());
        RequestBody formBody = formBuilder.build();
        GetAsyncPost mAsync = new GetAsyncPost(context, AllStuckeyAPIS.PROCESS_payment_Square, formBody, dialog, "") {
            @Override
            public void getValueParse(Response response) {
                dialog.dismiss();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String result = "";

                try {

                    result = response.body().string();

                  } catch (Exception e) {

                    e.printStackTrace();

                }

                Log.e("process_response", "result " + result);

                if (response.isSuccessful()) {

                    try {

                        JSONObject object = new JSONObject(result);

                        util.showToast(context, object.getString("success"));

                        Intent intent = new Intent(context, TicketListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                } else {

                    try {

                        JSONObject jsonObject = new JSONObject(result);

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void retry() {

            }
        };

        mAsync.execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}