package com.setnameinc.newsrss.common.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.setnameinc.newsrss.navigations.Activities
import com.setnameinc.newsrss.navigations.Fragments
import com.setnameinc.newsrss.utils.annotations.Layout
import com.setnameinc.newsrss.utils.annotations.MainContainer
import com.setnameinc.newsrss.utils.constants.Navigation
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

abstract class BaseActivityWithNavigation : BaseActivity(), BaseActivityWithNavigationInterface {

    private val TAG = this::class.java.simpleName

    @Inject
    @field:Named(Navigation.ROUTER_FIRST_LEVEL)
    lateinit var routerFirstLevel: Router

    @Inject
    @field:Named(Navigation.NAVIGATION_HOLDER_FIRST_LEVEL)
    lateinit var navigatorHolderFirstLevel: NavigatorHolder

    private lateinit var navigatorFirstLevel: Navigator

    //MARK: Navigation to someone fragment
    override fun navigateTo(justSimpleName: String?) {

        if (justSimpleName?.toLowerCase()?.contains("fragment")!!) {

            routerFirstLevel.newRootScreen(Fragments(justSimpleName))

        } else {

            routerFirstLevel.newRootScreen(Activities(justSimpleName))

        }

        Log.i(TAG, "onCreate | the $justSimpleName opened")

    }

    override fun init() {
        super.init()

        navigatorFirstLevel = SupportAppNavigator(this, (javaClass.getAnnotation(MainContainer::class.java) as MainContainer).id)

        Log.i(TAG, "onCreate | navigatorFirstLevel has been initialized")

    }

    override fun onStart() {
        super.onStart()

        navigatorHolderFirstLevel.setNavigator(navigatorFirstLevel)

        Log.i(TAG, "onStart")

    }

    override fun onDestroy() {

        navigatorHolderFirstLevel.removeNavigator()

        Log.i(TAG, "onDestroyed")

        super.onDestroy()

    }

}

interface BaseActivityWithNavigationInterface {

    fun navigateTo(justSimpleName: String?)

}
