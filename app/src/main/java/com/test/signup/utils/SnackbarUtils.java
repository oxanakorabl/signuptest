package com.test.signup.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.test.signup.R;
import com.test.signup.exceptions.UnexpectedException;

import io.reactivex.functions.Action;

public final class SnackbarUtils {

    @NonNull
    public static Snackbar getSettingSnakcbar(@NonNull final View view,
                                              @StringRes final int textRes,
                                              final int snackbarDuration,
                                              @NonNull final View.OnClickListener actionClickListener,
                                              @Nullable final Action onDismissedAction) {
        return Snackbar.make(view, textRes, snackbarDuration)
                .setAction(R.string.settings, actionClickListener)
                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {

                    @Override
                    public void onDismissed(@NonNull final Snackbar transientBottomBar, final int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (onDismissedAction != null) {
                            try {
                                onDismissedAction.run();
                            } catch (@NonNull final Exception ex) {
                                throw new UnexpectedException("No exceptions are expected onDismissed() method", ex);
                            }
                        }
                    }

                });
    }

    public static void showActivityUnavailableSnackbar(@NonNull final Activity activity,
                                                        @StringRes final int errorMessageRes) {
        Snackbar.make(activity.getWindow().getDecorView(), errorMessageRes, Snackbar.LENGTH_LONG).show();
    }

    private SnackbarUtils() {

    }

}
