package com.stuckeyfarm.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.stuckeyfarm.R
import com.stuckeyfarm.utills.MySharedPreference
import kotlinx.android.synthetic.main.activity_image_view.*

class ImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        var mySharedPreference: MySharedPreference = MySharedPreference(this@ImageViewActivity)

        ll_root.setBackgroundColor(Color.parseColor(mySharedPreference.bgColor))

        ll_back.setOnClickListener { finish() }

        var imgurl = intent.getStringExtra("image")
        Picasso.get()
            .load(imgurl)
            .into(img);
    }
}
