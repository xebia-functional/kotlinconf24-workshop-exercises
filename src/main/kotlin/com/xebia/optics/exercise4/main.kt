package com.xebia.com.xebia.optics.exercise4

import arrow.core.none
import arrow.optics.Every
import arrow.optics.dsl.at
import arrow.optics.dsl.every
import arrow.optics.dsl.some
import arrow.optics.optics
import arrow.optics.typeclasses.At

@optics
data class Player(val name: String, val points: Double) {
    companion object
}

@optics
data class Team(val players: Map<Int, Player>) {
    companion object
}

fun Team.addPoints(x: Int): Team =
    Team.players.every(Every.map()).points.modify(this) { it + x }

fun Team.addPoint(key: Int, x: Int): Team =
    Team.players.at(At.map(), key).some.points.modify(this) { it + x }

fun Team.removePlayer(key: Int): Team =
    Team.players.at(At.map(), key).set(this, none())
