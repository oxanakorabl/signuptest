package com.list101.list101;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.list101.list101.di.DependenciesScopesController;
import com.squareup.leakcanary.LeakCanary;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class List101App extends Application implements Application.ActivityLifecycleCallbacks {

    private static boolean activityVisible;
    @NonNull
    private static DependenciesScopesController dependenciesScopesController = new DependenciesScopesController();

    @NonNull
    public static DependenciesScopesController getDependenciesScopesController() {
        return dependenciesScopesController;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(this);
        dependenciesScopesController.buildAppComponent(this);
    }

    @Override
    public void onActivityCreated(@NonNull final Activity activity, @NonNull final Bundle bundle) {
        // not needed
    }

    @Override
    public void onActivityStarted(@NonNull final Activity activity) {
        // not needed
    }

    @Override
    public void onActivityResumed(@NonNull final Activity activity) {
        activityVisible = true;
    }

    @Override
    public void onActivityPaused(@NonNull final Activity activity) {
        activityVisible = false;
    }

    @Override
    public void onActivityStopped(@NonNull final Activity activity) {
        // not needed
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull final Activity activity, @NonNull final Bundle bundle) {
        // not needed
    }

    @Override
    public void onActivityDestroyed(@NonNull final Activity activity) {
        // not needed
    }

    private void initFabric() {
        final CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(isDebug())
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build(), new Crashlytics());
    }

    private boolean isDebug() {
        return BuildConfig.DEBUG;
    }

}
