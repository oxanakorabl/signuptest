package com.test.signup.mvp.presenters

import com.test.signup.fragments.navigation.SignUpStateController
import com.test.signup.lifecycle.RxLifecycle
import com.test.signup.mvp.interfaces.BaseSignUpView

abstract class BaseSignUpPresenter<TView : BaseSignUpView>(rxLifecycle: RxLifecycle, val controller: SignUpStateController)
    : MvpBaseLifecyclePresenter<TView>(rxLifecycle) {

    fun onBackPressed() = controller.onBackPressed()
}
