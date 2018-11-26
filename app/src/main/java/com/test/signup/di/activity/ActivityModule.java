package com.test.signup.di.activity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.test.signup.fragments.navigation.FragmentNavigation;
import com.test.signup.lifecycle.RxLifecycle;
import com.test.signup.mvp.presenters.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @NonNull
    private final RxLifecycle activityRxLifecycle;
    @NonNull
    private final Context activityContext;
    @NonNull
    private final FragmentNavigation fragmentNavigation;

    public ActivityModule(@NonNull final Context activityContext,
                          @NonNull final RxLifecycle activityRxLifecycle,
                          @NonNull final FragmentNavigation fragmentNavigation) {
        this.activityRxLifecycle = activityRxLifecycle;
        this.activityContext = activityContext;
        this.fragmentNavigation = fragmentNavigation;
    }

    // TODO to distinguish between activity and fragment lifecycles use @Qualifiers
    @NonNull
    @Provides
    @ActivityScope
    public RxLifecycle getRxLifecycle() {
        return activityRxLifecycle;
    }

    @NonNull
    @Provides
    @ActivityScope
    MainActivityPresenter provideMainActivityPresenter(@NonNull final RxLifecycle rxLifecycle) {
        return new MainActivityPresenter(rxLifecycle);
    }


}
