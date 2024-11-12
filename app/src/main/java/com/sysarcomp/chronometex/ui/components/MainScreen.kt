package com.sysarcomp.chronometex.ui.components

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sysarcomp.chronometex.ui.logic.startChronometer
import kotlinx.coroutines.delay

@Composable
fun MainScreen() {

    var isRunning by remember { mutableStateOf(false) }
    var formattedTime by remember { mutableStateOf("00:00") }
    var savedElapsedTime by remember { mutableStateOf(0L) }
    var startTime by remember { mutableStateOf(0L) }

    // Usamos mutableStateListOf para que la lista sea observable y se actualice cuando cambie
    val recordedTimeListt = remember { mutableStateListOf<String>() }

    // LazyListState para controlar el desplazamiento
    val listState = rememberLazyListState()


    // Empezar cronómetro
    LaunchedEffect(isRunning) {
        startTime = System.currentTimeMillis()
        while (isRunning) {
            val elapsedTime = startChronometer(isRunning, savedElapsedTime, startTime)
            formattedTime = String.format("%02d:%02d", elapsedTime / 1000, elapsedTime % 100)
            delay(10L)
        }
    }

    // Desplazar automáticamente cuando se agrega un nuevo elemento
    LaunchedEffect(recordedTimeListt.size) {

        if (recordedTimeListt.size != 0) {

            // Desplazar a la última posición
            listState.animateScrollToItem(recordedTimeListt.size - 1)

        }

    }


    // Llamar a los composables con las acciones necesarias
    ChronometerUI(
        formattedTime = formattedTime,
        onStartClick = { isRunning = true },
        onStopClick = {
            isRunning = false
            savedElapsedTime += System.currentTimeMillis() - startTime
        },
        onLapClick = { recordedTimeListt.add(formattedTime) },
        onResetClick = {
            isRunning = false
            formattedTime = "00:00"
            savedElapsedTime = 0L
            recordedTimeListt.clear()
        }, recordedTimeListt, listState
    )
}