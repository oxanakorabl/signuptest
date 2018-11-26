package com.list101.list101.mvp.model

import com.list101.list101.api.model.response.ScoresResponse

data class Scores(var influencePoints: Long, var popularityPoints: Long) {

    constructor(response: ScoresResponse) : this(response.influencePoints, response.popularityPoints)

}