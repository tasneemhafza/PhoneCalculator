package com.example.phonecalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val buttonList = listOf(
    "C", "(", ")", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "+",
    "1", "2", "3", "-",
    "AC", "0", ".", "=",
)

@Composable
fun Calculator(modifier: Modifier = Modifier, viewModel: CalculatorViewModel) {

    val equationText= viewModel.equationText.observeAsState()
    val resultText= viewModel.resultText.observeAsState()

    Box(modifier=Modifier.fillMaxSize()){
        Column (
            modifier=Modifier.align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.End,
        ){
            Text(text = equationText.value?:"",
                modifier=Modifier.padding(30.dp),

                style = TextStyle(
                    fontSize = 30.sp, textAlign = TextAlign.End
                ),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )


            Text(text = if (resultText.value == "0") "0" else {
                val formattedResult = if (viewModel.operation == "/") {
                    // Format the result to 5 decimal places only if division is used
                    "%.5f".format(resultText.value?.toDoubleOrNull() ?: 0.0)
                } else {
                    // Otherwise, show the result without decimals
                    resultText.value
                }
                "= $formattedResult"
            },
                style = TextStyle(
                    fontSize = 55.sp,
                    textAlign = TextAlign.End
                ),
                maxLines = 2
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(buttonList){
                    CalculatorButton(btn = it, onClick = {
                        viewModel.onButtonClick(it)
                    })
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(btn : String, onClick: ()-> Unit) {
    val containerColour = if (btn == "=") Color(0xFFED8114) else Color(0xFF242424)
    val fontSize= if (btn in listOf("+", "*","-", "=", )) 50.sp else 35.sp
    Box(modifier = Modifier.padding(8.dp)) {
        FloatingActionButton(
            onClick = { onClick() },
            modifier = Modifier.size(70.dp),
            containerColor = containerColour,
            contentColor = getColor(btn)
        ) {
            Text(text = btn, style = TextStyle(
                fontSize = fontSize, textAlign = TextAlign.Center
            ))
        }
    }
}

@Composable
fun getColor(btn : String) : Color{
    if(btn == "AC" || btn == "C" || btn == "+" || btn == "-" || btn == "*" || btn == "/"
        || btn == "(" || btn == ")") {
        return Color(0xffed8114)
    }
    return Color.White
}