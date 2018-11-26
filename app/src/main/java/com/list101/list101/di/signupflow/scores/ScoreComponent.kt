package com.list101.list101.di.signupflow.scores

import com.list101.list101.mvp.presenters.signup.ScorePresenter
import dagger.Subcomponent

@ScoreScope
@Subcomponent(modules = [ScoreModule::class])
interface ScoreComponent {

    fun getScorePresenter(): ScorePresenter

}