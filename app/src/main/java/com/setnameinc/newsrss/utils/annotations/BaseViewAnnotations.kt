package com.setnameinc.newsrss.utils.annotations

import androidx.annotation.LayoutRes

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(AnnotationTarget.CLASS)
annotation class Layout(@LayoutRes val id: Int)

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(AnnotationTarget.CLASS)
annotation class MainContainer(@LayoutRes val id: Int)
