package com.setnameinc.newsrss.utils.exceptions

import com.setnameinc.newsrss.utils.annotations.Layout
import com.setnameinc.newsrss.utils.annotations.MainContainer

//the BaseViewMissedAnnotationExceptionInterface implementation
class BaseViewMissedAnnotationException(val parentClass: Class<*>) :
    BaseViewMissedAnnotationExceptionInterface {

    override fun checkLayoutAnnotation() {
        if (!parentClass.isAnnotationPresent(Layout::class.java)) {
            throw ClassNotFoundException("layout annotation not presented, please set the @Layout annotation before activity class")
        }
    }

    override fun checkMainContainerAnnotation() {
        if (!parentClass.isAnnotationPresent(MainContainer::class.java)) {
            throw ClassNotFoundException("layout annotation not presented, please set the @MainContainer annotation before activity/fragment class")
        }
    }

    override fun checkFirstLevelAnnotations() {

        checkLayoutAnnotation()

        checkMainContainerAnnotation()

    }

}

//MARK:funs of BaseViewMissedAnnotationException
interface BaseViewMissedAnnotationExceptionInterface {

    fun checkLayoutAnnotation()
    fun checkMainContainerAnnotation()

    fun checkFirstLevelAnnotations()

}

//MARK: Builder for BaseViewMissedAnnotationException
class BaseViewMissedAnnotationExceptionBuilder : BaseViewMissedAnnotationExceptionBuilderInterface {

    private lateinit var parentClass: Class<*>

    override fun attachParentClass(parentClass: Class<*>): BaseViewMissedAnnotationExceptionBuilder {
        this.parentClass = parentClass
        return this
    }

    override fun build() = BaseViewMissedAnnotationException(parentClass = parentClass)

}

//MARK:funs of BaseViewMissedAnnotationExceptionBuilder
interface BaseViewMissedAnnotationExceptionBuilderInterface {
    fun attachParentClass(parentClass: Class<*>): BaseViewMissedAnnotationExceptionBuilder
    fun build(): BaseViewMissedAnnotationException
}
