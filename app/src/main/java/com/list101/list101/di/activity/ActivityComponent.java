package com.list101.list101.di.activity;

import android.support.annotation.NonNull;

import com.list101.list101.activities.MainActivity;
import com.list101.list101.di.signupflow.SignUpFlowComponent;
import com.list101.list101.di.signupflow.SignUpFlowModule;
import com.list101.list101.mvp.presenters.MainActivityPresenter;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveImports", "PMD.ExcessivePublicCount"})
public interface ActivityComponent {

    @NonNull
    SignUpFlowComponent plusSignUpFlowComponent(@NonNull SignUpFlowModule signUpFlowModule);

    // For now we use get methods only for presenters
    @NonNull
    MainActivityPresenter getMainActivityPresenter();

    void inject(@NonNull MainActivityPresenter mainActivityPresenter);

    void inject(@NonNull MainActivity mainActivity);

}
