package com.stuckeyfarm.ui.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stuckeyfarm.R;
import com.stuckeyfarm.adapter.NotificationListAdapter;
import com.stuckeyfarm.adapter.PreorderAdapter;
import com.stuckeyfarm.parser.AllStuckeyAPIS;
import com.stuckeyfarm.parser.GetAsyncGetLatest;
import com.stuckeyfarm.pojo.MypicModel;
import com.stuckeyfarm.pojo.Preorder_model;
import com.stuckeyfarm.ui.activity.TicketListActivity;
import com.stuckeyfarm.utills.MySharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Response;

public class PreorderFragment extends Fragment implements View.OnClickListener {

    View view;
    PreorderFragment context;
    ImageView imgBack,img_bg,imgTicket;
    RecyclerView re_preorder;
    ArrayList<Preorder_model> preorder_modelArrayList = new ArrayList<>();
    PreorderAdapter preorderAdapter;
    ProgressDialog dialog;
    String str_img="";
    TextView txt_no_data;
    MySharedPreference mySharedPreference;
    String bgColor,lbColor,textolor;
    RelativeLayout ll_root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_preorder, container, false);

        INiui();
        Onclick();
        return view;

    }

    private void INiui() {

        context=this;
        txt_no_data=view.findViewById(R.id.txt_no_data);
        imgBack=view.findViewById(R.id.imgBack);
        imgTicket=view.findViewById(R.id.imgTicket);
        img_bg=view.findViewById(R.id.img_bg);
        re_preorder=view.findViewById(R.id.re_preorder);
        mySharedPreference=new MySharedPreference(getContext());
        ll_root=(RelativeLayout)view.findViewById(R.id.ll_root);

        mySharedPreference.getBgColor();
        bgColor= mySharedPreference.getBgColor();
        lbColor= mySharedPreference.getLableColor();
        textolor= mySharedPreference.getTextColor();

        ll_root.setBackgroundColor(Color.parseColor(bgColor));


        PickListApi();

    }

    private void Onclick() {

        imgBack.setOnClickListener(this);
     //   imgTicket.setOnClickListener(this);

    }

    private void PickListApi() {

        dialog = new ProgressDialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
        dialog.setContentView(R.layout.my_progress);
        GetAsyncGetLatest mAsync = new GetAsyncGetLatest(getContext(), AllStuckeyAPIS.GET_PRODUCT) {
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

                Log.e("pick_response", "result " + result);

                if (response.isSuccessful()) {

                    try {

                        JSONObject object = new JSONObject(result);

                        JSONArray array = object.getJSONArray("response");

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject dataObject = array.getJSONObject(i);

                            Preorder_model preorder_model = new Preorder_model();

                            preorder_model.setId(dataObject.getString("id"));

                            preorder_model.setTitle(dataObject.getString("title"));
                            preorder_model.setDescription(dataObject.getString("description"));
                            preorder_model.setName(dataObject.getString("name"));
                            preorder_model.setImage(dataObject.getString("image"));

                            preorder_modelArrayList.add(preorder_model);

                        }

                        String str_img=object.getString("image");

                        Glide.with(getContext()).load(str_img).into(img_bg);

                        if (preorder_modelArrayList.size()>0){

                        re_preorder.setLayoutManager(new LinearLayoutManager(getContext()));
                        PreorderAdapter preorderAdapter=new PreorderAdapter(getActivity(),preorder_modelArrayList);
                        re_preorder.setAdapter(preorderAdapter);

                        txt_no_data.setVisibility(View.GONE);

                        } else {

                            txt_no_data.setVisibility(View.VISIBLE);

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
        switch (v.getId()){
            case R.id.imgBack:{
                getFragmentManager().popBackStack();
            }
//            case R.id.imgTicket:{
//                Intent intent=new Intent(getContext(), TicketListActivity.class);
//                startActivity(intent);
//            }
        }
    }
}