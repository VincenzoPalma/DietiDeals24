package com.example.dietideals_app.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class PaginaAsta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SchermataPaginaAsta(navController)

                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataPaginaAsta(navController: NavController) {
    var timeLeft by rememberSaveable { mutableLongStateOf(0L) }

    fun formatTime(timeMillis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(timeMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


    fun resetTimer() {
        val inputText = "00:04:30" // Imposta qui il tempo iniziale desiderato
        val time = inputText.split(":")
        if (time.size == 3) {
            val hours = time[0].toLongOrNull() ?: 0
            val minutes = time[1].toLongOrNull() ?: 0
            val seconds = time[2].toLongOrNull() ?: 0
            timeLeft = TimeUnit.HOURS.toMillis(hours) +
                    TimeUnit.MINUTES.toMillis(minutes) +
                    TimeUnit.SECONDS.toMillis(seconds)
        }
    }


    val defaultImage = painterResource(id = R.drawable.defaultimage)
    val colorGreen = 0xFF0EA639
    val colorRed = 0xFF9B0404
    val context = LocalContext.current
    val currentPage = remember { mutableIntStateOf(0) }
    var openDialog = remember { mutableStateOf(false) }
    var offerta by remember { mutableStateOf("") }
    val aste = listOf("All'inglese", "Silenziosa", "Inversa")
    val tipoAsta = aste.random()

    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Handle the result if needed
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (background, topBar, asta) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(background) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(Color.White)
        )
        TopAppBar(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, title = {
            Text(
                text = "DETTAGLI ASTA",
                textAlign = TextAlign.Center,

                fontWeight = FontWeight.Bold, // Imposta il grassetto
                fontSize = 40.sp
            ) // Imposta la dimensione del testo)
        },
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { navController.navigate("SchermataHome") }
                )
            }, colors = TopAppBarColors(
                containerColor = (MaterialTheme.colorScheme.primary),
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
                scrolledContainerColor = Color.White
            ),
            actions = {
            }
        )
        ElevatedCard(
            modifier = Modifier
                .fillMaxHeight()
                .constrainAs(asta) {
                    top.linkTo(topBar.bottom)
                    bottom.linkTo(parent.bottom)
                }
                .padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 45.dp)
                ,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ), content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.defaultimage),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp
                                )
                            )
                            .border(
                                1.dp, Color.Black, shape = RoundedCornerShape(
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp
                                )
                            )
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Scarpe Nike", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "di", fontSize = 20.sp, fontWeight = FontWeight.Bold)



                    TextButton(
                        modifier = Modifier.padding(start = 5.dp),
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(key = "idUtente", value = "76c21822-0ca8-4a31-a296-1aa48092bc0e") //inserire id dell'utente proprietario dell'asta
                            navController.navigate("SchermataProfiloUtente")
                        },

                        ) {
                        Text(text = "@marioRossi", fontSize = 15.sp)

                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "RetroFit Air 20XX: esclusiva, leggera, ad alte prestazioni. " +
                                "L'ultima tecnologia Nike incontra il design classico." +
                                " Perfetta per gli amanti dello stile e dell'azione. Offri ora e vola con stile.",
                        fontSize = 15.sp
                    )


                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_category_24),
                        contentDescription = null
                    )
                    Text(text = "Abbigliamento", fontSize = 15.sp)
                    Spacer(Modifier.width(80.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.auction_hammer),
                        contentDescription = null
                    )
                    Text(text = tipoAsta, fontSize = 15.sp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_attach_money_24),
                        contentDescription = null
                    )
                    Text(text = "OFFERTA ATTUALE €35", color = MaterialTheme.colorScheme.primary)
                    if (tipoAsta == "All'inglese" || tipoAsta == "Inversa") {
                        val text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 15.sp
                                )
                            ) {
                                append("Luca Gialli")
                                addStringAnnotation("URL", "https://www.example.com", 0, length)
                            }
                        }
                        Text(text = " di")
                        TextButton(

                            onClick = {
                                navController.navigate("SchermataProfiloUtente")
                            },

                            ) {
                            Text(text = "@LucaGialli", fontSize = 15.sp)

                        }
                    }
                }
                if (tipoAsta == "All'inglese") {
                    var timerRunning by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Absolute.Left,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.growth),
                            contentDescription = null
                        )
                        Text(text = " SOGLIA DI RIALZO €10", fontSize = 15.sp)
                    }
                    LaunchedEffect(Unit) {

                        val inputText = "00:04:30" // Inserisci qui il tempo iniziale desiderato
                        val time = inputText.split(":")
                        if (time.size == 3) {
                            val hours = time[0].toLongOrNull() ?: 0
                            val minutes = time[1].toLongOrNull() ?: 0
                            val seconds = time[2].toLongOrNull() ?: 0
                            val totalTime = TimeUnit.HOURS.toMillis(hours) +
                                    TimeUnit.MINUTES.toMillis(minutes) +
                                    TimeUnit.SECONDS.toMillis(seconds)
                            if (totalTime > 0) {
                                timeLeft = totalTime
                                timerRunning = true
                                while (timeLeft > 0 && timerRunning) {
                                    delay(1000)
                                    timeLeft -= 1000
                                }
                                timerRunning = false
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = if (tipoAsta == "All'inglese") R.drawable.baseline_access_time_filled_24 else R.drawable.baseline_calendar_month_24),
                        contentDescription = null
                    )
                    Text(
                        if (tipoAsta == "All'inglese") "Tempo rimanente: " else {
                            "Data di scadenza"
                        }, fontSize = 15.sp, color = Color(colorRed)
                    )
                    when (tipoAsta) {
                        "All'inglese" -> {
                            Text(
                                formatTime(timeLeft),
                                fontSize = 15.sp,
                                modifier = Modifier.padding(start = 4.dp), color = Color(colorRed)
                            )
                        }

                        "Inversa" -> {
                            Text(
                                "10/03/2024", //ToDo da sostituire con la data di scadenza dell'asta
                                fontSize = 15.sp,
                                modifier = Modifier.padding(start = 4.dp), color = Color(colorRed)
                            )
                        }

                        else -> {
                            Text(
                                "10/03/2024 22:00", //ToDo da sostituire con la data di scadenza dell'asta
                                fontSize = 15.sp,
                                modifier = Modifier.padding(start = 4.dp), color = Color(colorRed)
                            )

                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Absolute.Right,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ElevatedButton(colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.Gray
                    ), onClick = {
                        if (tipoAsta != "Silenziosa") openDialog.value = true
                        else {
                            navController.navigate("SchermataOfferte")
                        }
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = if (tipoAsta != "Silenziosa") R.drawable.hand_money_cash_hold_svgrepo_com else R.drawable.baseline_remove_red_eye_24),
                                contentDescription = null
                            )
                            Text(text = if (tipoAsta != "Silenziosa") "FAI UN OFFERTA" else "VISUALIZZA OFFERTE")
                        }

                    }

                }
            })














        if (openDialog.value) {

            when (currentPage.intValue) {
                0 -> {
                    BasicAlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ElevatedCard(
                            modifier = Modifier

                                .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                            content = {
                                Row {
                                    TopAppBar(
                                        title = {
                                            Text(
                                                "FAI UN OFFERTA ",
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                fontWeight = FontWeight.Bold, // Imposta il grassetto
                                                fontSize = 20.sp
                                            )
                                        },
                                        colors = TopAppBarColors(
                                            containerColor = (MaterialTheme.colorScheme.primary),
                                            navigationIconContentColor = Color.White,
                                            titleContentColor = Color.White,
                                            actionIconContentColor = Color.White,
                                            scrolledContainerColor = Color.White
                                        )
                                    )

                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically, modifier =
                                    Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "LA TUA OFFERTA  €",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    OutlinedTextField(
                                        value = offerta,
                                        onValueChange = { offerta = it },
                                        singleLine = true
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Absolute.Right,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    ElevatedButton(colors = ButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = Color.White,
                                        disabledContentColor = Color.Gray,
                                        disabledContainerColor = Color.Gray
                                    ), onClick = { currentPage.intValue = 1 }) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_done_24),
                                                contentDescription = null
                                            )
                                            Text(text = "CONFERMA")
                                        }

                                    }

                                }


                            })
                    }
                }


                1 -> {
                    AlertDialog(
                        onDismissRequest = { currentPage.intValue = 0 },
                        title = {
                            Text(
                                text = "Attenzione!",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        text = {
                            Text(text = "STAI OFFRENDO €$offerta. SEI SICURO DI PROCEDERE?")
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    currentPage.intValue = 2
                                }


                            ) {
                                Text(text = "Continua", fontSize = 15.sp)
                            }

                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    currentPage.intValue = 0
                                }, modifier = Modifier.padding(end = 100.dp)
                            ) {
                                Text(
                                    text = "Annulla",
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    )
                }

                2 -> {
                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = {
                            Text(
                                text = "CONGRATUALZIONI",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        text = {
                            Text(text = "LA TUA OFFERTA E' ANDATA A BUON FINE! POTRAI SEGUIRLA NELLA SEZIONE ASTE SEGUITE")
                        },
                        confirmButton = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextButton(
                                    onClick = {
                                        currentPage.intValue = 0
                                        openDialog.value = false
                                    }


                                ) {
                                    Text(text = "Continua", fontSize = 15.sp)
                                }

                            }

                        }


                    )
                }
            }


        }
    }
}


@Preview(showBackground = false)
@Composable
fun PreviewSchermataPaginaAsta() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataPaginaAsta(navController)
    }
}
