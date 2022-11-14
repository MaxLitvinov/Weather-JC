package com.jc.weather.logger

interface Logger {

    /**
     * Init tree(s) once in the [com.jc.weather.logger.di.LoggerModule]
     */
    fun initTrees()

    fun report(message: String)

    fun report(throwable: Throwable)
}
