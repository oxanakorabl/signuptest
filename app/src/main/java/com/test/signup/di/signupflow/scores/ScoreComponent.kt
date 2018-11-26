package com.test.signup.di.signupflow.scores

import com.test.signup.mvp.presenters.ScorePresenter
import dagger.Subcomponent

@ScoreScope
@Subcomponent(modules = [ScoreModule::class])
interface ScoreComponent {

    fun getScorePresenter(): ScorePresenter

}