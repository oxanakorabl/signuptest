package com.list101.list101.fragments.navigation

import com.list101.list101.fragments.signup.ScoreFragment
import com.list101.list101.fragments.signup.SignUpFragment
import com.list101.list101.fragments.signup.SignUpMapFragment

class Router(private val fragmentNavigation: FragmentNavigation) {

    fun openSignUp() {
        fragmentNavigation.setInitial(SignUpFragment::class.java)
    }

    fun openSignUpMap() {
        fragmentNavigation.push(SignUpMapFragment::class.java)
    }

    fun openSignUpScore() {
        fragmentNavigation.push(ScoreFragment::class.java)
    }

}