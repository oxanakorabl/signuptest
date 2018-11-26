package com.list101.list101.di;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.list101.list101.di.activity.ActivityComponent;
import com.list101.list101.di.activity.ActivityModule;
import com.list101.list101.di.app.AppComponent;
import com.list101.list101.di.app.AppModule;
import com.list101.list101.di.app.DaggerAppComponent;
import com.list101.list101.di.map.signup.SignUpMapComponent;
import com.list101.list101.di.map.signup.SignUpMapModule;
import com.list101.list101.di.signupflow.SignUpFlowComponent;
import com.list101.list101.di.signupflow.SignUpFlowModule;
import com.list101.list101.di.signupflow.scores.ScoreComponent;
import com.list101.list101.di.signupflow.scores.ScoreModule;
import com.list101.list101.di.signupflow.singup.SignUpComponent;
import com.list101.list101.di.signupflow.singup.SignUpModule;
import com.list101.list101.exceptions.EmptyInjectionComponentException;
import com.list101.list101.fragments.navigation.FragmentNavigation;
import com.list101.list101.lifecycle.RxLifecycle;


public class DependenciesScopesController {

    @NonNull
    private AppComponent appComponent;

    @Nullable
    private SignUpMapComponent signUpMapComponent;
    @Nullable
    private ActivityComponent activityComponent;

    @Nullable
    private SignUpFlowComponent signUpFlowComponent;
    @Nullable
    private SignUpComponent signUpComponent;
    @Nullable
    private ScoreComponent scoreComponent;

    public void buildAppComponent(@NonNull final Context context) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
    }

    @NonNull
    public AppComponent getAppComponent() {
        return appComponent;
    }

    @NonNull
    public ActivityComponent createActivityComponent(@NonNull final Context context,
                                                     @NonNull final RxLifecycle rxLifecycle,
                                                     @NonNull final FragmentNavigation fragmentNavigation) {
        activityComponent = appComponent.plusActivityComponent(new ActivityModule(context, rxLifecycle, fragmentNavigation));
        return activityComponent;
    }

    @NonNull
    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            throw new EmptyInjectionComponentException(ActivityComponent.class.getSimpleName());
        }
        return activityComponent;
    }

    public void releaseActivityComponent() {
        activityComponent = null;
    }


    @NonNull
    public SignUpFlowComponent createSignUpFlowComponent(@NonNull final RxLifecycle rxLifecycle) {
        if (signUpFlowComponent == null) {
            signUpFlowComponent = getActivityComponent().plusSignUpFlowComponent(new SignUpFlowModule(rxLifecycle));
        }
        return signUpFlowComponent;
    }

    @NonNull
    public SignUpFlowComponent getSignUpFlowComponent() {
        if (signUpFlowComponent == null) {
            throw new EmptyInjectionComponentException(SignUpFlowComponent.class.getSimpleName());
        }
        return signUpFlowComponent;
    }

    public void releaseSignUpFlowComponent() {
        signUpFlowComponent = null;
    }

    @NonNull
    public SignUpComponent createSignUpComponent(@NonNull final RxLifecycle fragmentRxLifecycle) {
        if (signUpComponent == null) {
            signUpComponent = getSignUpFlowComponent().plusSignUpComponent(new SignUpModule(fragmentRxLifecycle));
        }
        return signUpComponent;
    }

    @NonNull
    public SignUpComponent getSignUpComponent() {
        if (signUpComponent == null) {
            throw new EmptyInjectionComponentException(SignUpComponent.class.getSimpleName());
        }
        return signUpComponent;
    }

    public void releaseSignUpComponent() {
        signUpComponent = null;
    }

    @NonNull
    public ScoreComponent createScoreComponent(@NonNull final RxLifecycle fragmentRxLifecycle) {
        if (scoreComponent == null) {
            scoreComponent = getSignUpFlowComponent().plusScoreComponent(new ScoreModule(fragmentRxLifecycle));
        }
        return scoreComponent;
    }

    @NonNull
    public ScoreComponent getScoreComponent() {
        if (scoreComponent == null) {
            throw new EmptyInjectionComponentException(ScoreComponent.class.getSimpleName());
        }
        return scoreComponent;
    }

    public void releaseScoreComponent() {
        scoreComponent = null;
    }


    @NonNull
    public SignUpMapComponent createSignUpMapFragmentComponent(@NonNull final RxLifecycle fragmentRxLifecycle) {
        if (signUpMapComponent == null) {
            signUpMapComponent = getSignUpFlowComponent().plusSignUpMapComponent(new SignUpMapModule(fragmentRxLifecycle));
        }
        return signUpMapComponent;
    }

    @NonNull
    public SignUpMapComponent getSignUpMapFragmentComponent() {
        if (signUpMapComponent == null) {
            throw new EmptyInjectionComponentException(SignUpMapComponent.class.getSimpleName());
        }
        return signUpMapComponent;
    }

    public void releaseSignUpMapFragmentComponent() {
        signUpMapComponent = null;
    }

}
