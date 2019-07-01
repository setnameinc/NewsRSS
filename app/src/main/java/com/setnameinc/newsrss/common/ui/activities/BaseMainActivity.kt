package com.setnameinc.newsrss.common.ui.activities

import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionBuilder
import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionInterface


abstract class BaseMainActivity : BaseActivity(),
    BaseMainActivityInterface {

    override fun checkAnnotations() {

        val check: BaseViewMissedAnnotationExceptionInterface =
            BaseViewMissedAnnotationExceptionBuilder().attachParentClass(javaClass).build()

        check.checkFirstLevelAnnotations()

    }

}

interface BaseMainActivityInterface {

}