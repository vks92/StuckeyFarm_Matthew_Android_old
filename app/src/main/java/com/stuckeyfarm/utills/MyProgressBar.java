package com.stuckeyfarm.utills;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import com.stuckeyfarm.R;


public class MyProgressBar {
     Dialog dialog;
    Activity activity;

    public MyProgressBar(Activity activity) {
        this.activity = activity;
    }

    public void show( ){
        if(!activity.isDestroyed()) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.my_progress_bar);
            dialog.show();
        }
    }
    public void dismiss(){
        if(!activity.isDestroyed()) {
            dialog.dismiss();
        }
    }


}
