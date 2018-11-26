package com.test.signup.di.activity;

import android.support.annotation.NonNull;

import com.test.signup.activities.MainActivity;
import com.test.signup.di.signupflow.SignUpFlowComponent;
import com.test.signup.di.signupflow.SignUpFlowModule;
import com.test.signup.mvp.presenters.MainActivityPresenter;

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
