package com.example.a2020102527_main_exam_project.ui.canvas
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/*
This code defines a composable function CanvasScreen() using
 Jetpack Compose to create a drawing canvas.
 It allows users to draw lines on the canvas with touch gestures, clear the canvas,
  and displays a toast message when the canvas is cleared.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState")
@Composable
fun CanvasScreen() {
    val lines by remember { mutableStateOf(mutableStateListOf<Line>()) }
    val context = LocalContext.current
    var isTrue by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.padding(0.dp,70.dp,20.dp,0.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = { isTrue = true }) {
                Text(text = "Draw on Canvas")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { isTrue = true }) {
                    Text(text = "Clear Canvas")
                }

            }
        }

        if (isTrue) {
            // Clear lines after saving
            lines.clear()
            isTrue = false
            Toast.makeText(context, "Canvas Cleared", Toast.LENGTH_SHORT).show()
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .pointerInput(true) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()

                        val line = Line(
                            start = change.position - dragAmount,
                            end = change.position
                        )

                        lines.add(line)
                    }
                }
        ) {
            lines.forEach { line ->
                drawLine(
                    color = line.color,
                    start = line.start,
                    end = line.end,
                    strokeWidth = line.strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

    }
}

data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 1.dp
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CanvasScreen()
}
