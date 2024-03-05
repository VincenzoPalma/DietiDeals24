package com.example.dietideals_app.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import coil.compose.AsyncImage
import com.example.dietideals_app.R
import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.Offerta
import com.example.dietideals_app.model.enum.TipoAsta
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.viewmodel.PaginaAstaViewModel
import com.example.dietideals_app.viewmodel.listener.AsteListener
import com.example.dietideals_app.viewmodel.listener.OffertaListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
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


    val auth = Firebase.auth
    val viewModel = PaginaAstaViewModel()
    val defaultImage = painterResource(id = R.drawable.defaultimage)
    val colorGreen = 0xFF0EA639
    val colorRed = 0xFF9B0404
    val context = LocalContext.current
    val currentPage = remember { mutableIntStateOf(0) }
    var openDialog = remember { mutableStateOf(false) }
    var offerta by remember { mutableStateOf("") }
    var astaVisualizzata by remember { mutableStateOf<Asta?>(null) }
    var offertaVincente by remember { mutableStateOf<Offerta?>(null) }
    var astaTerminata by remember { mutableStateOf(false) }
    var idUtenteCorrente by remember { mutableStateOf<UUID?>(null) }
    var isUtenteProprietario by remember { mutableStateOf(false) }
    var utenteHasOfferta by remember { mutableStateOf(false) }
    var listaOfferte by remember { mutableStateOf<List<Offerta>?>(emptyList()) }

    val listenerOfferta = remember {
        object : OffertaListener {
            override fun onOffertaVincenteLoaded(offerta: Offerta) {
                offertaVincente = offerta
            }

            override fun onOfferteLoaded(offerte: List<Offerta>) {
                listaOfferte = offerte
                if (listaOfferte?.any { it.utente.id == idUtenteCorrente} == true){
                    utenteHasOfferta = true
                }
            }

            override fun onError() {
                println("Impossibile trovare l'offerta.")
            }
        }
    }

    LaunchedEffect(Unit) {
        val astaJson = navController.previousBackStackEntry?.savedStateHandle?.get<String>("asta")
        val gson = GsonBuilder().registerTypeAdapter(
            OffsetDateTime::class.java,
            JsonDeserializer { json, type, jsonDeserializationContext ->
                val text = json.getAsJsonPrimitive().asString
                OffsetDateTime.parse(text)
            }).create()
        astaVisualizzata = gson.fromJson(astaJson, Asta::class.java)
        idUtenteCorrente = auth.currentUser?.uid?.let { viewModel.getIdUtenteFromIdAuth(it) }
        if (idUtenteCorrente != null && idUtenteCorrente == astaVisualizzata?.proprietario?.id){
            isUtenteProprietario = true
        }
        astaVisualizzata?.id?.let { viewModel.getOffertaVincente(it, listenerOfferta) }
        if (!isUtenteProprietario && astaVisualizzata?.tipo == TipoAsta.SILENZIOSA){
            astaVisualizzata?.id?.let { viewModel.getOfferte(it, listenerOfferta) }
        }
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
                .padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 45.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ), content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = astaVisualizzata?.urlFoto,
                        placeholder = painterResource(id = R.drawable.defaultimage),
                        error = painterResource(id = R.drawable.defaultimage),
                        contentDescription = "Immagine dell'asta",
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
                    astaVisualizzata?.nome?.let {
                        Text(
                            text = it,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "di", fontSize = 20.sp, fontWeight = FontWeight.Bold)



                    TextButton(
                        modifier = Modifier.padding(start = 5.dp),
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "idUtente",
                                value = astaVisualizzata?.proprietario?.id.toString()
                            )
                            navController.navigate("SchermataProfiloUtente")
                        },

                        ) {
                        astaVisualizzata?.proprietario?.username?.let {
                            Text(
                                text = "@$it",
                                fontSize = 15.sp
                            )
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
                    astaVisualizzata?.descrizione?.let {
                        Text(
                            text = it,
                            fontSize = 15.sp
                        )
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
                        painter = painterResource(id = R.drawable.baseline_category_24),
                        contentDescription = null
                    )
                    astaVisualizzata?.categoria?.name?.replace("_", " ", false)
                        ?.let { Text(text = it, fontSize = 15.sp) }
                    Spacer(Modifier.width(80.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.auction_hammer),
                        contentDescription = null
                    )
                    astaVisualizzata?.tipo?.let { Text(text = it.name, fontSize = 15.sp) }
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
                    Text(
                        text = if (astaVisualizzata?.tipo == TipoAsta.SILENZIOSA || offertaVincente == null) {
                            "PREZZO BASE €" + astaVisualizzata?.prezzoBase.toString()
                        } else {
                            "OFFERTA ATTUALE €" + offertaVincente?.prezzo
                        }, color = MaterialTheme.colorScheme.primary
                    )
                    if ((astaVisualizzata?.tipo == TipoAsta.INGLESE || astaVisualizzata?.tipo == TipoAsta.INVERSA) && offertaVincente != null) {

                        Text(text = " di")
                        TextButton(

                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "idUtente",
                                    value = offertaVincente?.utente?.id.toString()
                                )
                                navController.navigate("SchermataProfiloUtente")
                            },

                            ) {
                            Text(text = "@" + offertaVincente?.utente?.username, fontSize = 15.sp)

                        }
                    }
                }
                if (astaVisualizzata?.tipo == TipoAsta.INGLESE) {
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
                        Text(
                            text = " SOGLIA DI RIALZO €" + astaVisualizzata!!.sogliaRialzo.toString(),
                            fontSize = 15.sp
                        )
                    }
                    LaunchedEffect(Unit) {

                        //tempo rimanente = orario di scadenza - orario attuale | orario di scadenza = orario creazione (asta o offerta) + intervallo tempo offerta
                        val orarioScadenza: OffsetDateTime = if (offertaVincente == null) {
                            astaVisualizzata?.intervalloTempoOfferta?.toLong()
                                ?.let { astaVisualizzata!!.creationDate.plusMinutes(it) }!!
                        } else {
                            astaVisualizzata?.intervalloTempoOfferta?.toLong()
                                ?.let { offertaVincente!!.creationDate.plusMinutes(it) }!!
                        }
                        val tempoRimanente =
                            orarioScadenza.minusHours(LocalDateTime.now().hour.toLong())
                                .minusMinutes(LocalDateTime.now().minute.toLong())
                                .minusSeconds(LocalDateTime.now().second.toLong()).toLocalTime()

                        val totalTimeInMillis = TimeUnit.SECONDS.toMillis(tempoRimanente.toSecondOfDay().toLong())
                        if (totalTimeInMillis > 0) {
                            timeLeft = totalTimeInMillis
                            timerRunning = true
                            while (timeLeft != 0L && timerRunning) {
                                delay(1000)
                                timeLeft = timeLeft.minus(1000)
                            }
                            timerRunning = false
                            astaTerminata = true
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
                        painter = painterResource(id = if (astaVisualizzata?.tipo == TipoAsta.INGLESE) R.drawable.baseline_access_time_filled_24 else R.drawable.baseline_calendar_month_24),
                        contentDescription = null
                    )
                    Text(
                        if (astaVisualizzata?.tipo == TipoAsta.INGLESE) "Tempo rimanente: " else {
                            "Data di scadenza"
                        }, fontSize = 15.sp, color = Color(colorRed)
                    )
                    when (astaVisualizzata?.tipo) {
                        TipoAsta.INGLESE -> {
                                Text(
                                    formatTime(timeLeft),
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(start = 4.dp), color = Color(colorRed)
                                )
                        }

                        TipoAsta.INVERSA -> {
                            astaVisualizzata?.dataScadenza?.let {
                                Text(
                                    it.format(DateTimeFormatter.ofPattern("dd/MM/yy")),
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(start = 4.dp),
                                    color = Color(colorRed)
                                )
                            }
                        }

                        else -> {
                            astaVisualizzata?.dataScadenza?.let {
                                Text(
                                    it.plusHours(1)
                                        .format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")),
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(start = 4.dp),
                                    color = Color(colorRed)
                                )
                            }
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
                    ElevatedButton(
                        enabled = !astaTerminata && (((astaVisualizzata?.tipo != TipoAsta.SILENZIOSA && !isUtenteProprietario)
                                && (astaVisualizzata?.tipo != TipoAsta.SILENZIOSA && idUtenteCorrente != offertaVincente?.utente?.id))
                                || (astaVisualizzata?.tipo == TipoAsta.SILENZIOSA && !utenteHasOfferta)),
                        colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = Color.LightGray
                    ), onClick = {
                        if (astaVisualizzata?.tipo != TipoAsta.SILENZIOSA) openDialog.value = true
                        else {
                            navController.navigate("SchermataOfferte")
                        }
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = if (astaVisualizzata?.tipo != TipoAsta.SILENZIOSA) R.drawable.hand_money_cash_hold_svgrepo_com else R.drawable.baseline_remove_red_eye_24),
                                contentDescription = null
                            )
                            Text(text = if (astaVisualizzata?.tipo != TipoAsta.SILENZIOSA || !isUtenteProprietario) "FAI UN OFFERTA" else "VISUALIZZA OFFERTE")
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
