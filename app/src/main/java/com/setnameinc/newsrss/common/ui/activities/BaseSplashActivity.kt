package com.setnameinc.newsrss.common.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.setnameinc.newsrss.utils.annotations.Layout
import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionBuilder
import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionInterface

abstract class BaseSplashActivity : AppCompatActivity(),
    BaseSplashActivityInterface {

    private val TAG = this::class.java.simpleName

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject()

        init()

        Log.i(TAG, "onCreate | created")

    }

    override fun init() {

        checkAnnotations()

        setContentView((javaClass.getAnnotation(Layout::class.java) as Layout).id)

    }

    override fun checkAnnotations() {

        val check: BaseViewMissedAnnotationExceptionInterface =
            BaseViewMissedAnnotationExceptionBuilder().attachParentClass(javaClass).build()

        check.checkLayoutAnnotation()

    }

}

interface BaseSplashActivityInterface {

    fun init()

    fun checkAnnotations()
}