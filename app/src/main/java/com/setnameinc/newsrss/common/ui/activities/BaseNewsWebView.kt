package com.setnameinc.newsrss.common.ui.activities

import com.setnameinc.newsrss.App
import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionBuilder
import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionInterface

abstract class BaseNewsWebView : BaseActivity(), BaseNewsWebViewInterface {

    override fun checkAnnotations() {
        val check: BaseViewMissedAnnotationExceptionInterface =
            BaseViewMissedAnnotationExceptionBuilder().attachParentClass(javaClass).build()

        check.checkLayoutAnnotation()
    }


}

interface BaseNewsWebViewInterface{

}
