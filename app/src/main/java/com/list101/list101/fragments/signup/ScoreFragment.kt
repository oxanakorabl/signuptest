package com.list101.list101.fragments.signup

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.list101.list101.List101App
import com.list101.list101.R
import com.list101.list101.mvp.interfaces.ScoreView
import com.list101.list101.mvp.model.Scores
import com.list101.list101.mvp.presenters.signup.ScorePresenter

class ScoreFragment : BaseSignUpFragment<ScoreView, ScorePresenter>(), ScoreView {

    @BindView(R.id.fragment_signup_score_boost_button_container)
    lateinit var boostButtonContainer: View
    @BindView(R.id.fragment_sign_score_progress_bar)
    lateinit var progress: ProgressBar
    @BindView(R.id.fragment_signup_score_not_now_button)
    lateinit var notNowButton: View
    @BindView(R.id.fragment_signup_score_see_my_ranking_button)
    lateinit var seeMyRankingButton: View
    @BindView(R.id.fragment_signup_score_dummy_influence_score_result)
    lateinit var influenceScoreTextView: TextView
    @BindView(R.id.fragment_signup_score_dummy_popularity_score_result)
    lateinit var popularityScoreTextView: TextView

    override fun onStart() {
        super.onStart()

        presenter.getScore()
    }

    override fun doInjections() {
        List101App.getDependenciesScopesController().createScoreComponent(fragmentRxLifecycle)
    }

    override fun releaseComponent() {
        List101App.getDependenciesScopesController().releaseScoreComponent()
    }

    override fun createPresenter() = List101App.getDependenciesScopesController().scoreComponent.getScorePresenter()

    override fun configureButtons(boostedWithInstagram: Boolean) {
        boostButtonContainer.visibility = if (!boostedWithInstagram) View.VISIBLE else View.INVISIBLE
        notNowButton.visibility = if (!boostedWithInstagram) View.VISIBLE else View.INVISIBLE
        seeMyRankingButton.visibility = if (boostedWithInstagram) View.VISIBLE else View.INVISIBLE
    }

    override fun getProgressBar() = progress

    override fun deactivateButtons() {
        boostButtonContainer.visibility = View.INVISIBLE
        notNowButton.visibility = View.INVISIBLE
        seeMyRankingButton.visibility = View.INVISIBLE
    }

    override fun activateButtons() {
        // TODO: refactor it after add boosting
        configureButtons(false)
    }

    override fun displayScore(scores: Scores) {
        popularityScoreTextView.text = scores.popularityPoints.toString()
        influenceScoreTextView.text = scores.influencePoints.toString()
    }

    override fun getLayoutResource() = R.layout.fragment_signup_score

    @OnClick(R.id.fragment_signup_score_boost_button_container)
    fun onBoostClicked() {
        // TODO implement later
    }

    @OnClick(R.id.fragment_signup_score_not_now_button)
    fun onNotNowClicked() {
        presenter.acceptScore()
    }

    @OnClick(R.id.fragment_signup_score_see_my_ranking_button)
    fun onSeeMyRankingClicked() {
        presenter.acceptScore()
    }

}