package com.test.signup.di.signupflow

import com.test.signup.fragments.navigation.Router
import com.test.signup.fragments.navigation.SignUpStateController
import com.test.signup.lifecycle.RxLifecycle
import com.test.signup.storage.prefs.ReactiveJsonSharedPreferences
import com.test.signup.storage.prefs.Session
import dagger.Module
import dagger.Provides

@Module
class SignUpFlowModule(val rxLifecycle: RxLifecycle) {

    @Provides
    @SignUpFlowScope
    fun provideSighUpStateController(sessionPreferences: ReactiveJsonSharedPreferences<Session>,
                                     router: Router) = SignUpStateController(rxLifecycle, router, sessionPreferences)

}