package com.test.signup.di.signupflow

import com.test.signup.di.map.signup.SignUpMapComponent
import com.test.signup.di.map.signup.SignUpMapModule
import com.test.signup.di.signupflow.scores.ScoreComponent
import com.test.signup.di.signupflow.scores.ScoreModule
import com.test.signup.di.signupflow.singup.SignUpComponent
import com.test.signup.di.signupflow.singup.SignUpModule
import com.test.signup.fragments.navigation.SignUpStateController
import dagger.Subcomponent

@SignUpFlowScope
@Subcomponent(modules = [SignUpFlowModule::class])
interface SignUpFlowComponent {

    fun plusSignUpComponent(signUpModule: SignUpModule): SignUpComponent

    fun plusScoreComponent(scoreModule: ScoreModule): ScoreComponent

    fun plusSignUpMapComponent(signUpMapModule: SignUpMapModule): SignUpMapComponent

    fun getController(): SignUpStateController

}