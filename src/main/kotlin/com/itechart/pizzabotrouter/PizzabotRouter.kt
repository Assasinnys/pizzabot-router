package com.itechart.pizzabotrouter

import com.itechart.pizzabotrouter.model.Pos
import com.itechart.pizzabotrouter.parser.InputStringParser
import com.itechart.pizzabotrouter.parser.InputParser
import com.itechart.pizzabotrouter.router.Router
import com.itechart.pizzabotrouter.router.RouterImpl
import com.itechart.pizzabotrouter.util.ERR_INPUT_EMPTY
import com.itechart.pizzabotrouter.util.ERR_UNKNOWN
import com.itechart.pizzabotrouter.util.ERR_UNKNOWN_COMMAND
import com.itechart.pizzabotrouter.util.PIZZABOT_COMMAND
import java.util.Scanner

class PizzabotRouter(
    private val router: Router = RouterImpl(),
    private val inputParser: InputStringParser = InputParser()
) {

    private val scanner = Scanner(System.`in`)

    fun start() {
        println("Welcome to the pizzabot router. To plot a route, please enter /pizzabot [clients coordinates]")
        println("or you can exit from the router by closing this window")

        while (true) {
            val userInput = scanner.nextLine()

            if (userInput.startsWith(PIZZABOT_COMMAND))
                println(findRouteToClients(userInput))
            else
                println(ERR_UNKNOWN_COMMAND)
        }
    }

    fun findRouteToClients(input: String): String {
        return try {
            if (input.isEmpty()) throw IllegalArgumentException(ERR_INPUT_EMPTY)

            val clientPositions = inputParser.parsePositions(input)
            val grid = inputParser.parseGrid(input)
            val validatedClients = areaValidation(grid, clientPositions)

            router.getRoute(validatedClients)

        } catch (e: Exception) {
            e.message ?: ERR_UNKNOWN
        }
    }

    private fun areaValidation(grid: Pair<Int, Int>, positions: List<Pos>): List<Pos> {
        return positions.filter { pos ->
            grid.first > pos.x && grid.second > pos.y
        }
    }
}