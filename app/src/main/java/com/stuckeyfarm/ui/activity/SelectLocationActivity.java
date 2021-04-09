package com.stuckeyfarm.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stuckeyfarm.R;
import com.stuckeyfarm.Util.ConnectivityReceiver;
import com.stuckeyfarm.Util.SavePref;
import com.stuckeyfarm.Util.util;
import com.stuckeyfarm.adapter.LocationAdapter;
import com.stuckeyfarm.parser.AllStuckeyAPIS;
import com.stuckeyfarm.parser.GetAsyncGetLatest;
import com.stuckeyfarm.pojo.Data;
import com.stuckeyfarm.utills.MySharedPreference;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectLocationActivity extends AppCompatActivity implements View.OnClickListener {

    SelectLocationActivity context;
    private TextView text_save;
    private ImageView img_back;
    private RelativeLayout ll_root;
    private RecyclerView recycleView_location;
    public LocationAdapter locationAdapter;
    ArrayList<Data> dataArrayList;
    public String str_location_id="";
    SavePref savePref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        Iniui();

    }

    void Iniui() {

        context = this;
        savePref=new SavePref(context);
        img_back=(ImageView)findViewById(R.id.img_back);
        ll_root=(RelativeLayout)findViewById(R.id.ll_root);
        text_save = (TextView) findViewById(R.id.text_save);
        recycleView_location = (RecyclerView) findViewById(R.id.recycleView_location);

        text_save.setOnClickListener(this);
        img_back.setOnClickListener(this);


        if (ConnectivityReceiver.isConnected()) {

            GetLocationApi();



        } else {

            util.Message(context, getString(R.string.no_internet), 1);

        }

    }

    private void GetLocationApi() {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        GetAsyncGetLatest mAsync = new GetAsyncGetLatest(context, AllStuckeyAPIS.GET_LOCATION) {
            @Override
            public void getValueParse(Response response) {
                progressDialog.dismiss();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String result = "";
                dataArrayList=new ArrayList<>();

                try {

                 result = response.body().string();

                } catch (Exception e) {

                    e.printStackTrace();

                }

                Log.e("Location_response", "result " + result);

                text_save.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

                    try {

                        JSONObject object = new JSONObject(result);

                        JSONArray array = object.getJSONArray("data");

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject dataObject = array.getJSONObject(i);

                            Data data = new Data();

                            data.setId(dataObject.getString("id"));

                            data.setLocation_name(dataObject.getString("location_name"));

                            dataArrayList.add(data);

                           }

                        LocationAdapter locationAdapter = new LocationAdapter(context,dataArrayList, new LocationAdapter.SetOnitemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                str_location_id=dataArrayList.get(position).id;


                            }
                        });

                        recycleView_location.setLayoutManager(new LinearLayoutManager(context));
                        recycleView_location.setAdapter(locationAdapter);

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
            case R.id.img_back:
                finish();
                break;

            case R.id.text_save:

                MySharedPreference mySharedPreference=new MySharedPreference(this);
                mySharedPreference.putString("str_location_id",str_location_id);

                Intent intent=new Intent(SelectLocationActivity.this,HomeActivity.class);
                startActivity(intent);

                break;


        }
    }
}
