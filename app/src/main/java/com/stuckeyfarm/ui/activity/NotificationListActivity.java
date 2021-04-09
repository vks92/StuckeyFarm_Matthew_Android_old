package com.stuckeyfarm.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.StrictMode;
import android.os.Bundle;
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
import com.stuckeyfarm.Util.SavePref;
import com.stuckeyfarm.adapter.LocationAdapter;
import com.stuckeyfarm.adapter.NotificationListAdapter;
import com.stuckeyfarm.parser.AllStuckeyAPIS;
import com.stuckeyfarm.parser.GetAsyncGetLatest;
import com.stuckeyfarm.pojo.Data;
import com.stuckeyfarm.pojo.MypicModel;
import com.stuckeyfarm.utills.MySharedPreference;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationListActivity extends AppCompatActivity implements View.OnClickListener {

    NotificationListActivity context;
    NotificationListAdapter notificationListAdapter;
    RecyclerView recycleView_notification;
    RelativeLayout ll_root;
    MySharedPreference mySharedPreference;
    String bgColor,lbColor,textolor;
    TextView textm;
    SavePref savePref;
    ArrayList<MypicModel>mypicModelArrayList;
    ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        INiui();

    }

    void INiui() {

        context = this;
        savePref=new SavePref(context);
        img_back=findViewById(R.id.img_back);
        mySharedPreference=new MySharedPreference(context);
        ll_root=(RelativeLayout)findViewById(R.id.ll_root);
        textm=(TextView)findViewById(R.id.textm);
        img_back.setOnClickListener(this);

        recycleView_notification = (RecyclerView) findViewById(R.id.recycleView_notification);


        mySharedPreference.getBgColor();
        bgColor= mySharedPreference.getBgColor();
        lbColor= mySharedPreference.getLableColor();
        textolor= mySharedPreference.getTextColor();
        ll_root.setBackgroundColor(Color.parseColor(bgColor));
        textm.setTextColor( Color.parseColor(textolor));

        PickListApi();

    }

    private void PickListApi() {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        GetAsyncGetLatest mAsync = new GetAsyncGetLatest(context, AllStuckeyAPIS.PICK_LIST) {
            @Override
            public void getValueParse(Response response) {
                progressDialog.dismiss();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String result = "";
                mypicModelArrayList=new ArrayList<>();
                try {
                    result = response.body().string();

                } catch (Exception e) {

                    e.printStackTrace();

                }

                Log.e("pick_response", "result " + result);


                if (response.isSuccessful()) {

                    try {

                        JSONObject object = new JSONObject(result);

                        JSONArray array = object.getJSONArray("response");

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject dataObject = array.getJSONObject(i);

                            MypicModel mypicModel = new MypicModel();

                            mypicModel.setId(dataObject.getString("id"));

                            mypicModel.setCategory_id(dataObject.getString("category_id"));
                            mypicModel.setLocation_id(dataObject.getString("location_id"));
                            mypicModel.setName(dataObject.getString("name"));

                            mypicModelArrayList.add(mypicModel);

                        }
                        NotificationListAdapter notificationListAdapter = new NotificationListAdapter(context,mypicModelArrayList);
                        recycleView_notification.setLayoutManager(new LinearLayoutManager(context));
                        recycleView_notification.setAdapter(notificationListAdapter);

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
        }
    }
}
