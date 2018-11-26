package com.test.signup.mvp.interfaces

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseSignUpView : MvpView {
    
    fun showProgress()

    fun hideProgress()

    fun showError(throwable: Throwable)
}