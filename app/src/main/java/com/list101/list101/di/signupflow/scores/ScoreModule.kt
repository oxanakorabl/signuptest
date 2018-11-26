package com.list101.list101.di.signupflow.scores

import com.list101.list101.api.apipart.wrappers.SignUpApiWrapper
import com.list101.list101.fragments.navigation.SignUpStateController
import com.list101.list101.lifecycle.RxLifecycle
import com.list101.list101.mvp.presenters.signup.ScorePresenter
import dagger.Module
import dagger.Provides

@Module
class ScoreModule(val fragmentRxLifecycle: RxLifecycle) {

    @Provides
    @ScoreScope
    fun provideScorePresenter(signUpApiWrapper: SignUpApiWrapper, controller: SignUpStateController) = ScorePresenter(fragmentRxLifecycle,
            signUpApiWrapper, controller)

}