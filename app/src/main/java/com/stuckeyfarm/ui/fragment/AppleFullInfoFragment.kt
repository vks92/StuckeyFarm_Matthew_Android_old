package com.stuckeyfarm.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.stuckeyfarm.R
import com.stuckeyfarm.api.API
import com.stuckeyfarm.ui.activity.ImageViewActivity
import com.stuckeyfarm.utills.MySharedPreference
import kotlinx.android.synthetic.main.fragment_apple_full_info.view.*
import org.json.JSONObject


class AppleFullInfoFragment : Fragment(), View.OnClickListener {

    var rootView: View? = null
    var data = ""
    var mySharedPreference : MySharedPreference? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_apple_full_info, container, false)
        init()
        setlistener()
        return rootView

    }

    private fun init() {

        val mySharedPreference: MySharedPreference = MySharedPreference(activity)


        rootView!!.ll_fragment_root.setBackgroundColor(Color.parseColor(mySharedPreference.bgColor))

        data = arguments?.getString("data")?:""

        var res = JSONObject(data)

        rootView!!.txt_descriptiond.setText(res.getString("description"))
        rootView!!.txt_named.setText(res.getString("name"))

        var imgurl = API.PiskListImageUrl + res.getString("image")

        Picasso.get()
            .load(imgurl)
            .into(rootView!!.img_info);

        rootView?.status_label?.setBackgroundColor(Color.parseColor(mySharedPreference.lableColor))
        rootView?.txt_status?.setTextColor(Color.parseColor(mySharedPreference.textColor))
        rootView!!.txt_named.setTextColor(Color.parseColor(mySharedPreference.textColor))
        rootView!!.txt_descriptiond.setTextColor(Color.parseColor(mySharedPreference.textColor))
        rootView!!.text_product.setTextColor(Color.parseColor(mySharedPreference.textColor))


        var category_info = JSONObject(data).getJSONObject("category_info")
        var farnimgurl = API.CatImageUrl
        /*if (category_info.getString("name").equals("Done For Season", true)) {
            farnimgurl = R.drawable.apple_r;
            // DONE FOR SEASON

        } else if (category_info.getString("name").equals("Now Picking", true)) {
            farnimgurl = R.drawable.apple_g;

        } else if (category_info.getString("name").equals("Coming Soon", true)) {
            farnimgurl = R.drawable.apple_y;
        }*/


        farnimgurl += JSONObject(data).getJSONObject("category_info").getString("image")

        Picasso.get().load(farnimgurl).error(R.drawable.apple_g)
            .placeholder(R.drawable.apple_g)
            .into(rootView!!.img_logo);

        rootView!!.txt_status.text = category_info.getString("name")
        var tmpImg = API.MapImageBaseUrl
        var imgArray = JSONObject(data).getJSONArray("map_image")

        if (imgArray.length() >= 1) {
            tmpImg += imgArray.getJSONObject(0).getString("image")
        }


        rootView!!.ll_view_map.setOnClickListener {

            startActivity(Intent(activity, ImageViewActivity::class.java).putExtra("image", tmpImg))

        }


    }

    private fun setlistener() {

        rootView!!.img_back.setOnClickListener(this)



    }


    override fun onClick(v: View?) {

        when (v?.id) {

            rootView!!.img_back.id -> {

                activity?.onBackPressed()

            }


        }
    }


}