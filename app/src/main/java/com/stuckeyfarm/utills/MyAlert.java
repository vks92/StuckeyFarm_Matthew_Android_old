package com.stuckeyfarm.utills;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.stuckeyfarm.R;


/**
 * Created by user on 15/6/17.
 */

public class MyAlert {


    public void show(Activity activity, String title, String msg){
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.my_alert_layout);
        ((TextView)dialog.findViewById(R.id.ma_title)).setText(title);
        ((TextView)dialog.findViewById(R.id.ma_msg)).setText(msg);

        dialog.findViewById(R.id.ma_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });



        dialog.show();
    }
    public void showClose(final Activity activity, String title, String msg){
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.my_alert_layout);
        ((TextView)dialog.findViewById(R.id.ma_title)).setText(title);
        ((TextView)dialog.findViewById(R.id.ma_msg)).setText(msg);

        dialog.findViewById(R.id.ma_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
                activity.finish();
            }
        });



        dialog.show();
    }

    public void show(final Activity activity, String title, String msg, final Intent startIntent){
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.my_alert_layout);
        ((TextView)dialog.findViewById(R.id.ma_title)).setText(title);
        ((TextView)dialog.findViewById(R.id.ma_msg)).setText(msg);

        dialog.findViewById(R.id.ma_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(startIntent);
                dialog.dismiss();
            }
        });



        dialog.show();
    }
    public void showFinsh(final Activity activity, String title, String msg, final Intent startIntent){
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.my_alert_layout);
        ((TextView)dialog.findViewById(R.id.ma_title)).setText(title);
        ((TextView)dialog.findViewById(R.id.ma_msg)).setText(msg);

        dialog.findViewById(R.id.ma_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(startIntent);
                activity.finish();
                dialog.dismiss();
            }
        });



        dialog.show();
    }
    public void showFinshAll(final Activity activity, String title, String msg, final Intent startIntent){
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.my_alert_layout);
        ((TextView)dialog.findViewById(R.id.ma_title)).setText(title);
        ((TextView)dialog.findViewById(R.id.ma_msg)).setText(msg);

        dialog.findViewById(R.id.ma_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(startIntent);
                activity.finishAffinity();
                dialog.dismiss();
            }
        });


        dialog.show();




    }







}
