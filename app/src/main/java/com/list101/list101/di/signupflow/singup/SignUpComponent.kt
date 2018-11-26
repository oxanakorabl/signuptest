package com.list101.list101.di.signupflow.singup

import com.list101.list101.mvp.presenters.signup.SignUpPresenter
import dagger.Subcomponent

@SignUpScope
@Subcomponent(modules = [SignUpModule::class])
interface SignUpComponent {

    fun getSignUpPresenter(): SignUpPresenter

}