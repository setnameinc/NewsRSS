package com.setnameinc.newsrss.common.presenters

abstract class BaseMainPresenter<View: BaseMainView> : BasePresenter<View, MainRouter>()

interface BaseMainView{
    fun showError(string: String)
}

interface MainRouter{

    /*fun showMainFragment()
    fun showUserFragment()*/

}