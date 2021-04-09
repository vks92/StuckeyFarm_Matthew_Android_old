package com.stuckeyfarm.ui.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.squareup.picasso.Picasso

import com.stuckeyfarm.R
import com.stuckeyfarm.api.API
import com.stuckeyfarm.ui.activity.HomeActivity
import com.stuckeyfarm.utills.MyProgressBar
import com.stuckeyfarm.utills.MySharedPreference
import kotlinx.android.synthetic.main.fragment_apple_pick.*
import kotlinx.android.synthetic.main.fragment_apple_pick.view.*
import kotlinx.android.synthetic.main.item_piclist.view.*
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class ApplePickFragment : Fragment() ,View.OnClickListener{

    var mySharedPreference : MySharedPreference? = null
    var myProgressBar : MyProgressBar?= null
    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_apple_pick, container, false)
        init()
        setListener()
        appData()
        return rootView

    }

    private fun init() {

        mySharedPreference= MySharedPreference(activity)
        myProgressBar= MyProgressBar(activity)
        rootView!!.ll_fragment_root.setBackgroundColor(Color.parseColor(mySharedPreference!!.bgColor))

    }
    private fun setListener() {
       rootView!!. ll_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            rootView!!. ll_back.id-> activity?.onBackPressed()
        }
    }

    fun appData(){

        myProgressBar!!.show()
        AndroidNetworking.get(API.GET_CATEGORY)
          //  .addQueryParameter("location_id",mySharedPreference!!.getString("str_location_id"))
            .setTag("GET_CATEGORY")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if(response.getBoolean("status")){
                        var res=response.getJSONArray("response")

                        for (i in 0..(res.length()-1)) {
                            var item=res.getJSONObject(i)
                            when(item.getInt("id")){

                                2->{
                                    txt_now_pick.setText(item.getString("name"))
                                }
                                3->{
                                    txt_done_for_season.setText(item.getString("name"))
                                }
                                4->{

                                    txt_coming_soon.setText(item.getString("name"))
                                }

                            }
                        }

                        myProgressBar!!.dismiss()

                        showData();
                    }
                }
                override fun onError(error: ANError) {

                    myProgressBar!!.dismiss()
                }
            })
    }

    fun showData(){
        myProgressBar!!.show()
        AndroidNetworking.get(API.GET_PICK_LIST)
          //  .addQueryParameter("location_id",mySharedPreference!!.getString("str_location_id"))
            .setTag("GET_PICK_LIST")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if(response.getBoolean("status")){
                        var res=response.getJSONArray("response")

                        for (i in 0..(res.length()-1)) {
                            try {
                                var item = res.getJSONObject(i)
                                var category_info = item.getJSONObject("category_info")
                                var view = layoutInflater.inflate(R.layout.item_piclist, null)
                                view.ll_root.setBackgroundColor(Color.parseColor(mySharedPreference!!.lableColor))
                                view.txt_title.setTextColor(Color.parseColor(mySharedPreference!!.textColor))
                                view.txt_title.setText(item.getString("name"))
                                var farnimgurl = API.CatImageUrl

                               /* if (category_info.getString("name").equals("Done For Season", true)) {
                                    farnimgurl = R.drawable.apple_r;
                                    // DONE FOR SEASON

                                } else if (category_info.getString("name").equals("Now Picking", true)) {
                                    farnimgurl = R.drawable.apple_g;

                                } else if (category_info.getString("name").equals("Coming Soon", true)) {
                                    farnimgurl = R.drawable.apple_y;
                                }*/

                                farnimgurl  += item.getJSONObject("category_info").getString("image")

                                    Picasso.get().load(farnimgurl)
                                        .into(view.img_logo);

                                view.setOnClickListener(View.OnClickListener {
                                    var appleFullInfoFragment: AppleFullInfoFragment = AppleFullInfoFragment()
                                    val bundle = Bundle()
                                    bundle.putString("data", item.toString())
                                    appleFullInfoFragment.setArguments(bundle)
                                    (activity as HomeActivity).replaceFragment(
                                        appleFullInfoFragment,
                                        "AppleFullInfoFragment"
                                    )
                                })

                                rootView!!.ll_show_List.addView(view)

                            }catch (e: Exception){
                                e.printStackTrace()

                            }

                        }
                        myProgressBar!!.dismiss()

                    }
                }
                override fun onError(error: ANError) {

                    myProgressBar!!.dismiss()
                }
            })
    }
}
