package com.sysarcomp.chronometex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ChronometerUI(
    formattedTime: String,
    onStartClick: () -> Unit,
    onStopClick: () -> Unit,
    onLapClick: () -> Unit,
    onResetClick: () -> Unit,
    recordedTimeListt: SnapshotStateList<String>,
    listState: LazyListState
) {
    Column(
        modifier = Modifier
            .padding(top = 120.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = formattedTime, fontSize = 75.sp, fontFamily = FontFamily.Monospace)


        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Buttons(onStartClick, onStopClick, onLapClick, onResetClick)


            // LazyColumn con altura limitada
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally, state = listState
            ) {
                items(recordedTimeListt) { time ->
                    Text(text = time, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }
            }
        }


    }
}