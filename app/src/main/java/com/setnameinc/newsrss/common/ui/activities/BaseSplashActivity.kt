package com.setnameinc.newsrss.common.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.setnameinc.newsrss.utils.annotations.Layout
import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionBuilder
import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionInterface

abstract class BaseSplashActivity : BaseActivity(), BaseSplashActivityInterface {

    override fun checkAnnotations() {

        val check: BaseViewMissedAnnotationExceptionInterface =
            BaseViewMissedAnnotationExceptionBuilder().attachParentClass(javaClass).build()

        check.checkLayoutAnnotation()

    }

}

interface BaseSplashActivityInterface {



}