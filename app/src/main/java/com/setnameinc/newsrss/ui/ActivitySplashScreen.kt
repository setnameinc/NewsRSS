package com.setnameinc.newsrss.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.bumptech.glide.Glide
import com.setnameinc.newsrss.App
import com.setnameinc.newsrss.R
import com.setnameinc.newsrss.common.ui.activities.BaseSplashActivity
import com.setnameinc.newsrss.utils.annotations.Layout
import kotlinx.android.synthetic.main.activity_splash_screen.*

@Layout(R.layout.activity_splash_screen)
class ActivitySplashScreen : BaseSplashActivity (){

    override fun inject() {
        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(applicationContext)
            .load("https://i.pinimg.com/originals/53/62/6d/53626d65b067a71d5d8d1c1d4d7d224f.gif")
            .into(activity_splash_screen_iv_main)

        activity_splash_screen_btn_next.setOnClickListener {

            //lazy to use cicerone :(
            startActivity(Intent(this.applicationContext, ActivityMain::class.java))
            this.finish()
            
        }

    }

}