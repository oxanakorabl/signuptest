package com.test.signup.di.signupflow.singup

import com.test.signup.mvp.presenters.SignUpPresenter
import dagger.Subcomponent

@SignUpScope
@Subcomponent(modules = [SignUpModule::class])
interface SignUpComponent {

    fun getSignUpPresenter(): SignUpPresenter

}