package com.stuckeyfarm.Util;

import android.app.Application;
import android.content.Intent;
import com.stuckeyfarm.services.NetworkServices;


public class AppController extends Application

{

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    @Override
    public void onCreate()

    {

        super.onCreate();

        mInstance = this;
        Intent intent= new Intent(mInstance, NetworkServices.class);
        mInstance.startService(intent);

    }

    public static synchronized AppController getInstance()
    {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener)
    {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}