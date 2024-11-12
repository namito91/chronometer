package com.sysarcomp.chronometex.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Buttons(
    onStartClick: () -> Unit,
    onStopClick: () -> Unit,
    onLapClick: () -> Unit,
    onResetClick: () -> Unit
) {

    // start , stop buttons
    Row(
        horizontalArrangement = Arrangement.spacedBy(45.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {

        // Botón START
        Button(
            onClick = onStartClick,
            shape = CircleShape,
            modifier = Modifier.size(90.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF039BE5), Color.White),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("START", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        // Botón STOP
        Button(
            onClick = onStopClick,
            shape = CircleShape,
            modifier = Modifier.size(90.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFF4511E), Color.White),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("STOP", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }


    // lap button
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 20.dp)
    ) {

        Button(
            onClick = onLapClick,
            shape = RoundedCornerShape(3.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF6B6A67), Color.White)
        ) {
            Text("Lap", fontWeight = FontWeight.Bold)
        }

    }

    // reset button
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 20.dp)
    ) {

        Button(
            onClick = onResetClick,
            shape = RoundedCornerShape(3.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF6B6A67), Color.White)
        ) {
            Text("Reset", fontWeight = FontWeight.Bold)
        }
    }

}