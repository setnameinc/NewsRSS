package com.setnameinc.newsrss.common.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.setnameinc.newsrss.utils.annotations.Layout

abstract class BaseActivity () : AppCompatActivity(), BaseActivityInterface{

    private val TAG = this::class.java.simpleName

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject()

        init()

    }

    override fun init() {

        checkAnnotations()

        Log.i(TAG, "onCreate | annotations checked ")

        setContentView((javaClass.getAnnotation(Layout::class.java) as Layout).id)

        Log.i(TAG, "onCreate | content has been set")

    }

}

interface BaseActivityInterface{

    fun init()
    fun checkAnnotations()

}