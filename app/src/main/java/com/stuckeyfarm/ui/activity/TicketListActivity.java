package com.stuckeyfarm.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.stuckeyfarm.R;
import com.stuckeyfarm.adapter.TicketListAdapter;
import com.stuckeyfarm.parser.AllStuckeyAPIS;
import com.stuckeyfarm.parser.GetAsyncGetLatest;
import com.stuckeyfarm.pojo.Ticket_model;
import com.stuckeyfarm.utills.MySharedPreference;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TicketListActivity extends AppCompatActivity implements View.OnClickListener {

    TicketListActivity context;
    MySharedPreference mySharedPreference;
    String bgColor, lbColor, textolor;
    LinearLayout ll_root;
    RecyclerView recycleView;
    ArrayList<Ticket_model> ticket_modelArrayList;
    ImageView imgBackTicket;
    TextView tx_no_data;
    RelativeLayout relative_background;
    String android_id = "";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        Iniui();

    }

    void Iniui() {

        context = this;
        mySharedPreference = new MySharedPreference(context);
        mySharedPreference.getBgColor();
        bgColor = mySharedPreference.getBgColor();
        lbColor = mySharedPreference.getLableColor();
        textolor = mySharedPreference.getTextColor();
        relative_background = findViewById(R.id.relative_background);
        tx_no_data = findViewById(R.id.tx_no_data);
        imgBackTicket =findViewById(R.id.imgBackTicket);
        ll_root =findViewById(R.id.ll_root);
        ll_root.setBackgroundColor(Color.parseColor(mySharedPreference.getBgColor()));

        recycleView = (RecyclerView) findViewById(R.id.recycleView);

        imgBackTicket.setOnClickListener(this);

        Ticket_by_id_Api();

    }

    private void Ticket_by_id_Api() {

        dialog = new ProgressDialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
        dialog.setContentView(R.layout.my_progress);
        String mobile_id = "?mobile_id=" + mySharedPreference.getIMEI_NO();
        GetAsyncGetLatest mAsync = new GetAsyncGetLatest(context, AllStuckeyAPIS.TICKET_BY_ID + mobile_id) {
            @Override
            public void getValueParse(Response response) {
                dialog.dismiss();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String result = "";

                ticket_modelArrayList = new ArrayList<>();

                try {

                    result = response.body().string();

                } catch (Exception e) {
                    e.printStackTrace();

                }

                Log.e("ticket_by_id_response", "result " + result);

                if (response.isSuccessful()) {

                    try {

                        JSONObject object = new JSONObject(result);

                        JSONArray array = object.getJSONArray("response");

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject dataObject = array.getJSONObject(i);

                            Ticket_model ticket_model = new Ticket_model();

                            ticket_model.setUnique_id(dataObject.getString("unique_id"));
                            ticket_model.setTicketCount(dataObject.getString("quantity"));
                            ticket_model.setBarcodeImage(dataObject.getString("barcode_image"));

                            JSONObject obj_event=dataObject.getJSONObject("event_info");

                            ticket_model.setTitle(obj_event.getString("name"));
                            ticket_model.setDiscription(obj_event.getString("description"));
                            ticket_model.setTitle(obj_event.getString("title"));
                            ticket_model.setFirst_date(obj_event.getString("date_to"));
                            ticket_model.setLast_date(obj_event.getString("date_from"));

                            JSONObject obj_ticket_info=dataObject.getJSONObject("ticket_info");

                            ticket_model.setPrice(obj_ticket_info.getString("price"));


                            ticket_modelArrayList.add(ticket_model);
                            
                        }

                        String str_image = object.getString("image");
                        Glide.with(context).load(str_image).asBitmap().into(new SimpleTarget<Bitmap>(500, 700) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    relative_background.setBackground(drawable);
                                }
                            }
                        });

                        if (ticket_modelArrayList.size() > 0) {

                            TicketListAdapter ticketListAdapter = new TicketListAdapter(context,ticket_modelArrayList);
                            recycleView.setLayoutManager(new LinearLayoutManager(context));
                            recycleView.setAdapter(ticketListAdapter);
                            tx_no_data.setVisibility(View.GONE);

                        } else {

                            tx_no_data.setVisibility(View.VISIBLE);

                        }

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
            case R.id.imgBackTicket:
                finish();
                break;

        }
    }
}
