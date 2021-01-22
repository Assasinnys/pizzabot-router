package com.itechart.pizzabotrouter.parser

import com.itechart.pizzabotrouter.util.ERR_BAD_POS
import com.itechart.pizzabotrouter.util.ERR_WRONG_BRACES
import com.itechart.pizzabotrouter.model.Pos
import com.itechart.pizzabotrouter.util.ERR_GRID_NOT_FOUND
import java.lang.NumberFormatException

class InputParser(private val bracesPair: Pair<Char, Char> = '(' to ')') : InputStringParser {

    private val ignoreIndexes = arrayListOf<Int>()

    override fun parsePositions(inputString: String): List<Pos> {

        val wordsInBraces = arrayListOf<String>()

        for (i in inputString.indices) {
            val c = inputString[i]

            if (ignoreIndexes.contains(i)) continue

            when (c) {
                bracesPair.first -> {
                    val s = findCloseBrace(inputString.substring(i.plus(1)), bracesPair, i)
                    ignoreIndexes.add(i)
                    wordsInBraces.add(s)
                }
            }
        }

        ignoreIndexes.clear()
        return wordsInBraces.toCoordinates()
    }

    override fun parseGrid(inputString: String): Pair<Int, Int> {
        val stringGrid: String = inputString.split(" ").find {
            it.contains("x")
        } ?: ""

        try {
            val axisSizes = stringGrid.split("x")
            if (axisSizes.size == 2) {
                return axisSizes[0].trim().toInt() to axisSizes[1].trim().toInt()
            } else
                throw IllegalArgumentException(ERR_GRID_NOT_FOUND)

        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(ERR_GRID_NOT_FOUND)
        }
    }

    private fun findCloseBrace(
        substring: String,
        bracesPair: Pair<Char, Char>,
        lengthBefore: Int
    ): String {
        var counter = 0

        for (i in substring.indices) {
            val c = substring[i]

            if (ignoreIndexes.contains(i.plus(lengthBefore)))
                continue

            when (c) {
                bracesPair.first -> counter++
                bracesPair.second -> {
                    if (counter == 0) {
                        ignoreIndexes.add(i.plus(lengthBefore))
                        return substring.substring(0, i)
                    } else counter--
                }
            }
        }

        throw IllegalArgumentException(ERR_WRONG_BRACES)
    }

    private fun List<String>.toCoordinates(): List<Pos> {
        return map { stringPos ->
            val split = stringPos.split(",")
            if (split.size == 2) {
                try {
                    Pos(split[0].trim().toInt(), split[1].trim().toInt())
                } catch (e: NumberFormatException) {
                    throw IllegalArgumentException(ERR_BAD_POS)
                }
            } else {
                throw IllegalArgumentException(ERR_BAD_POS)
            }
        }
    }
}
