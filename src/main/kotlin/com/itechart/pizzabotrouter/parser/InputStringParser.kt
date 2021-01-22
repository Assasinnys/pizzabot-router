package com.itechart.pizzabotrouter.parser

import com.itechart.pizzabotrouter.model.Pos

interface InputStringParser {
    fun parsePositions(inputString: String): List<Pos>
    fun parseGrid(inputString: String): Pair<Int, Int>
}