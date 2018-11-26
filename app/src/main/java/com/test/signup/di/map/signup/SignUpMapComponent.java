package com.test.signup.di.map.signup;

import android.support.annotation.NonNull;

import com.test.signup.mvp.presenters.SignUpMapPresenter;

import dagger.Subcomponent;

@SignUpMapScope
@Subcomponent(modules = SignUpMapModule.class)
public interface SignUpMapComponent {

    // For now we use get methods only for presenters
    @NonNull
    SignUpMapPresenter getMapPresenter();

}
