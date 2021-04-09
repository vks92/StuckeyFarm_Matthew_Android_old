package com.stuckeyfarm.parser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import com.stuckeyfarm.Util.Parameters;
import com.stuckeyfarm.Util.SavePref;
import com.stuckeyfarm.utills.MySharedPreference;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public abstract class GetAsyncGetLatest extends AsyncTask<Response, Void, Response> {

    String url = "";
    RequestBody requestBody;
    Context context;
    WeakReference<Context> contextWeakReference;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    SavePref savePref;
    String auth_key;
    MySharedPreference mySharedPreference;

    public GetAsyncGetLatest(Context context, String s) {
        this.url = s;
        contextWeakReference = new WeakReference<Context>(context);
        this.context = contextWeakReference.get();
        mySharedPreference=new MySharedPreference(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        savePref = new SavePref(context);

    }

    @Override
    protected Response doInBackground(Response... params) {
        Response jsonData = null;

        try {

            try {

                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(50, TimeUnit.SECONDS).writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .get()
                        .addHeader(Parameters.AUTH_KEY,savePref.getAuthorization_key())
//                        .addHeader(Parameters.ACCEPT,"application/json")
                        .url(url)
                        .build();

                try {

                    Response responses = client.newCall(request).execute();
                    jsonData = responses;

//                    jsonData = responses.body().string();

                } catch (SocketTimeoutException ex) {

                    ex.printStackTrace();

                } catch (ConnectException ex) {

                    ex.printStackTrace();

                } catch (Exception ex) {

                    ex.printStackTrace();

                }

            } catch (Exception ex) {
                ex.printStackTrace();

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return jsonData;
    }

    @Override
    protected void onPostExecute(Response result) {
        super.onPostExecute(result);
        if (result != null)

            getValueParse(result);

          else {

              showDialog();

        }
    }

    public abstract void getValueParse(Response listValue);

    public abstract void retry();
    private void showDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);

        } else {

            builder = new AlertDialog.Builder(context);

        }

        builder.setTitle("Warning!");
        builder.setMessage("internet error");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                retry();
            }
        });
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });
    }
}