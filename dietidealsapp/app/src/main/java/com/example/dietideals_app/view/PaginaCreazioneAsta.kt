package com.example.dietideals_app.view

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dietideals_app.R
import com.example.dietideals_app.model.dto.CreaAsta
import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.TipoAsta
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.viewmodel.PagineCreazioneAstaViewModel
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date
import java.util.Locale

class PaginaCreazioneAsta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SchermataCreazioneAsta(navController)
                }
            }
        }

    }
}


@SuppressLint("SuspiciousIndentation", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataCreazioneAsta(navController: NavController) {

    val viewModel = PagineCreazioneAstaViewModel()
    var isDialogVisible by remember { mutableStateOf(false) }
    var isConfirmDialogVisible by remember { mutableStateOf(false) }
    val categorie = arrayOf(
        "ELETTRONICA",
        "INFORMATICA",
        "GIOCATTOLI",
        "ALIMENTARI",
        "SERVIZI",
        "ARREDAMENTO",
        "AUTO_E_MOTO",
        "LIBRI",
        "ABBIGLIAMENTO",
        "ATTREZZI_E_UTENSILI",
        "BELLEZZA",
        "MUSICA_E_ARTE"
    )
    val MAX_LENGTH = 300
    val colorGreen = 0xFF0EA639
    val colorRed = 0xFF9B0404
    val storage = Firebase.storage
    val storageRef = storage.reference
    val defaultImage = painterResource(id = R.drawable.defaultimage)
    val selectedTabIndex = remember { mutableIntStateOf(2) }
    val tabNames = listOf("All'inglese", "Silenziosa", "Inversa")
    var nomeAsta by remember { mutableStateOf("") }
    var sogliaRialzo by remember { mutableStateOf<BigDecimal>(BigDecimal.TEN) }
    var oreIntervallo by remember { mutableStateOf("1") }
    val ore = minOf(maxOf(oreIntervallo.toIntOrNull() ?: 0, 0), 3)
    oreIntervallo = ore.toString()
    val oreIntervalloFocusRequested = remember { FocusRequester() }
    var minutiIntervallo by remember { mutableStateOf("0") }
    val minutiIntervalloFocusRequested = remember { FocusRequester() }
    var prezzo by remember { mutableStateOf<BigDecimal>(BigDecimal.ZERO) }
    var descrizione by remember { mutableStateOf("") }
    val prezzoFocusrequested = remember { FocusRequester() }
    val descrizioneFocusRequested = remember { FocusRequester() }
    val currentPage = remember { mutableIntStateOf(0) }
    val openDateDialog = remember { mutableStateOf(false) }
    val state = rememberDatePickerState()
    val timeState = rememberTimePickerState(11, 30, true)
    val openTimeDialog = remember { mutableStateOf(false) }
    var selectedHour by remember { mutableIntStateOf(24) }
    var selectedMinute by remember { mutableIntStateOf(60) }
    var downloadUrl: String? by remember { mutableStateOf(null) }
    var categoriaSelezionata by remember { mutableStateOf<CategoriaAsta?>(null) }
    var isUtenteVenditore by remember { mutableStateOf(false) }
    var alertDialog by remember { mutableStateOf(false) }
    var snackbarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isUtenteVenditore = viewModel.isUtenteVenditore()
    }

    LocalContext.current as ComponentActivity
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    // Definisci il contratto per l'activity result
    val getContent =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            // Gestisci l'URI dell'immagine selezionata
            // Puoi eseguire ulteriori operazioni qui, come caricare l'immagine
            uri?.let {
                selectedImageUri = it
            }
        }

    fun isScadenzaValid(): Boolean {
        if ((selectedTabIndex.intValue == 1 || selectedTabIndex.intValue == 2) && !(LocalDate.of(
                1970,
                1,
                1
            )
                .plusDays(state.selectedDateMillis!! / (24 * 60 * 60 * 1000)))
                .isBefore(LocalDate.now().plusDays(31))
        ) {
            return false
        }
        if (selectedTabIndex.intValue == 1) {
            return if (LocalDate.of(1970, 1, 1)
                    .plusDays(state.selectedDateMillis!! / (24 * 60 * 60 * 1000))
                    .isEqual(LocalDate.now().plusDays(1))
            ) {
                if (selectedHour > LocalTime.now().hour + 1) {
                    true
                } else if (selectedHour == LocalTime.now().hour + 1) {
                    selectedMinute >= LocalTime.now().minute
                } else {
                    false
                }
            } else if (LocalDate.of(1970, 1, 1)
                    .plusDays(state.selectedDateMillis!! / (24 * 60 * 60 * 1000))
                    .isAfter(LocalDate.now().plusDays(1))
            ) {
                true
            } else {
                //data passata o attuale
                false
            }
        } else if (selectedTabIndex.intValue == 2) {
            return LocalDate.of(1970, 1, 1)
                .plusDays(state.selectedDateMillis!! / (24 * 60 * 60 * 1000))
                .isAfter(LocalDate.now().plusDays(1))
        }
        return false
    }

    fun caricaImmagineAsta() {
        CoroutineScope(Dispatchers.Main).launch {
            if (selectedImageUri != null) {
                val immagineAstaRef =
                    storageRef.child("ImmaginiAsta/${selectedImageUri?.lastPathSegment}")
                selectedImageUri?.let { immagineAstaRef.putFile(it).await() }
                immagineAstaRef.downloadUrl.addOnSuccessListener { uri ->
                    // Ottieni l'URL di download dell'immagine
                    downloadUrl = uri.toString()
                }.addOnFailureListener { exception ->
                    println("Errore durante il recupero dell'URL di download: $exception")
                }
            }
        }
    }

    fun reset() {
        nomeAsta = ""
        sogliaRialzo = BigDecimal.TEN
        oreIntervallo = "1"
        minutiIntervallo = "0"
        prezzo = BigDecimal.ZERO
        descrizione = ""
        selectedHour = 24
        selectedMinute = 60
        categoriaSelezionata = null
        state.selectedDateMillis = null
        selectedImageUri = null

    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (background, topBar, tab) = createRefs()
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
        ) {}
        TopAppBar(modifier = Modifier.constrainAs(topBar)
        {
            top.linkTo(parent.top)
        }, title = {
            Text(
                text = "CREA ASTA",
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
                            if (currentPage.intValue == 0) {
                                navController.navigate("SchermataHome")
                            } else {
                                currentPage.intValue = 0
                            }


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

        var tabSelezionata by remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier
                .constrainAs(tab)
                {
                    top.linkTo(topBar.top)
                }


                .fillMaxHeight()
                .offset(y = 60.dp)
        ) {
            // TabRow con schede
            TabRow(selectedTabIndex.intValue, modifier = Modifier.fillMaxWidth()) {
                // Itera attraverso le schede e le aggiunge a TabRow
                for (index in tabNames.indices) {
                    Tab(
                        enabled = ((index == 0 || index == 1) && isUtenteVenditore) || (index == 2),
                        selected = selectedTabIndex.intValue == index,
                        onClick = {
                            alertDialog = true
                            tabSelezionata = index
                        },
                        text = {
                            Text(
                                text = tabNames[index], fontSize = 15.sp,
                                color = if ((index == 0 || index == 1) && isUtenteVenditore || (index == 2)) {
                                    Color(colorGreen)
                                } else {
                                    Color.LightGray
                                }
                            )
                        }
                    )
                    if (alertDialog) {
                        AlertDialog(
                            onDismissRequest = { alertDialog = false },
                            title = {
                                Text(
                                    text = "Attenzione!",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            },
                            text = {
                                Text(text = "Perderai tutti i progressi fatti. Continuare?")
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        selectedTabIndex.intValue = tabSelezionata
                                        currentPage.intValue = 0
                                        reset()
                                        alertDialog = false
                                    }, modifier = Modifier.padding(end = 36.dp)
                                ) {
                                    Text(text = "Continua", fontSize = 15.sp)
                                }

                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        // Qui gestisci l'azione quando l'utente sceglie di non continuare
                                        alertDialog = false
                                    }, modifier = Modifier.padding(end = 36.dp)
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

                }
            }


            fun convertMillisToDate(millis: Long): String {
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                return formatter.format(Date(millis))
            }

            // Contenuto dinamico in base alla scheda corrente
            when (selectedTabIndex.intValue) {
                0 -> {
                    when (currentPage.intValue) {
                        0 -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)

                            )
                            {
                                Text(
                                    text = "DETTAGLI ASTA",
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(value = nomeAsta,
                                    onValueChange = { nomeAsta = it },
                                    label = { Text(text = "Nome Asta") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 40.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { prezzoFocusrequested.requestFocus() }
                                    ))
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 120.dp)
                                )


                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 130.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "Prezzo Base: € ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )

                                    OutlinedTextField(value = prezzo.toString(),
                                        onValueChange = {
                                            prezzo = try {
                                                it.toBigDecimal()
                                            } catch (ex: NumberFormatException) {
                                                BigDecimal.ZERO
                                            }
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        keyboardActions = KeyboardActions(
                                            onNext = { prezzoFocusrequested.requestFocus() }
                                        ),
                                        modifier = Modifier
                                            .focusRequester(descrizioneFocusRequested)
                                            .width(100.dp)
                                            .height(50.dp),
                                        textStyle = TextStyle(fontSize = 15.sp)
                                    )

                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 190.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .offset(y = 200.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Categoria:  ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )

                                    ElevatedButton(onClick = { isDialogVisible = true }) {
                                        (if (categoriaSelezionata == null) {
                                            "Seleziona Categoria"
                                        } else {
                                            categoriaSelezionata?.name?.replace("_", " ")
                                        })?.let { Text(text = it) }
                                    }

                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 260.dp)
                                )
                                OutlinedTextField(
                                    value = descrizione,
                                    onValueChange = { descrizione = it.take(MAX_LENGTH) },
                                    supportingText = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        )
                                        {
                                            Text(
                                                text = descrizione.length.toString() + "/$MAX_LENGTH",
                                                color = Color.LightGray,
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Right
                                            )
                                        }
                                    },
                                    label = { Text(text = "Descrizione") },
                                    modifier = Modifier
                                        .offset(y = 270.dp)
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .focusRequester(descrizioneFocusRequested),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done
                                    ),
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                    ElevatedButton(
                                        enabled = descrizione.isNotEmpty() && nomeAsta.isNotEmpty() && prezzo > BigDecimal.ZERO && categoriaSelezionata != null,
                                        onClick = { currentPage.intValue = 1 }, modifier = Modifier
                                            .offset(y = 500.dp)
                                            .padding(8.dp)
                                    ) {
                                        Text(text = "Avanti", fontSize = 20.sp)

                                    }

                                }

                            }
                        }

                        1 -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            )
                            {
                                Text(
                                    text = "DETTAGLI OPZIONALI",
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 40.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    AsyncImage(
                                        model = selectedImageUri,
                                        contentDescription = "immagine Asta",
                                        modifier = Modifier
                                            .height(180.dp)
                                            .clickable { getContent.launch("image/*") },
                                        placeholder = defaultImage,
                                        error = defaultImage,
                                        contentScale = ContentScale.Crop,
                                        onSuccess = { snackbarVisible = true }
                                    )
                                }

                                if (snackbarVisible) {
                                    Snackbar(
                                        modifier = Modifier
                                            .padding(16.dp, bottom = 64.dp)
                                            .align(Alignment.BottomCenter),
                                        content = { Text(text = "Immagine caricata con successo!") },
                                        action = {
                                            TextButton(onClick = { snackbarVisible = false }) {
                                                Text(text = "OK")
                                            }
                                        }
                                    )
                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 235.dp)
                                )


                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 250.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                )
                                {
                                    Text(
                                        text = "Soglia di rialzo € ",
                                        fontSize = 25.sp,

                                        fontWeight = FontWeight.Bold,
                                    )
                                    OutlinedTextField(
                                        value = sogliaRialzo.toString(),
                                        onValueChange = {
                                            try {
                                                if (it.toBigDecimal() > BigDecimal.ZERO) {
                                                    sogliaRialzo = it.toBigDecimal()
                                                }
                                            } catch (ex: NumberFormatException) {
                                                sogliaRialzo = BigDecimal.TEN
                                            }
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        singleLine = true,
                                        keyboardActions = KeyboardActions(
                                            onNext = { oreIntervalloFocusRequested.requestFocus() }
                                        ),
                                        modifier = Modifier
                                            .width(90.dp)
                                            .height(60.dp),
                                        textStyle = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                    )


                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 325.dp)
                                )
                                Text(
                                    text = "INTERVALLO DI TEMPO OFFERTA",
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 340.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 380.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                )
                                {
                                    OutlinedTextField(
                                        value = oreIntervallo,
                                        onValueChange = {
                                            oreIntervallo = it.take(1)
                                            if (it.isDigitsOnly() && it.toIntOrNull() in 0..3) {
                                                oreIntervallo = it

                                            }
                                        }, // Limita il testo a massimo 2 cifre
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            imeAction = ImeAction.Next,
                                            keyboardType = KeyboardType.Number // Tastiera numerica
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onNext = { minutiIntervalloFocusRequested.requestFocus() }
                                        ),
                                        modifier = Modifier
                                            .focusRequester(oreIntervalloFocusRequested)
                                            .width(50.dp)
                                            .height(55.dp),
                                        textStyle = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        visualTransformation = VisualTransformation.None // Per mantenere il testo visibile come è stato inserito
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "ORE",
                                        fontSize = 20.sp,

                                        )
                                    Spacer(modifier = Modifier.width(10.dp))


                                    if (oreIntervallo.toIntOrNull() == 0) {
                                        val minuti = minOf(
                                            maxOf(
                                                minOf(
                                                    minutiIntervallo.toIntOrNull() ?: 0, 59
                                                ), 30
                                            ), 59
                                        )
                                        minutiIntervallo = minuti.toString()
                                    }
                                    if (oreIntervallo.toIntOrNull() == 3) {
                                        val minuti = 0
                                        minutiIntervallo = minuti.toString()
                                    }


                                    OutlinedTextField(
                                        value = minutiIntervallo, onValueChange = {
                                            try {
                                                if (it.isDigitsOnly() && it.toInt() in 0..59) {
                                                    minutiIntervallo = it.take(2)
                                                }
                                            } catch (ex: NumberFormatException) {
                                                minutiIntervallo = ""
                                            }
                                        },
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            imeAction = ImeAction.Next,
                                            keyboardType = KeyboardType.Number // Tastiera numerica
                                        ),

                                        modifier = Modifier
                                            .focusRequester(minutiIntervalloFocusRequested)
                                            .width(60.dp)
                                            .height(55.dp),
                                        textStyle = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "MINUTI",
                                        fontSize = 20.sp,
                                    )


                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                    ElevatedButton(
                                        enabled = minutiIntervallo.isNotEmpty(),
                                        onClick = {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                caricaImmagineAsta()
                                                delay(1500)
                                                categoriaSelezionata?.let {
                                                    CreaAsta(
                                                        nomeAsta,
                                                        descrizione,
                                                        null,
                                                        prezzo,
                                                        sogliaRialzo,
                                                        (oreIntervallo.toInt() * 60) + minutiIntervallo.toInt(),
                                                        it,
                                                        TipoAsta.INGLESE,
                                                        downloadUrl
                                                    )

                                                }?.let { viewModel.inserisciAsta(it) }
                                            }
                                            isConfirmDialogVisible = true
                                        },
                                        modifier = Modifier
                                            .offset(y = 500.dp)
                                            .padding(8.dp)
                                    ) {
                                        Text(text = "Conferma", fontSize = 20.sp)

                                    }

                                }


                            }
                        }

                    }
                }


                1 -> {
                    val prezzoBaseFocusRequested = remember { FocusRequester() }
                    val dataScadenzaFocusRequested = remember { FocusRequester() }

                    when (currentPage.intValue) {
                        0 -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            )
                            {
                                Text(
                                    text = "DETTAGLI ASTA",
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(value = nomeAsta,
                                    onValueChange = { nomeAsta = it },
                                    label = { Text(text = "Nome Asta") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 40.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { prezzoBaseFocusRequested.requestFocus() }
                                    ))
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 120.dp)
                                )


                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 130.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "Prezzo Base: € ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )

                                    OutlinedTextField(
                                        value = prezzo.toString(),
                                        onValueChange = {
                                            prezzo = try {
                                                it.toBigDecimal()
                                            } catch (ex: NumberFormatException) {
                                                BigDecimal.ZERO
                                            }
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        keyboardActions = KeyboardActions(
                                            onNext = { prezzoBaseFocusRequested.requestFocus() }
                                        ),
                                        modifier = Modifier
                                            .focusRequester(descrizioneFocusRequested)
                                            .width(100.dp)
                                            .height(50.dp),
                                        textStyle = TextStyle(fontSize = 15.sp)
                                    )

                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 190.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .offset(y = 200.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Categoria:  ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    ElevatedButton(onClick = { isDialogVisible = true }) {
                                        (if (categoriaSelezionata == null) {
                                            "Seleziona Categoria"
                                        } else {
                                            categoriaSelezionata?.name?.replace("_", " ")
                                        })?.let { Text(text = it) }
                                    }

                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 260.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .offset(y = 270.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Data di scadenza ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )

                                    TextButton(
                                        onClick = { openDateDialog.value = true },
                                    ) {

                                        Text(
                                            text = if (state.selectedDateMillis == null) "__/__/____" else convertMillisToDate(
                                                state.selectedDateMillis!!
                                            ),
                                            modifier = Modifier

                                                .focusRequester(dataScadenzaFocusRequested)


                                        )

                                    }

                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 330.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .offset(y = 340.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Orario di scadenza ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )

                                    TextButton(
                                        onClick = { openTimeDialog.value = true },
                                    ) {
                                        Text(
                                            text =
                                            if (selectedHour == 24 && selectedMinute == 60) "__ : __" else {
                                                "$selectedHour : $selectedMinute"
                                            }
                                        )
                                    }

                                }

                                OutlinedTextField(
                                    value = descrizione,
                                    onValueChange = { descrizione = it.take(MAX_LENGTH) },
                                    supportingText = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        )
                                        {
                                            Text(
                                                text = descrizione.length.toString() + "/$MAX_LENGTH",
                                                color = Color.LightGray,
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Right
                                            )
                                        }
                                    },
                                    label = { Text(text = "Descrizione") },
                                    modifier = Modifier
                                        .offset(y = 400.dp)
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .focusRequester(descrizioneFocusRequested),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done
                                    ),
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                    ElevatedButton(
                                        enabled = nomeAsta.isNotEmpty() && prezzo > BigDecimal.ZERO && categoriaSelezionata != null && descrizione.isNotEmpty()
                                                && state.selectedDateMillis != null
                                                && isScadenzaValid()
                                                && selectedHour != 24 && selectedMinute != 60,
                                        onClick = { currentPage.intValue = 1 },
                                        modifier = Modifier
                                            .offset(y = 500.dp)
                                            .padding(8.dp)
                                    ) {
                                        Text(text = "Avanti", fontSize = 20.sp)

                                    }

                                }

                            }

                        }

                        1 -> {


                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            )
                            {
                                Text(
                                    text = "DETTAGLI OPZIONALI",
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 40.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {

                                    AsyncImage(
                                        model = selectedImageUri,
                                        contentDescription = "immagine Asta",
                                        modifier = Modifier
                                            .height(200.dp)
                                            .clickable { getContent.launch("image/*") },
                                        placeholder = defaultImage,
                                        error = defaultImage,
                                        contentScale = ContentScale.Crop,
                                        onSuccess = { snackbarVisible = true }
                                    )
                                }

                                if (snackbarVisible) {
                                    Snackbar(
                                        modifier = Modifier
                                            .padding(16.dp, bottom = 64.dp)
                                            .align(Alignment.BottomCenter),
                                        content = { Text(text = "Immagine caricata con successo!") },
                                        action = {
                                            TextButton(onClick = { snackbarVisible = false }) {
                                                Text(text = "OK")
                                            }
                                        }
                                    )
                                }



                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                    ElevatedButton(
                                        onClick = {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                caricaImmagineAsta()
                                                delay(1500)
                                                categoriaSelezionata?.let {
                                                    CreaAsta(
                                                        nomeAsta,
                                                        descrizione,
                                                        LocalDate.of(1970, 1, 1)
                                                            .plusDays(state.selectedDateMillis!! / (24 * 60 * 60 * 1000))
                                                            .toString() + "T" + selectedHour + ":" + selectedMinute + ":" + "00+00:00",
                                                        prezzo,
                                                        null,
                                                        null,
                                                        it,
                                                        TipoAsta.SILENZIOSA,
                                                        downloadUrl
                                                    )
                                                }?.let { viewModel.inserisciAsta(it) }
                                            }
                                            isConfirmDialogVisible = true
                                        },
                                        modifier = Modifier
                                            .offset(y = 500.dp)
                                            .padding(8.dp)
                                    ) {
                                        Text(text = "Conferma", fontSize = 20.sp)

                                    }

                                }


                            }

                        }

                    }
                }

                2 -> {

                    val prezzoMassimoFocusRequested = remember { FocusRequester() }
                    val dataScadenzaFocusRequested = remember { FocusRequester() }
                    when (currentPage.intValue) {
                        0 -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            )
                            {
                                Text(
                                    text = "DETTAGLI ASTA",
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(value = nomeAsta,
                                    onValueChange = { nomeAsta = it },
                                    label = { Text(text = "Che cosa stai cercando?") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 40.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { prezzoMassimoFocusRequested.requestFocus() }
                                    ))
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 120.dp)
                                )


                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 130.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "Prezzo base: € ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )

                                    OutlinedTextField(value = prezzo.toString(),
                                        onValueChange = {
                                            prezzo = try {
                                                it.toBigDecimal()
                                            } catch (ex: NumberFormatException) {
                                                BigDecimal.ZERO
                                            }
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        keyboardActions = KeyboardActions(
                                            onNext = { descrizioneFocusRequested.requestFocus() }
                                        ),
                                        modifier = Modifier
                                            .focusRequester(prezzoFocusrequested)
                                            .width(100.dp)
                                            .height(50.dp),
                                        textStyle = TextStyle(fontSize = 15.sp)
                                    )

                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 190.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .offset(y = 200.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Categoria:  ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    ElevatedButton(onClick = { isDialogVisible = true }) {
                                        (if (categoriaSelezionata == null) {
                                            "Seleziona Categoria"
                                        } else {
                                            categoriaSelezionata?.name?.replace("_", " ")
                                        })?.let { Text(text = it) }
                                    }

                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 260.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .offset(y = 270.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Data di scadenza ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )

                                    TextButton(
                                        onClick = { openDateDialog.value = true },
                                    ) {

                                        Text(
                                            text = if (state.selectedDateMillis == null) "__/__/____" else convertMillisToDate(
                                                state.selectedDateMillis!!
                                            ),
                                            modifier = Modifier

                                                .focusRequester(dataScadenzaFocusRequested)

                                        )
                                    }


                                }
                                HorizontalDivider(
                                    // Aggiungi una linea divisoria
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 320.dp)
                                )
                                OutlinedTextField(

                                    value = descrizione,
                                    onValueChange = { descrizione = it.take(MAX_LENGTH) },
                                    supportingText = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        )
                                        {
                                            Text(
                                                text = descrizione.length.toString() + "/$MAX_LENGTH",
                                                color = Color.LightGray,
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Right
                                            )
                                        }
                                    },
                                    label = { Text(text = "Descrizione") },
                                    modifier = Modifier
                                        .offset(y = 340.dp)
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .focusRequester(descrizioneFocusRequested),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done
                                    ),
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                    ElevatedButton(
                                        enabled = nomeAsta.isNotEmpty() && prezzo > BigDecimal.ZERO && state.selectedDateMillis != null && isScadenzaValid() && descrizione.isNotEmpty(),
                                        onClick = { currentPage.intValue = 1 },
                                        modifier = Modifier
                                            .offset(y = 500.dp)
                                            .padding(8.dp)
                                    ) {
                                        Text(text = "Avanti", fontSize = 20.sp)

                                    }

                                }
                            }

                        }

                        1 -> {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            )
                            {
                                Text(
                                    text = "DETTAGLI OPZIONALI",
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 40.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {

                                    AsyncImage(
                                        model = selectedImageUri,
                                        contentDescription = "immagine Asta",
                                        modifier = Modifier
                                            .height(200.dp)
                                            .clickable { getContent.launch("image/*") },
                                        placeholder = defaultImage,
                                        error = defaultImage,
                                        contentScale = ContentScale.Crop,
                                        onSuccess = { snackbarVisible = true }
                                    )
                                }

                                if (snackbarVisible) {
                                    Snackbar(
                                        modifier = Modifier
                                            .padding(16.dp, bottom = 64.dp)
                                            .align(Alignment.BottomCenter),
                                        content = { Text(text = "Immagine caricata con successo!") },
                                        action = {
                                            TextButton(onClick = { snackbarVisible = false }) {
                                                Text(text = "OK")
                                            }
                                        }
                                    )
                                }


                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                    ElevatedButton(
                                        onClick = {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                snackbarVisible = false
                                                caricaImmagineAsta()
                                                delay(1500)
                                                categoriaSelezionata?.let {
                                                    CreaAsta(
                                                        nomeAsta,
                                                        descrizione,
                                                        LocalDate.of(1970, 1, 1)
                                                            .plusDays(state.selectedDateMillis!! / (24 * 60 * 60 * 1000))
                                                            .toString() + "T" + LocalTime.now().hour + ":" + LocalTime.now().minute + ":" + "00+00:00",
                                                        prezzo,
                                                        null,
                                                        null,
                                                        it,
                                                        TipoAsta.INVERSA,
                                                        downloadUrl
                                                    )
                                                }?.let { viewModel.inserisciAsta(it) }
                                            }
                                            isConfirmDialogVisible = true
                                        },
                                        modifier = Modifier
                                            .offset(y = 500.dp)
                                            .padding(8.dp)
                                    ) {
                                        Text(text = "Conferma", fontSize = 20.sp)

                                    }

                                }


                            }

                        }
                    }

                }

            }
        }
        if (isDialogVisible) {
            Dialog(onDismissRequest = { isDialogVisible = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp),
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            items(categorie.size) { index ->
                                // Colonna per ogni elemento della lista
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                categoriaSelezionata =
                                                    CategoriaAsta.valueOf(categorie[index])
                                                isDialogVisible = false
                                            },
                                    ) {
                                        Text(
                                            text = categorie[index].replace("_", " "),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )

                                    }
                                    HorizontalDivider(thickness = 2.dp)
                                }
                            }
                        }
                    }
                }
            }
        }

        if (isConfirmDialogVisible) {
            Dialog(onDismissRequest = { isConfirmDialogVisible = false }) {
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
                            text = "ASTA CREATA!\n\n" +
                                    "ASTA CREATA CON SUCCESSO " +
                                    "POTRAI VEDERE LA TUA ASTA IN ASTE ATTIVE",
                            textAlign = TextAlign.Center
                        )
                        TextButton(onClick = {
                            isConfirmDialogVisible = false
                            navController.navigate("SchermataHome")
                        }) {
                            Text(text = "OK", fontSize = 20.sp)

                        }


                    }
                }
            }
        }

        if (openDateDialog.value) {
            DatePickerDialog(onDismissRequest = {
                openDateDialog.value = false
            }, confirmButton = {
                TextButton(onClick = {

                    openDateDialog.value = false

                }) {
                    Text("OK")
                }
            }, dismissButton = {
                TextButton(onClick = {
                    openDateDialog.value = false
                }) {
                    Text("CANCEL")
                }
            }) {
                DatePicker(
                    state = state

                )
            }
        }


        if (openTimeDialog.value) {

            BasicAlertDialog(
                onDismissRequest = { openTimeDialog.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePicker(state = timeState)
                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { openTimeDialog.value = false }) {
                            Text(text = "Dismiss")
                        }
                        TextButton(onClick = {
                            openTimeDialog.value = false
                            selectedHour = timeState.hour
                            selectedMinute = timeState.minute
                        }) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSchermataCreazioneAsta() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataCreazioneAsta(navController)
    }
}