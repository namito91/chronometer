package com.sysarcomp.chronometex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sysarcomp.chronometex.ui.components.MainScreen
import com.sysarcomp.chronometex.ui.theme.ChronometexTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChronometexTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Box(modifier = Modifier.padding(innerPadding)) {

                        MainScreen()

                        //ChronometerScreen()
                    }
                }
            }
        }
    }
}


@Composable
fun ChronometerScreen() {

    var isRunning by remember { mutableStateOf(false) }
    var formattedTime by remember { mutableStateOf("00:00") }
    var savedElapsedTime by remember { mutableStateOf(0L) }  // Tiempo acumulado al detener
    var startTime by remember { mutableStateOf(0L) }  // Tiempo de inicio de la ejecución actual

    // Usamos mutableStateListOf para que la lista sea observable y se actualice cuando cambie
    val recordedTimeListt = remember { mutableStateListOf<String>() }

    // LazyListState para controlar el desplazamiento
    val listState = rememberLazyListState()


    // Efecto que ejecuta el incremento de tiempo cada segundo cuando está activo
    /*Se ejecuta cuando los valores de las claves de dependencia (parámetros) cambian
    Si el valor de la clave cambia, el bloque de código de LaunchedEffect se reinicia.
    LaunchedEffect(isRunning) observa el estado de isRunning. Cada vez que isRunning cambia,
    el bloque de código dentro de LaunchedEffect se vuelve a ejecutar.*/

    LaunchedEffect(isRunning) {

        startTime = System.currentTimeMillis()  // Tiempo inicial en milisegundos

        while (isRunning) {

            val currentTime = System.currentTimeMillis()

            // Calcula el tiempo total transcurrido (tiempo de ejecución acumulado + tiempo actual - tiempo de inicio)
            val elapsedTime = savedElapsedTime + (currentTime - startTime)


            Log.i("patrox", elapsedTime.toString())

            // Calcula los segundos y milisegundos
            val seconds = (elapsedTime!! / 1000) % 60
            val milliseconds = elapsedTime!! % 100

            // Formatea el texto en "00s:000ms"
            formattedTime = String.format("%02d:%02d", seconds, milliseconds)

            // Aquí puedes actualizar un estado o mostrar el `formattedTime`
            //println(formattedTime) // Esto es solo un ejemplo, en Compose usarías un Text() o similar
            delay(10L)

        }
    }


    // Interfaz de usuario
    Column(
        modifier = Modifier
            .padding(top = 200.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = formattedTime, fontSize = 65.sp, fontFamily = FontFamily.Monospace)

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(45.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                // Botón START
                Button(
                    onClick = { isRunning = true },
                    shape = CircleShape,
                    modifier = Modifier.size(90.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF039BE5), Color.White),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text("START", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                // Botón STOP
                Button(
                    onClick = {
                        isRunning = false
                        savedElapsedTime += System.currentTimeMillis() - startTime
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(90.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFF4511E), Color.White),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text("STOP", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            // Botón Lap
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Button(
                    onClick = {
                        recordedTimeListt.add(formattedTime)
                    },
                    shape = RoundedCornerShape(3.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF6B6A67), Color.White)
                ) {
                    Text("Lap")
                }
            }

            // Botón RESET
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Button(
                    onClick = {
                        isRunning = false
                        formattedTime = "00:00"
                        savedElapsedTime = 0L
                        recordedTimeListt.clear()
                    },
                    shape = RoundedCornerShape(3.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF6B6A67), Color.White)
                ) {
                    Text("RESET")
                }
            }

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

            // Desplazar automáticamente cuando se agrega un nuevo elemento
            LaunchedEffect(recordedTimeListt.size) {
                // Desplazar a la última posición
                listState.animateScrollToItem(recordedTimeListt.size - 1)
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun ChronometerScreenPreview() {
    ChronometerScreen()
}
