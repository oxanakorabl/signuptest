package com.test.signup.di.signupflow.scores

import com.test.signup.api.apipart.wrappers.SignUpApiWrapper
import com.test.signup.fragments.navigation.SignUpStateController
import com.test.signup.lifecycle.RxLifecycle
import com.test.signup.mvp.presenters.ScorePresenter
import dagger.Module
import dagger.Provides

@Module
class ScoreModule(val fragmentRxLifecycle: RxLifecycle) {

    @Provides
    @ScoreScope
    fun provideScorePresenter(signUpApiWrapper: SignUpApiWrapper, controller: SignUpStateController) = ScorePresenter(fragmentRxLifecycle,
            signUpApiWrapper, controller)

}