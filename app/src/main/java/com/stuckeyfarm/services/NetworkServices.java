package com.stuckeyfarm.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.stuckeyfarm.Util.AppController;
import com.stuckeyfarm.Util.ConnectivityReceiver;
import com.stuckeyfarm.Util.SavePref;


import java.util.ArrayList;


public class NetworkServices extends Service implements ConnectivityReceiver.ConnectivityReceiverListener
{
    Context context;
    SavePref savePref;
    private int uploadCounter;
    ArrayList<String> uplaodList;
    boolean isConnect = false;

    String dataCut="";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void NetworkServices(Context context){
        this.context= context;
        savePref= new SavePref(context);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        AppController.getInstance().setConnectivityListener(this);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected, Context context) {

        this.context=context;
        this.isConnect=isConnected;

        if(isConnected)
        {

        }

    }
}
