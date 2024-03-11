package com.example.dietideals_app.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.layout.ContentScale
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
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dietideals_app.R
import com.example.dietideals_app.model.Offerta
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.viewmodel.PaginaOfferteViewModel
import com.example.dietideals_app.viewmodel.listener.OffertaListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

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
                    SchermataOfferte(navController)

                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataOfferte(navController: NavController) {

    val viewModel = PaginaOfferteViewModel()
    var isDialogVisible by remember { mutableStateOf(false) }
    var listaOfferteVisualizzate by remember { mutableStateOf<List<Offerta>>(emptyList()) }

    val listenerOfferta = remember {
        object : OffertaListener {
            override fun onOffertaVincenteLoaded(offerta: Offerta) {
                //
            }

            override fun onOfferteLoaded(offerte: List<Offerta>) {
                listaOfferteVisualizzate = offerte
            }

            override fun onError() {
                println("Impossibile trovare l'offerta.")
            }
        }
    }

    LaunchedEffect(Unit) {
        val idAsta = navController.previousBackStackEntry?.savedStateHandle?.get<String>("idAsta")
        viewModel.paginaAstaViewModel.getOfferte(UUID.fromString(idAsta), listenerOfferta)
    }

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
                        .clickable { navController.popBackStack() }
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
        // Definisci uno stato per tracciare le righe visualizzate
        val righeVisualizzate = remember { mutableStateListOf(*Array(7) { it }) }
        var nomeSelezionato by remember { mutableStateOf("") }
        var prezzoSelezionato by remember { mutableStateOf("") }

        @Composable
        fun SwipeableRow(
            onSwipeLeft: () -> Unit,
            onSwipeRight: () -> Unit,
            content: @Composable () -> Unit
        ) {
            var offsetX by remember { mutableFloatStateOf(0f) }

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
                                val sensitiveDragAmount = dragAmount * 0.5f
                                offsetX += sensitiveDragAmount
                            },
                            onDragStart = { offsetX = 0f },
                            onDragEnd = {
                                if (offsetX > 100) {
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

            if (listaOfferteVisualizzate.isEmpty()) {
                item {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "NESSUNA OFFERTA DISPONIBILE",
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                    }
                }

            } else {


                items(listaOfferteVisualizzate.size) { index ->
                    // Utilizza l'indice per accedere all'elemento corrispondente della lista dei dati
                    val offerta = listaOfferteVisualizzate[index]
                    if (index in righeVisualizzate) {
                        val nome = offerta.utente.username
                        val prezzo = offerta.prezzo

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
                                            AsyncImage( //ci torniamo
                                                model = offerta.utente.immagineProfilo,
                                                placeholder = painterResource(id = com.facebook.R.drawable.com_facebook_profile_picture_blank_portrait),
                                                error = painterResource(id = com.facebook.R.drawable.com_facebook_close),
                                                contentDescription = "Immagine dell'utente",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
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
                                                text = prezzo.toString(),
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
                                                    val newOfferteVisualizzate =
                                                        listaOfferteVisualizzate.toMutableList()
                                                    newOfferteVisualizzate.removeAt(index)
                                                    newOfferteVisualizzate.toList()
                                                    listaOfferteVisualizzate =
                                                        newOfferteVisualizzate
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        viewModel.makeOffertaRifiutata(offerta.id)
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
                                                    nomeSelezionato = offerta.utente.username
                                                    prezzoSelezionato = offerta.prezzo.toString()
                                                    isDialogVisible = true
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        viewModel.makeOffertaVincente(offerta.id)
                                                    }
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
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp) // Altezza dello Spacer uguale all'altezza della BottomAppBar
                    )
                }
            }
        }
        NavigationBar(
            tonalElevation = 30.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)// Add a black border
                .constrainAs(bottomBar)
                {
                    bottom.linkTo(parent.bottom)
                },
            content = {
                NavigationBarItem(
                    label = { Text(text = "Home") },
                    selected = false,
                    onClick = {
                        navController.navigate("SchermataHome") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.homeicon),
                            contentDescription = "Home"
                        )
                    }
                )
                NavigationBarItem(
                    label = { Text(text = "Gestisci") },
                    selected = false,
                    onClick = {
                        navController.navigate("SchermataGestioneAste") {
                            popUpTo("SchermataGestioneAste") {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.gestisci_icon),
                            contentDescription = "Gestione Aste"
                        )
                    }
                )
                NavigationBarItem(
                    label = { Text(text = "Crea Asta") },
                    selected = false,
                    onClick = {
                        navController.navigate("SchermataCreazioneAsta") {
                            popUpTo("SchermataCreazioneAsta") {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.crea_asta_icon),
                            contentDescription = "Crea Asta"
                        )
                    }
                )
                NavigationBarItem(
                    label = { Text(text = "Profilo") },
                    selected = false,
                    onClick = {
                        navController.navigate("SchermataProfiloUtente") {
                            popUpTo("SchermataProfiloUtente") {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.usericon),
                            contentDescription = "ProfiloUtente"
                        )
                    }
                )

            }
        )




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
                                    " PER " + prezzoSelezionato,
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