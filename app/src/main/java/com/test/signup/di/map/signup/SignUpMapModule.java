package com.test.signup.di.map.signup;

import android.support.annotation.NonNull;

import com.test.signup.api.apipart.wrappers.SignUpApiWrapper;
import com.test.signup.fragments.navigation.SignUpStateController;
import com.test.signup.lifecycle.RxLifecycle;
import com.test.signup.mvp.presenters.SignUpMapPresenter;

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
