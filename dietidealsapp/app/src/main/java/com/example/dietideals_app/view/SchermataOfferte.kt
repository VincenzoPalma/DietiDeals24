package com.example.dietideals_app.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.ui.theme.DietidealsappTheme

class PaginaOfferte : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "SchermataAutenticazione"
                    ) {
                        composable("SchermataAutenticazione") {
                            SchermataAutenticazione(
                                navController = navController
                            )
                        }
                        composable("SchermataRegistrazione") { SchermataRegistrazione(navController = navController) }
                        composable("SchermataHome") { SchermataHome(navController = navController) }
                        composable("SchermataProfiloUtente") { SchermataProfiloUtente(navController = navController) }
                        composable("SchermataModificaProfilo") {
                            SchermataModificaProfilo(
                                navController = navController
                            )
                        }
                        composable("SchermataPagamentiProfilo") {
                            SchermataPagamentiProfilo(
                                navController = navController
                            )
                        }
                        composable("SchermataGestioneAste") { SchermataGestioneAste(navController = navController) }
                        composable("SchermataCreazioneAsta") { SchermataCreazioneAsta(navController = navController) }
                        composable("SchermataPaginaAsta") { SchermataPaginaAsta(navController = navController) }
                        composable("SchermataOfferte") { SchermataOfferte(navController = navController) }

                    }
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataOfferte(navController: NavController) {

    val homeIcon = R.drawable.baseline_home_24
    val gestisciAste = R.drawable.line_chart_svgrepo_com
    val creaAsta = R.drawable.hand_money_cash_hold_svgrepo_com
    val account = R.drawable.baseline_manage_accounts_24
    val colorGreen = 0xFF0EA639
    var isDialogVisible by remember { mutableStateOf(false) }








    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (background, topBar, bottomBar, listaOfferte) = createRefs()

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
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "OFFERTE",
                    fontWeight = FontWeight.Bold, // Imposta il grassetto
                    fontSize = 40.sp
                ) // Imposta la dimensione del testo)

            }

        },
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { navController.navigate("SchermataPaginaAsta") }
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
        @Composable
        fun IconWithText(iconId: Int, text: String, route: String, color: Color) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(2.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { navController.navigate(route) },
                    tint = color

                )

                Text(
                    text = text,
                    fontSize = 15.sp,
                    color = color
                )//
            }
        }

        // Definisci uno stato per tracciare le righe visualizzate
        val righeVisualizzate = remember { mutableStateListOf(*Array(7) { it }) }
        val nomi = remember {
            mutableStateListOf(
                "Mario Rossi", "Giovanni Bianchi", "Luca Verdi", "Paolo Neri",
                "Francesca Ferrari", "Alessandro Russo", "Chiara Esposito"
            )
        }

        val prezzi = remember {
            mutableStateListOf(
                "€100,00", "€150,00", "€200,00", "€250,00",
                "€300,00", "€350,00", "€400,00"
            )
        }
        var nomeSelezionato by remember { mutableStateOf("") }
        var prezzoSelezionato by remember { mutableStateOf("") }

        @Composable
        fun SwipeableRow(
            onSwipeLeft: () -> Unit,
            onSwipeRight: () -> Unit,
            content: @Composable () -> Unit
        ) {
            var offsetX by remember { mutableStateOf(0f) }

            val animatableOffset = remember { androidx.compose.animation.core.Animatable(0f) }
            val offsetState by animateFloatAsState(
                targetValue = offsetX,
                animationSpec = tween(durationMillis = 100), label = ""
            )

            LaunchedEffect(offsetState) {
                animatableOffset.animateTo(offsetState)
            }

            Row(
                modifier = Modifier
                    .graphicsLayer {
                        translationX = animatableOffset.value
                    }
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount
                            },
                            onDragStart = { offsetX = 0f },
                            onDragEnd = {
                                if (offsetX > 0) {
                                    onSwipeRight()
                                } else {
                                    onSwipeLeft()
                                }
                                offsetX = 0f
                            },

                            )
                    }
            ) {
                content()
            }
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(listaOfferte) {
                    top.linkTo(parent.top, margin = 50.dp)

                }
                .padding(16.dp)
        )
        {
            items(righeVisualizzate.size) { index ->
                // Utilizza l'indice per accedere all'elemento corrispondente della lista dei dati


                if (index in righeVisualizzate) {
                    val nome = nomi[index]
                    val prezzo = prezzi[index]
                    SwipeableRow(
                        onSwipeLeft = {
                            // Rimuovi la riga dalla lista delle righe visualizzate quando si fa clic su RIFIUTA
                            righeVisualizzate.removeAt(index)
                            nomi.removeAt(index)
                            prezzi.removeAt(index)
                            // Aggiusta gli indici delle righe visualizzate dopo la rimozione
                            for (i in index until righeVisualizzate.size) {
                                righeVisualizzate[i] -= 1
                            }
                            // Azione da eseguire quando viene effettuato uno swipe verso sinistra
                        },
                        onSwipeRight = {
                            nomeSelezionato = nomi[index]
                            prezzoSelezionato = prezzi[index]
                            isDialogVisible = true
                            // Azione da eseguire quando viene effettuato uno swipe verso destra
                        }
                    ) {

                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            border = BorderStroke(1.dp, Color.Black),

                            ) {

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(
                                    modifier = Modifier.fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(90.dp) // Imposta le dimensioni della Box
                                            .clip(shape = CircleShape)
                                            .fillMaxHeight()
                                            .padding(start = 10.dp)// Applica la forma circolare
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.user_image), // Sostituisci con il tuo ID di immagine
                                            contentDescription = null, // Descrizione del contenuto opzionale
                                            modifier = Modifier.fillMaxSize(), // Riempie completamente lo spazio della Box
                                            // Scala l'immagine per adattarla alla Box, ritagliando l'eventuale eccesso
                                        )
                                    }
                                }

                                Column(Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = nome, fontWeight = FontWeight.Bold)
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = "OFFERTA : ")
                                        Text(
                                            text = prezzo,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        ElevatedButton(
                                            onClick = {
                                                // Rimuovi la riga dalla lista delle righe visualizzate quando si fa clic su RIFIUTA
                                                righeVisualizzate.removeAt(index)
                                                nomi.removeAt(index)
                                                prezzi.removeAt(index)
                                                // Aggiusta gli indici delle righe visualizzate dopo la rimozione
                                                for (i in index until righeVisualizzate.size) {
                                                    righeVisualizzate[i] -= 1
                                                }
                                            },
                                            colors = ButtonColors(
                                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                                contentColor = Color(0xFFBA1A1A),
                                                disabledContentColor = Color.Gray,
                                                disabledContainerColor = Color.Gray
                                            )
                                        ) {
                                            Text(text = "RIFIUTA")
                                        }
                                        Spacer(modifier = Modifier.width(4.dp))
                                        ElevatedButton(
                                            onClick = {
                                                nomeSelezionato = nomi[index]
                                                prezzoSelezionato = prezzi[index]
                                                isDialogVisible = true
                                            },
                                            colors = ButtonColors(
                                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                                contentColor = MaterialTheme.colorScheme.primary,
                                                disabledContentColor = Color.Gray,
                                                disabledContainerColor = Color.Gray
                                            )
                                        ) {
                                            Text(text = "ACCETTA")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp) // Altezza dello Spacer uguale all'altezza della BottomAppBar
                )
            }
        }

        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomBar) {
                    bottom.linkTo(parent.bottom)
                }

                .background(color = Color.White) // Set the background color to white
                .border(1.dp, color = Color.Black)
                .height(60.dp)// Add a black border
        ) {
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )

            IconWithText(
                iconId = homeIcon,
                text = "Home",
                "SchermataHome",
                Color(colorGreen)
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )

            IconWithText(
                iconId = gestisciAste,
                text = "Gestisci Aste",
                "",
                Color(colorGreen)
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )

            IconWithText(
                iconId = creaAsta,
                text = "Crea Asta",
                "",
                Color(colorGreen)
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )

            IconWithText(
                iconId = account,
                text = "Profilo",
                "SchermataProfiloUtente",
                Color(colorGreen)
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
        }

        if (isDialogVisible) {
            Dialog(onDismissRequest = { isDialogVisible = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "OFFERTA ACCETTATA!\n\n" +
                                    "HAI ACCETTATO L'OFFERTA DI " + nomeSelezionato +
                                    "PER " + prezzoSelezionato,
                            textAlign = TextAlign.Center
                        )
                        TextButton(onClick = {
                            isDialogVisible = false
                            navController.navigate("SchermataHome")
                        }) {
                            Text(text = "OK", fontSize = 20.sp)
                        }


                    }
                }
            }

        }


    }


}


@Preview(showBackground = true)
@Composable
fun PreviewSchermataOfferte() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataOfferte(navController)
    }
}