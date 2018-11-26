package com.list101.list101.di.map.signup;

import android.support.annotation.NonNull;

import com.list101.list101.api.apipart.wrappers.SignUpApiWrapper;
import com.list101.list101.fragments.navigation.SignUpStateController;
import com.list101.list101.lifecycle.RxLifecycle;
import com.list101.list101.mvp.presenters.signup.SignUpMapPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SignUpMapModule {

    @NonNull
    private final RxLifecycle fragmentRxLifecycle;

    public SignUpMapModule(@NonNull final RxLifecycle fragmentRxLifecycle) {
        this.fragmentRxLifecycle = fragmentRxLifecycle;
    }

    @NonNull
    @Provides
    @SignUpMapScope
    SignUpMapPresenter provideMapPresenter(@NonNull final SignUpApiWrapper signUpApiWrapper,
                                           @NonNull final SignUpStateController interactor) {
        return new SignUpMapPresenter(fragmentRxLifecycle, signUpApiWrapper, interactor);
    }

}
