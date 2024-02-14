package com.example.dietideals_app.view


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.dietideals_app.ui.theme.DietidealsappTheme

class PaginaGestioneAste : ComponentActivity() {
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
                        composable("SchermataOfferte") { SchermataOfferte(navController = navController) }

                    }
                }
            }
        }

    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataGestioneAste(navController: NavController) {


    val colorGreen = 0xFF0EA639
    val colorRed = 0xFF9B0404
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val tabNames = listOf("ASTE ATTIVE", "ASTE CONCLUSE", "ASTE SEGUITE", "ASTE VINTE")
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (background) = createRefs()
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
        ) {
            TopAppBar(modifier = Modifier.align(Alignment.TopCenter), title = {
                Text(
                    text = "LE MIE ASTE",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold, // Imposta il grassetto
                    fontSize = 40.sp
                ) // Imposta la dimensione del testo)
            },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("SchermataHome")


                            }
                            .size(35.dp)
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


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .offset(y = 60.dp)
            ) {
                // TabRow per contenere le schede
                TabRow(selectedTabIndex.intValue) {
                    // Itera attraverso le schede e le aggiunge a TabRow
                    for (index in tabNames.indices) {
                        Tab(
                            selected = selectedTabIndex.intValue == index,
                            onClick = { selectedTabIndex.intValue = index },
                            // Puoi personalizzare l'aspetto delle schede qui, ad esempio aggiungendo icone, testo, etc.
                            text = {
                                Text(text = tabNames[index], fontSize = 10.sp)
                            }
                        )
                    }
                }
                @Composable
                fun ProductDetails() {
                    LazyColumn(
                        modifier = Modifier


                            .fillMaxSize()
                            .padding(16.dp)


                    ) {
                        items(6) { index ->
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
                                            .weight(1f)
                                            .height(150.dp)
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
                                                        .size(150.dp)
                                                        .padding(8.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Column {
                                                // Titolo in grassetto
                                                Text(
                                                    text = "SCARPE NIKE",
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 10.sp,

                                                    )

                                                //TAB ASTE ATTIVE
                                                if (selectedTabIndex.intValue == 0) {
                                                    Text(
                                                        text = "Offerta Iniziale",
                                                        color = Color.Black,
                                                        fontSize = 10.sp,

                                                        )
                                                }


                                                ///TAB ASTE CONCLUSE
                                                if (selectedTabIndex.intValue == 1) {
                                                    Text(
                                                        text = "Venduto per",
                                                        color = Color.Black,
                                                        fontSize = 10.sp,

                                                        )

                                                }
                                                //TAB ASTE SEGUITE
                                                /*
                                                if(offerta NON rifiutata){
                                                Text(
                                                        text = "Offerta Attuale",
                                                        color = Color.Black,
                                                        fontSize = 10.sp,

                                                        )

                                                }
                                                else{
                                                Text(
                                                        text = "La Tua Offerta",
                                                        color = Color.Black,
                                                        fontSize = 10.sp,

                                                        )
                                                }
                                                *
                                                *
                                                *
                                                *
                                                *
                                                * */

                                                //TAB ASTE VINTE
                                                if (selectedTabIndex.intValue == 3) {
                                                    Text(
                                                        text = "Acquistato per",
                                                        color = Color.Black,
                                                        fontSize = 10.sp,

                                                        )

                                                }
                                                //TAB ASTE SEGUITE
                                                /*
                                                if(offerta NON rifiutata){
                                                Text(
                                                    text = "€100,00",
                                                    color = Color(colorGreen),
                                                    fontSize = 10.sp,

                                                    )

                                                }
                                                else{
                                                Text(
                                                    text = "€100,00 RIFIUTATA",
                                                    color = Color(colorRed),
                                                    fontSize = 10.sp,

                                                    )
                                                }
                                                *
                                                *
                                                *
                                                *
                                                *
                                                * */


                                                // Prezzo in verde
                                                Text(
                                                    text = "€100,00",
                                                    color = Color(colorGreen),
                                                    fontSize = 10.sp,

                                                    )
                                                Text(
                                                    text = "Categoria: Abbigliamento",
                                                    color = Color.Black,
                                                    fontSize = 10.sp,

                                                    )

                                                // Altre informazioni

                                                if ((selectedTabIndex.intValue % 2) != 0) {
                                                    Text(
                                                        text = "Conclusa il 08/11/23",
                                                        color = Color(colorRed),
                                                        fontSize = 10.sp,

                                                        )
                                                } else {
                                                    Text(
                                                        text = "Tempo rimanente : 2 Giorni",
                                                        color = Color.Black,
                                                        fontSize = 10.sp,

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

                // Contenuto della scheda selezionata
                when (selectedTabIndex.intValue) {
                    0 -> {
                        ProductDetails()

                    }


                    1 -> {
                        ProductDetails()

                    }

                    2 -> {
                        ProductDetails()
                    }

                    3 -> {
                        ProductDetails()
                    }
                }
            }
            @Composable
            fun IconWithText(iconId: Int, text: String, route: String) {
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
                        tint = Color(0xFF0EA639)

                    )

                    Text(
                        text = text,
                        fontSize = 14.sp,
                        color = Color(0xFF0EA639)
                    )//
                }
            }

            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()

                    .background(color = Color.White) // Set the background color to white
                    .border(1.dp, color = Color.Black)
                    .height(60.dp)// Add a black border
                    .align(Alignment.BottomCenter)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                IconWithText(
                    iconId = R.drawable.baseline_home_24,
                    text = "Home",
                    "SchermataHome"
                )


                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                IconWithText(
                    iconId = R.drawable.line_chart_svgrepo_com,
                    text = "Gestisci Aste",
                    ""
                )



                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                IconWithText(
                    iconId = R.drawable.hand_money_cash_hold_svgrepo_com,
                    text = "Crea Asta",
                    ""
                )


                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                IconWithText(
                    iconId = R.drawable.baseline_manage_accounts_24,
                    text = "Profilo",
                    "SchermataProfiloUtente"
                )


                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSchermataGestioneAste() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataGestioneAste(navController)
    }
}

