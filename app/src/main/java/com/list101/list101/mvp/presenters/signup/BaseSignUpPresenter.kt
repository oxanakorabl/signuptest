package com.list101.list101.mvp.presenters.signup

import com.list101.list101.fragments.navigation.SignUpStateController
import com.list101.list101.lifecycle.RxLifecycle
import com.list101.list101.mvp.interfaces.BaseSignUpView
import com.list101.list101.mvp.presenters.base.MvpBaseLifecyclePresenter

abstract class BaseSignUpPresenter<TView : BaseSignUpView>(rxLifecycle: RxLifecycle, val controller: SignUpStateController)
    : MvpBaseLifecyclePresenter<TView>(rxLifecycle) {

    fun onBackPressed() = controller.onBackPressed()
}
