package com.test.signup.fragments.signup

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ProgressBar
import com.test.signup.R
import com.test.signup.activities.base.BaseActivity
import com.test.signup.fragments.base.BaseMainActivityLifecycleFragment
import com.test.signup.mvp.interfaces.BaseSignUpView
import com.test.signup.mvp.presenters.BaseSignUpPresenter

abstract class BaseSignUpFragment<TView : BaseSignUpView, TPresenter : BaseSignUpPresenter<TView>>
    : BaseMainActivityLifecycleFragment<TView, TPresenter>(), BaseSignUpView, BaseActivity.OnBackPressedListener {

    protected abstract fun doInjections()

    protected abstract fun getProgressBar(): ProgressBar

    protected abstract fun deactivateButtons()

    protected abstract fun activateButtons()

    protected abstract fun releaseComponent()

    override fun showProgress() {
        getProgressBar().visibility = View.VISIBLE
        deactivateButtons()
    }

    override fun hideProgress() {
        getProgressBar().visibility = View.GONE
        activateButtons()
    }

    override fun showError(throwable: Throwable) {
        // TODO implement later
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        doInjections()

        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        releaseComponent()

        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarInflater.inflateOnlyTitleToolbar(baseActivity, R.string.sign_up)
        baseActivity.disableSidebar()
    }

    override fun onStart() {
        super.onStart()

        baseActivity.addOnBackPressedListener(this)
    }

    override fun onStop() {
        super.onStop()

        baseActivity.removeOnBackPressedListener(this)
    }

    override fun onConfigureNavigation(menu: Menu, inflater: MenuInflater) {
        super.onConfigureNavigation(menu, inflater)

        baseActivity.supportActionBar.setDisplayHomeAsUpEnabled(false)
    }

    override fun onBackPressed() = presenter.onBackPressed()

}