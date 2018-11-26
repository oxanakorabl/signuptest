package com.list101.list101.di.signupflow

import com.list101.list101.fragments.navigation.Router
import com.list101.list101.fragments.navigation.SignUpStateController
import com.list101.list101.lifecycle.RxLifecycle
import com.list101.list101.storage.prefs.ReactiveJsonSharedPreferences
import com.list101.list101.storage.prefs.Session
import dagger.Module
import dagger.Provides

@Module
class SignUpFlowModule(val rxLifecycle: RxLifecycle) {

    @Provides
    @SignUpFlowScope
    fun provideSighUpStateController(sessionPreferences: ReactiveJsonSharedPreferences<Session>,
                                     router: Router) = SignUpStateController(rxLifecycle, router, sessionPreferences)

}