package com.example.dietideals_app.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.presenter.AutenticazionePresenter
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.ui.theme.titleCustomFont
import kotlin.random.Random

class PaginaSchermataHome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "SchermataAutenticazione") {
                        composable("SchermataAutenticazione") { SchermataAutenticazione(navController = navController) }
                        composable("SchermataRegistrazione") { SchermataRegistrazione(navController = navController) }
                        composable("SchermataImmagineProfilo") { SchermataImmagineProfilo(navController = navController) }
                        composable("SchermataDiventaVenditore") { SchermataDiventaVenditore(navController = navController) }
                        composable("SchermataRegistrazioneSuccesso") { SchermataRegistrazioneSuccesso(navController = navController) }
                        composable("SchermataRegistrazioneDatiVenditore"){SchermataRegistrazioneDatiVenditore(navController = navController)}
                        composable("SchermataHome"){SchermataHome(navController = navController)}
                    }
                }
            }
        }

    }
}

@Composable
fun SchermataHome(navController: NavController) {
    val logoApp = painterResource(id = R.drawable.iconaapp)
    var isSearchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isNotificationVisible by remember { mutableStateOf(false)}

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val (notificationBar, notificationBoxes, header, subTitle, buttons, boxes) = createRefs()




        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
                .constrainAs(header) {
                    top.linkTo(parent.top)
                }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Testo e icona quando la barra di ricerca non è visibile
                Image(
                    painter = logoApp,
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (isSearchVisible) {
                    // Barra di ricerca
                    TextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            // Aggiungi il codice per gestire la ricerca qui
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .height(30.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        placeholder = { Text("Ricerca Asta...", color = Color.Black) }


                    )
                } else {

                    Text(
                        text = "DIETIDEALS 24",
                        color = Color.White,
                        fontFamily = titleCustomFont,
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "searchButton",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                // Cambia lo stato per mostrare/nascondere la barra di ricerca
                                isSearchVisible = !isSearchVisible
                            }
                    )

                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(painter = painterResource(id = R.drawable.baseline_notifications_24),
                    contentDescription = "notificationButton",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { isNotificationVisible = !isNotificationVisible })
            }
        }

        Row(
            modifier = Modifier
                .constrainAs(subTitle) {
                    top.linkTo(header.bottom, margin = 5.dp)
                }
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Aste in Corso",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                modifier = Modifier.weight(1f),
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = "filterButton",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(30.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .constrainAs(boxes) {
                    top.linkTo(subTitle.bottom, margin = 200.dp)
                    bottom.linkTo(buttons.top, margin = 50.dp)
                }
                .fillMaxSize()
                .padding(16.dp)
                .let {
                    if (isSearchVisible || isNotificationVisible) {
                        it.clickable {
                            isSearchVisible = false
                            isNotificationVisible = false
                            searchText = "" // Resetta il testo di ricerca
                        }
                    } else {
                        it
                    }
                }

        ) {
            items(6) { index ->
                // Colonna per ogni elemento della lista
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        // Riquadro per ogni elemento della lista
                        Box(
                            modifier = Modifier
                                .weight(1f) // Assegna un peso uguale alle Box
                                .height(200.dp)
                                .padding(8.dp)
                                .background(Color.White)
                                .border(1.dp, Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Immagine
                                Image(
                                    painter = painterResource(id = R.drawable.defaultimage),
                                    contentDescription = "Image",
                                    modifier = Modifier
                                        .size(100.dp)
                                )

                                // Titolo
                                Text(
                                    text = "Elemento $index-A",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(4.dp)
                                )

                                // Bottone
                                Button(
                                    onClick = { /* Azione del bottone */ },
                                    modifier = Modifier.padding(4.dp),
                                    shape = RoundedCornerShape(0, 0)
                                ) {
                                    Text(
                                        String.format("€%.2f", 80.0 + Random.nextDouble() * 9.0),
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        // Riquadro per ogni elemento della lista
                        Box(
                            modifier = Modifier
                                .weight(1f) // Assegna un peso uguale alle Box
                                .height(200.dp)
                                .padding(8.dp)
                                .background(Color.White)
                                .border(1.dp, Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Immagine
                                Image(
                                    painter = painterResource(id = R.drawable.defaultimage),
                                    contentDescription = "Image",
                                    modifier = Modifier
                                        .size(100.dp)
                                )

                                // Titolo
                                Text(
                                    text = "Elemento $index-B",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(4.dp)
                                )

                                // Bottone
                                Button(
                                    onClick = { /* Azione del bottone */ },
                                    modifier = Modifier,
                                    shape = RoundedCornerShape(0, 0)
                                ) {
                                    Text(
                                        text = String.format(
                                            "€%.2f",
                                            80.0 + Random.nextDouble() * 9.0
                                        ),
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
        val homePressed by remember { mutableStateOf(false) }
        val manageAuctionsPressed by remember { mutableStateOf(false) }
        val createAuctionPressed by remember { mutableStateOf(false) }
        val profilePressed by remember { mutableStateOf(false) }


        @Composable
        fun IconWithText(iconId: Int, text: String) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(2.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color(0xFF0EA639)
                )

                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = Color(0xFF0EA639)
                )//
            }
        }

        Box(
            modifier = Modifier
                .constrainAs(buttons) {
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White)
                .border(0.5.dp, Color.Black)
                .padding(8.dp)


        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconWithText(iconId = R.drawable.baseline_home_24, text = "Home")
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                IconWithText(iconId = R.drawable.line_chart_svgrepo_com, text = "Gestisci Aste")
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                IconWithText(
                    iconId = R.drawable.hand_money_cash_hold_svgrepo_com,
                    text = "Crea Asta"
                )
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                IconWithText(iconId = R.drawable.baseline_manage_accounts_24, text = "Profilo")
            }
        }
        if (isNotificationVisible) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(200.dp)
                .background(Color.White)
                .constrainAs(notificationBar)
                {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .border(0.5.dp, Color.Black)
            ) {
               Row( modifier = Modifier
                   .fillMaxWidth()
                   .padding(8.dp),) {
                   Icon(
                       painter = painterResource(id = R.drawable.trash_svgrepo_com),
                       contentDescription = null,
                       modifier = Modifier
                           .size(30.dp),
                       tint = Color.Black
                   )
                   Spacer(modifier = Modifier.width(8.dp))
                   Text(text = "Notifiche",fontSize = 20.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                   Spacer(modifier = Modifier.width(8.dp))
                   Icon(
                       painter = painterResource(id = R.drawable.baseline_close_24),
                       contentDescription = null,
                       modifier = Modifier
                           .size(30.dp),
                       tint = Color(0xFF9B0404)
                   )


               }
                Spacer(modifier = Modifier.height(30.dp))
                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ){

                    items(6) { index ->
                        // Colonna per ogni elemento della lista
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                // Riquadro per ogni elemento della lista
                                Box(
                                    modifier = Modifier
                                        .weight(1f) // Assegna un peso uguale alle Box
                                        .height(100.dp)
                                        .padding(2.dp)
                                        .background(Color.White)
                                        .border(1.dp, Color.Black),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Column {
                                            // Immagine
                                            Image(
                                                painter = painterResource(id = R.drawable.defaultimage),
                                                contentDescription = "Image",
                                                modifier = Modifier
                                                    .size(50.dp)
                                            )
                                        }
                                        // Titolo
                                        Text(
                                            text = "Hai Vinto l'asta $index Scarpe Adidas per € 40",
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            modifier = Modifier
                                                .padding(4.dp)
                                        )


                                    }
                                }

                            }

                        }
                    }

                }


            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSchermataHome() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataHome(navController)
    }
}