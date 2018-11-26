package com.test.signup.mvp.model

import com.test.signup.api.model.response.ScoresResponse

data class Scores(var influencePoints: Long, var popularityPoints: Long) {

    constructor(response: ScoresResponse) : this(response.influencePoints, response.popularityPoints)

}