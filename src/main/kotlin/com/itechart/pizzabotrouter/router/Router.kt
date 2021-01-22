package com.itechart.pizzabotrouter.router

import com.itechart.pizzabotrouter.model.Pos

interface Router {
    fun getRoute(clients: List<Pos>): String
}