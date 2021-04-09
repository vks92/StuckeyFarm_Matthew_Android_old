package com.stuckeyfarm.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stuckeyfarm.R;
import com.stuckeyfarm.Util.ConnectivityReceiver;
import com.stuckeyfarm.Util.SavePref;
import com.stuckeyfarm.Util.util;
import com.stuckeyfarm.parser.AllStuckeyAPIS;
import com.stuckeyfarm.parser.GetAsyncGetLatest;
import com.stuckeyfarm.utills.MySharedPreference;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback, LocationListener {

    View view;
    LocationFragment context;
    GoogleMap map;
    SavePref savePref;
    private ImageView ll_back;
    private TextView text_diraction;
    double latitude, longitude;
    public String str_lat="",str_long="",str_id="";
    ProgressDialog dialog;
    MySharedPreference mySharedPreference;
    String bgColor,lbColor,textolor;
    RelativeLayout ll_root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        INiui();
        return view;

    }

    void INiui() {

        context = this;
        savePref = new SavePref(getContext());
        ll_root=view.findViewById(R.id.ll_root);
        ll_back=view.findViewById(R.id.ll_back);
        text_diraction = view.findViewById(R.id.text_diraction);
        text_diraction.setOnClickListener(this);
        ll_back.setOnClickListener(this);

        mySharedPreference=new MySharedPreference(getContext());
        ll_root=view.findViewById(R.id.ll_root);

        mySharedPreference.getBgColor();
        bgColor= mySharedPreference.getBgColor();
        lbColor= mySharedPreference.getLableColor();
        textolor= mySharedPreference.getTextColor();

        ll_root.setBackgroundColor(Color.parseColor(bgColor));
        text_diraction.setBackgroundColor(Color.parseColor(lbColor));
        text_diraction.setTextColor(Color.parseColor(textolor));

        startLocationListener();

        if (ConnectivityReceiver.isConnected()) {

            GetMapApi();

        } else {

            util.Message(getActivity(), getString(R.string.no_internet), 1);

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }

    @Override
    public void onLocationChanged(Location location) {

        map.clear();

        MarkerOptions mp = new MarkerOptions();

        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

        mp.title("my position");

        map.addMarker(mp);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 16));


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onProviderDisabled(String provider) {


    }

    private void startLocationListener() {

        Log.e("MainActivity", "+startLocationListener++++" + "startLocationListener");

        long mLocTrackingInterval = 1000 * 10; // 5 sec
        float trackingDistance = 0;
        LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;

        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(trackingAccuracy)
                .setDistance(trackingDistance)
                .setInterval(mLocTrackingInterval);

        SmartLocation.with(getContext())
                .location()
                .continuous()
                .config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        Log.e("MainActivity", "+latitude++++" + latitude + "+////+longitude++++++++++" + longitude);

                        LatLng latLng = new LatLng(latitude, longitude);
                        map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        map.animateCamera(CameraUpdateFactory.zoomTo(16));
                        savePref.setLat(String.valueOf(latitude));
                        savePref.setLong(String.valueOf(longitude));

                    }
        });

    }

    private void GetMapApi() {

        dialog = new ProgressDialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
        dialog.setContentView(R.layout.my_progress);
        GetAsyncGetLatest mAsync = new GetAsyncGetLatest(getContext(), AllStuckeyAPIS.GET_MAP) {
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

                Log.e("GETMAP_response", "result " + result);


                if (response.isSuccessful()) {

                    try {

                        JSONObject object = new JSONObject(result);

                        JSONObject obj_map = object.getJSONObject("data");

                         str_id = obj_map.getString("id");
                         str_lat = obj_map.getString("lat");
                         str_long = obj_map.getString("log");

//                        LatLng latLng = new LatLng(str_lat, str_long);
//                        map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
//                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                        map.animateCamera(CameraUpdateFactory.zoomTo(16));

                      }  catch (JSONException e) {
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
            case R.id.text_diraction:
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=" + savePref.getLong() + "," + savePref.getLat() + "&daddr="+str_long + "," + str_lat));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
                break;
            case R.id.ll_back:
                getActivity().onBackPressed();
                break;

        }

    }
}
