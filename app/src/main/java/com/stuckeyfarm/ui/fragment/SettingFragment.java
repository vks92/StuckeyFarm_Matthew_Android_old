package com.stuckeyfarm.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.stuckeyfarm.R;
import com.stuckeyfarm.ui.activity.NotificationListActivity;
import com.stuckeyfarm.utills.MySharedPreference;

public class SettingFragment extends Fragment implements View.OnClickListener {

    View view;
    SettingFragment context;
    RelativeLayout re_noti;
    RelativeLayout ll_root;
    MySharedPreference mySharedPreference;
    String bgColor,lbColor,textolor;
    TextView text_notification;
    View view01,view02;
    ImageView ll_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_setting, container, false);

        INiui();
        Onclick();
        return view;

    }

    private void INiui() {

        context=this;
        ll_back=view.findViewById(R.id.ll_back);
        view01=view.findViewById(R.id.view01);
        view02=view.findViewById(R.id.view02);
        re_noti=view.findViewById(R.id.re_noti);
        ll_back.setOnClickListener(this);
        text_notification=view.findViewById(R.id.text_notification);
        mySharedPreference=new MySharedPreference(getContext());
        ll_root=(RelativeLayout)view.findViewById(R.id.ll_root);

        mySharedPreference.getBgColor();
        bgColor= mySharedPreference.getBgColor();
        lbColor= mySharedPreference.getLableColor();
        textolor= mySharedPreference.getTextColor();

        ll_root.setBackgroundColor(Color.parseColor(bgColor));
        text_notification.setTextColor(Color.parseColor(textolor));
        view01.setBackgroundColor(Color.parseColor(lbColor));
        view02.setBackgroundColor(Color.parseColor(lbColor));

    }

    private void Onclick() {

        re_noti.setOnClickListener(this);
        ll_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_noti:
                Intent i = new Intent(getActivity(), NotificationListActivity.class);
                startActivity(i);
                break;
            case R.id.ll_back:
                getFragmentManager().popBackStack();
                break;

        }
    }
}