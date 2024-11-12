package com.sysarcomp.chronometex.ui.logic

fun startChronometer(isRunning: Boolean, savedElapsedTime: Long, startTime: Long): Long {
    if (isRunning) {
        val currentTime = System.currentTimeMillis()
        return savedElapsedTime + (currentTime - startTime)
    }
    return savedElapsedTime
}