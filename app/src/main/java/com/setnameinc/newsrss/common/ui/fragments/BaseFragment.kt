package com.setnameinc.newsrss.common.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.setnameinc.newsrss.common.presenters.BasePresenter
import com.setnameinc.newsrss.utils.annotations.Layout
import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionBuilder
import com.setnameinc.newsrss.utils.exceptions.BaseViewMissedAnnotationExceptionInterface

abstract class BaseFragment : Fragment(), BaseFragmentInterface {

    protected abstract fun getPresenter(): BasePresenter<*, *>
    protected abstract fun inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkAnnotations()

        inject()
        getPresenter().view = this

    }

    override fun checkAnnotations() {

        val check: BaseViewMissedAnnotationExceptionInterface =
            BaseViewMissedAnnotationExceptionBuilder().attachParentClass(javaClass).build()

        check.checkLayoutAnnotation()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate((javaClass.getAnnotation(Layout::class.java) as Layout).id, container, false)
    }

    override fun onStart() {
        super.onStart()
        getPresenter().onStart()
    }

    override fun onStop() {
        super.onStop()
        getPresenter().onStop()
    }

}

interface BaseFragmentInterface {

    fun checkAnnotations()

}