package com.example.dietideals_app.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.Notifica
import com.example.dietideals_app.presenter.SchermataHomePresenter
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
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
    fun SchermataHome(navController: NavController) {
    val logoApp = painterResource(id = R.drawable.iconaapp)
    var isSearchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isNotificationVisible by remember { mutableStateOf(false) }
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    fun generateNotifications(): List<Notifica> {
        // Implementa questa funzione secondo le tue esigenze
        // Restituisce una lista di notifiche
        return listOf(
            //Notifica("Notifica 1", LocalDateTime.now()),
            //Notifica("Notifica 2", LocalDateTime.now()),
            //Notifica("Notifica 3", LocalDateTime.now()),
            //Notifica("Notifica 4", LocalDateTime.now()),
            //Notifica("Notifica 5", LocalDateTime.now()),
            //Notifica("Notifica 6", LocalDateTime.now())
        )
    }

    var notifications by remember { mutableStateOf(generateNotifications()) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.7f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_close_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable {
                                            scope.launch {
                                                drawerState.apply {
                                                    close()
                                                }
                                            }
                                        },
                                    tint = Color(0xFF9B0404)
                                )

                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Notifiche",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.trash_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable { notifications = emptyList() },
                                    tint = Color.Black
                                )

                            }

                            if (notifications.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(16.dp)
                                ) {
                                    items(notifications.size) { notification ->
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
                                                        .height(100.dp)
                                                        .padding(2.dp)
                                                        .background(Color.White)
                                                        .border(1.dp, Color.Black),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                    ) {

                                                        // Titolo
                                                        Text(
                                                            text = "Hai Vinto l'asta $notification\n Scarpe Adidas per € 40",
                                                            color = Color.Black,
                                                            fontWeight = FontWeight.Bold,
                                                            fontSize = 8.sp,
                                                            textAlign = TextAlign.Left,
                                                            modifier = Modifier
                                                                .padding(4.dp)
                                                        )
                                                        Column {
                                                            // Immagine
                                                            Image(
                                                                painter = painterResource(id = R.drawable.defaultimage),
                                                                contentDescription = "Image",
                                                                modifier = Modifier
                                                                    .size(100.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Nessuna Notifica",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic,
                                        color = Color.Gray,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                        }

                    }
                }
            },

            content = {
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


                        TopAppBar(modifier = Modifier.align(Alignment.TopCenter),
                            title = {
                                if (!isSearchVisible) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_search_24),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clickable {
                                                isSearchVisible = !isSearchVisible

                                                // Azione da eseguire quando si clicca sull'icona di ricerca
                                            }
                                            .size(40.dp)


                                    )
                                    Text(
                                        text = "DIETI DEALS 24",
                                        textAlign = TextAlign.Left,
                                        modifier = Modifier
                                            .fillMaxWidth(),

                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp
                                    )

                                } else {
                                    SearchBar(
                                        query = query,
                                        onQueryChange = { nuovaQuery -> query = nuovaQuery },
                                        onSearch = { /* Gestisci l'azione di ricerca */ },
                                        active = isSearchVisible,
                                        onActiveChange = { attiva -> isSearchVisible = attiva }
                                    ) {
                                        TextField(
                                            value = query,
                                            onValueChange = { nuovaQuery -> query = nuovaQuery },
                                            placeholder = { Text("Cerca Asta...") },
                                            modifier = Modifier.height(60.dp)
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarColors(
                                containerColor = (MaterialTheme.colorScheme.primary),
                                navigationIconContentColor = Color.White,
                                titleContentColor = Color.White,
                                actionIconContentColor = Color.White,
                                scrolledContainerColor = Color.White
                            ),

                            actions = {

                                Image(
                                    painter = logoApp,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp),
                                    contentScale = ContentScale.Crop
                                )
                            },
                            navigationIcon = {
                                BadgedBox(badge = { if(notifications.isNotEmpty()){Badge {

                                    Text(notifications.size.toString())}
                                }
                                   }){Icon(
                                    painter = painterResource(id = R.drawable.baseline_notifications_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clickable {
                                            scope.launch {
                                                drawerState.apply {
                                                    if (isClosed) open() else close()
                                                }
                                            }
                                            // Azione da eseguire quando si clicca sull'icona di navigazione
                                        }
                                        .size(35.dp)
                                )
                                   }
                            }
                        )
                        Row(
                            modifier = Modifier

                                .fillMaxWidth()
                                .padding(end = 16.dp)
                                .offset(y = 70.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                                contentDescription = "filterButton",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(30.dp)
                            )
                            Text(
                                text = "Aste in Corso",
                                color = Color.Gray,
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp,
                                modifier = Modifier.weight(1f),
                            )

                        }

                        LazyColumn(
                            modifier = Modifier
                                .offset(y = 80.dp)
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            items(6) { index ->

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        // Riquadro per ogni elemento della lista
                                        OutlinedCard(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(200.dp)
                                                .padding(8.dp)
                                                .clickable { navController.navigate("SchermataPaginaAsta") },
                                            border = BorderStroke(1.dp, Color.Black),

                                            ) {
                                            Column(
                                                modifier = Modifier.padding(16.dp),

                                                ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    // Immagine
                                                    Image(
                                                        painter = painterResource(id = R.drawable.defaultimage),
                                                        contentDescription = "Image",
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(80.dp),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }

                                                // Titolo
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = "Asta $index ",
                                                        color = Color.Black,
                                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                                        fontSize = 20.sp,
                                                        modifier = Modifier.padding(top = 8.dp)
                                                    )
                                                }
                                            }

                                            // Bottone
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                ElevatedButton(
                                                    onClick = { /* Azione del bottone */ },
                                                    modifier = Modifier.padding(bottom = 8.dp),
                                                    colors = ButtonColors(
                                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                                        contentColor = MaterialTheme.colorScheme.primary,
                                                        disabledContentColor = Color.Gray,
                                                        disabledContainerColor = Color.Gray
                                                    )
                                                ) {
                                                    Text(
                                                        text = String.format(
                                                            "€%.2f",
                                                            80.0 + Random.nextDouble() * 9.0
                                                        ),
                                                        fontSize = 12.sp
                                                    )
                                                }
                                            }

                                        }


                                        // Riquadro per ogni elemento della lista
                                        OutlinedCard(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(200.dp)
                                                .padding(8.dp)
                                                .clickable { navController.navigate("SchermataPaginaAsta") },
                                            border = BorderStroke(1.dp, Color.Black),

                                            ) {
                                            Column(
                                                modifier = Modifier.padding(16.dp),

                                                ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    // Immagine
                                                    Image(
                                                        painter = painterResource(id = R.drawable.defaultimage),
                                                        contentDescription = "Image",
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(80.dp),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }

                                                // Titolo
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = "Asta $index ",
                                                        color = Color.Black,
                                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                                        fontSize = 20.sp,
                                                        modifier = Modifier.padding(top = 8.dp)
                                                    )
                                                }
                                            }

                                            // Bottone
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                ElevatedButton(
                                                    onClick = { /* Azione del bottone */ },
                                                    modifier = Modifier.padding(bottom = 8.dp),
                                                    colors = ButtonColors(
                                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                                        contentColor = MaterialTheme.colorScheme.primary,
                                                        disabledContentColor = Color.Gray,
                                                        disabledContainerColor = Color.Gray
                                                    )
                                                ) {
                                                    Text(
                                                        text = String.format(
                                                            "€%.2f",
                                                            80.0 + Random.nextDouble() * 9.0
                                                        ),
                                                        fontSize = 12.sp
                                                    )
                                                }
                                            }

                                        }
                                    }

                                }

                            item {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp) // Altezza dello Spacer uguale all'altezza della BottomAppBar
                                )
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
                                iconId = R.drawable.baseline_manage_accounts_24,
                                text = "Profilo",
                                "SchermataProfiloUtente"
                            )

                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(1f)
                            )
                            IconWithText(
                                iconId = R.drawable.hand_money_cash_hold_svgrepo_com,
                                text = "Crea Asta",
                                "SchermataCreazioneAsta"
                            )


                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(1f)
                            )
                            IconWithText(
                                iconId = R.drawable.line_chart_svgrepo_com,
                                text = "Gestisci Aste",
                                "SchermataGestioneAste"
                            )


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
                        }


                    }

                }
            })
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