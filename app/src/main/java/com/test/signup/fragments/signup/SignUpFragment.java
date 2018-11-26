package com.test.signup.fragments.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.test.signup.TestApp;
import com.test.signup.R;
import com.test.signup.mvp.interfaces.SignUpView;
import com.test.signup.mvp.presenters.SignUpPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpFragment extends BaseSignUpFragment<SignUpView, SignUpPresenter> implements SignUpView {

    @BindView(R.id.fragment_sign_up_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_sign_up_facebook_button)
    View faceBookButton;

    @NonNull
    private final LoginManager facebookLoginManager = LoginManager.getInstance();
    @NonNull
    private final CallbackManager facebookCallbackManager = CallbackManager.Factory.create();

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        facebookLoginManager.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(@NonNull final LoginResult loginResult) {
                presenter.sendFacebookLoginInfo(loginResult);
            }

            @Override
            public void onCancel() {
                // do nothing
            }

            @Override
            public void onError(@NonNull final FacebookException error) {
                // TODO: change it when we have UI for that
                Toast.makeText(getBaseActivity(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void doInjections() {
        TestApp.getDependenciesScopesController().createSignUpComponent(fragmentRxLifecycle);
    }

    public void releaseComponent() {
        TestApp.getDependenciesScopesController().releaseSignUpComponent();
    }

    @Override
    @NonNull
    public SignUpPresenter createPresenter() {
        return TestApp.getDependenciesScopesController().getSignUpComponent().getSignUpPresenter();
    }

    @Override
    public void showReactivationDialog() {
        new MaterialDialog.Builder(getBaseActivity())
                .content(R.string.settings_fragment_reactivation_dialog_text)
                .title(R.string.settings_fragment_reactivation_dialog_title)
                .positiveText(R.string.settings_fragment_reactivation_dialog_reactivate_button_title)
                .negativeText(R.string.settings_fragment_reactivation_dialog_create_button_title)
                .onPositive((dialog, which) -> presenter.reactivateAccount())
                .onNegative((dialog, which) -> presenter.createNewAccount())
                .cancelable(false)
                .show();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.fragment_sign_up_facebook_button)
    void facebookButtonClicked() {
        facebookLoginManager.logInWithReadPermissions(this, presenter.getFacebookPermissions());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sign_up;
    }

    @NonNull
    @Override
    protected ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    protected void deactivateButtons() {
        faceBookButton.setVisibility(View.GONE);
    }

    @Override
    protected void activateButtons() {
        faceBookButton.setVisibility(View.VISIBLE);
    }
}
