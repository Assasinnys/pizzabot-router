package com.itechart.pizzabotrouter.router

import com.itechart.pizzabotrouter.util.ERR_BAD_INPUT
import com.itechart.pizzabotrouter.model.Pos
import com.itechart.pizzabotrouter.util.pow
import kotlin.math.absoluteValue

class RouterImpl(private val startPosition: Pos = Pos(0, 0)) : Router {

    private var robotPosition: Pos = startPosition

    override fun getRoute(clients: List<Pos>): String {
        if (clients.isEmpty()) throw IllegalArgumentException(ERR_BAD_INPUT)

        val route = StringBuilder()
        val sortedClients = clients.sortedBy { calculateDistance(robotPosition, it) }

        sortedClients.forEach { client ->
            val diffX = client.x.minus(robotPosition.x)
            when {
                diffX > 0 -> repeat(diffX) {
                    route.append(EAST)
                }
                diffX < 0 -> repeat(diffX.absoluteValue) {
                    route.append(WEST)
                }
            }

            val diffY = client.y.minus(robotPosition.y)
            when {
                diffY > 0 -> repeat(diffY) {
                    route.append(NORTH)
                }
                diffY < 0 -> repeat(diffY.absoluteValue) {
                    route.append(SOUTH)
                }
            }
            robotPosition = client
            route.append(DROP)
        }

        robotPosition = startPosition
        return route.toString()
    }

    private fun calculateDistance(a: Pos, b: Pos): Int = (a.x.minus(b.x)).pow(2).plus(a.y.minus(b.y).pow(2))

    companion object {
        const val NORTH = "N"
        const val SOUTH = "S"
        const val WEST = "W"
        const val EAST = "E"
        const val DROP = "D"
    }
}