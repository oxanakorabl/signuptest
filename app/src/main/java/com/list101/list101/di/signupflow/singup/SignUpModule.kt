package com.list101.list101.di.signupflow.singup

import com.list101.list101.api.apipart.wrappers.AccountsApiWrapper
import com.list101.list101.fragments.navigation.SignUpStateController
import com.list101.list101.lifecycle.RxLifecycle
import com.list101.list101.mvp.presenters.signup.SignUpPresenter
import dagger.Module
import dagger.Provides

@Module
class SignUpModule(val fragmentRxLifecycle: RxLifecycle) {

    @Provides
    @SignUpScope
    fun provideSignUpPresenter(accountsApiWrapper: AccountsApiWrapper, controller: SignUpStateController) = SignUpPresenter(fragmentRxLifecycle,
            accountsApiWrapper, controller)

}