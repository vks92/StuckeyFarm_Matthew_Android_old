package com.stuckeyfarm.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.stuckeyfarm.R
import com.stuckeyfarm.api.API
import com.stuckeyfarm.ui.fragment.*
import com.stuckeyfarm.utills.MyProgressBar
import com.stuckeyfarm.utills.MySharedPreference
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    var mySharedPreference: MySharedPreference? = null
    var myProgressBar: MyProgressBar? = null

    private val PERMISSIONS_REQUEST_READ_PHONE_STATE = 999

    private var mTelephonyManager: TelephonyManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()

        setListener()
        addFragment(HomeFragment())

    }

    private fun init() {

        mySharedPreference = MySharedPreference(this@HomeActivity)
        myProgressBar = MyProgressBar(this@HomeActivity)
        dl_root.setBackgroundColor(Color.parseColor(mySharedPreference!!.bgColor))


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) !== PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    PERMISSIONS_REQUEST_READ_PHONE_STATE
                )

               } else {

            }

        } else {
            getDeviceImei()
        }

    }

    private fun setListener() {

        img_menu.setOnClickListener(this)
        ll_home.setOnClickListener(this)
        ll_location.setOnClickListener(this)
        ll_pickup_list.setOnClickListener(this)
        ll_events.setOnClickListener(this)
        ll_about_us.setOnClickListener(this)
        ll_pree_order.setOnClickListener(this)
        ll_setting.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()

        setUi()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            img_menu.id -> dl_root.openDrawer(ll_menu_bar)

            ll_home.id -> {
                val fm = getSupportFragmentManager()
                for (i in 0 until fm.getBackStackEntryCount()) {
                    fm.popBackStack()
                }
                addFragment(HomeFragment())
                dl_root.closeDrawer(ll_menu_bar)
            }

            ll_location.id -> {

                replaceFragment(LocationFragment(), "LocationFragment")

            }

            ll_pickup_list.id -> {


                replaceFragment(ApplePickFragment(), "ApplePickFragment")

            }

            ll_events.id -> {

                replaceFragment(EventInfoFragment(), "EventInfoFragment")

            }

            ll_about_us.id -> {

                replaceFragment(AboutUsFragment(), "AboutUsFragment")

            }   ll_setting.id -> {

                replaceFragment(SettingFragment(), "AboutUsFragment")

            }
        }
    }

    fun addFragment(fragment: Fragment) {

        showToolBar(fragment)


        var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fl_contaner, fragment)
        fragmentTransaction.commit()


    }

    fun replaceFragment(fragment: Fragment, name: String) {

        var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(com.stuckeyfarm.R.id.fl_contaner, fragment)
            .addToBackStack(name)
        fragmentTransaction.commit()
        showToolBar(fragment)

    }

    override fun onBackPressed() {

        super.onBackPressed()
        var fragment: Fragment = supportFragmentManager.findFragmentById(com.stuckeyfarm.R.id.fl_contaner)!!
        showToolBar(fragment)

    }

    fun showToolBar(fragment: Fragment) {

        if (fragment is HomeFragment) {

            ll_toolbar.visibility = View.VISIBLE

            if (dl_root.getDrawerLockMode(ll_menu_bar) != DrawerLayout.LOCK_MODE_UNLOCKED) {

                dl_root.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

        } else {

            ll_toolbar.visibility = View.GONE

            if (dl_root.getDrawerLockMode(ll_menu_bar) != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {

                dl_root.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }
        }
    }


    fun setUi() {

        var bgColor = mySharedPreference!!.getBgColor()
        var textolor = mySharedPreference!!.getTextColor()

        ll_toolbar.setBackgroundColor(Color.parseColor(bgColor))
        ll_menu_bar.setBackgroundColor(Color.parseColor(bgColor))

        txt_home.setTextColor(Color.parseColor(textolor))
        txt_locate_us.setTextColor(Color.parseColor(textolor))
        txt_pick_list.setTextColor(Color.parseColor(textolor))
        txt_event.setTextColor(Color.parseColor(textolor))
        txt_about_us.setTextColor(Color.parseColor(textolor))

        img_home.setColorFilter(Color.parseColor(textolor))
        img_locate_us.setColorFilter(Color.parseColor(textolor))
        img_pick_list.setColorFilter(Color.parseColor(textolor))
        img_event.setColorFilter(Color.parseColor(textolor))
        img_about.setColorFilter(Color.parseColor(textolor))

    }

    override fun onRequestPermissionsResult(

        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray

    ) {

        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceImei()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceImei() {
        mTelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val deviceid = mTelephonyManager!!.getDeviceId()
        mySharedPreference!!.imeI_NO = deviceid
     //    updateToken()
        Log.d("msg", "DeviceImei $deviceid")

    }

    fun updateToken() {

        myProgressBar!!.show()
        AndroidNetworking.post(API.TOKEN_UPDATE)
            .addBodyParameter("location_id", mySharedPreference!!.getString("str_location_id"))

            .setTag("TOKEN_UPDATE")
            .setPriority(Priority.LOW)
            .addBodyParameter("uid", mySharedPreference!!.imeI_NO)
            .addBodyParameter("token", "test FCM")
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if (response.getBoolean("status")) {

                    }
                }
                override fun onError(error: ANError) {

                    myProgressBar!!.dismiss()
                }
            })
    }
}
