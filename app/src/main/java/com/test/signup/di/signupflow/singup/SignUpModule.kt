package com.test.signup.di.signupflow.singup

import com.test.signup.api.apipart.wrappers.AccountsApiWrapper
import com.test.signup.fragments.navigation.SignUpStateController
import com.test.signup.lifecycle.RxLifecycle
import com.test.signup.mvp.presenters.SignUpPresenter
import dagger.Module
import dagger.Provides

@Module
class SignUpModule(val fragmentRxLifecycle: RxLifecycle) {

    @Provides
    @SignUpScope
    fun provideSignUpPresenter(accountsApiWrapper: AccountsApiWrapper, controller: SignUpStateController) = SignUpPresenter(fragmentRxLifecycle,
            accountsApiWrapper, controller)

}