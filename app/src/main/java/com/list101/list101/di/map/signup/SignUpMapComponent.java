package com.list101.list101.di.map.signup;

import android.support.annotation.NonNull;

import com.list101.list101.mvp.presenters.signup.SignUpMapPresenter;

import dagger.Subcomponent;

@SignUpMapScope
@Subcomponent(modules = SignUpMapModule.class)
public interface SignUpMapComponent {

    // For now we use get methods only for presenters
    @NonNull
    SignUpMapPresenter getMapPresenter();

}
