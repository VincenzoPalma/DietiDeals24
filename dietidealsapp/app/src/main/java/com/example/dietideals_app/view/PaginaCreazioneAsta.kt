package com.example.dietideals_app.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import java.text.SimpleDateFormat
import java.util.Date

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


@SuppressLint("SuspiciousIndentation", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataCreazioneAsta(navController: NavController) {

    var isDialogVisible by remember { mutableStateOf(false) }
    var isConfirmDialogVisible by remember { mutableStateOf(false) }
    val categorie = arrayOf(
        "Elettronica",
        "Informatica",
        "Giocattoli",
        "Alimentari",
        "Servizi",
        "Arredamento",
        "Auto e Moto",
        "Libri",
        "Abbigliamento",
        "Attrezzi ed utensili",
        "Bellezza",
        "Musica",
        "Arte"
    )
    val colorGreen = 0xFF0EA639
    val colorRed = 0xFF9B0404
    val defaultImage = painterResource(id = R.drawable.defaultimage)
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val tabNames = listOf("All'inglese", "Silenziosa", "Inversa")
    var nomeAsta by remember { mutableStateOf("") }
    var sogliaRialzo by remember { mutableStateOf("10") }


    var oreIntervallo by remember { mutableStateOf("1") }
    val ore = minOf(maxOf(oreIntervallo.toIntOrNull() ?: 0, 0), 3)
    oreIntervallo = ore.toString()
    val oreIntervalloFocusRequested = remember { FocusRequester() }
    var minutiIntervallo by remember { mutableStateOf("0") }
    val minutiIntervalloFocusRequested = remember { FocusRequester() }
    var prezzoBase by remember { mutableStateOf("") }
    var descrizione by remember { mutableStateOf("") }
    val prezzoBaseFocusRequested = remember { FocusRequester() }
    val descrizioneFocusRequested = remember { FocusRequester() }
    val currentPage = remember { mutableStateOf(0) }
    val openDateDialog = remember { mutableStateOf(false) }
    val state = rememberDatePickerState()
    val timeState = rememberTimePickerState(11, 30, false)
    val openTimeDialog = remember { mutableStateOf(false) }
    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }


    var categoriaSelezionata by remember {
        mutableStateOf("Seleziona Categoria")
    }

    /* LocalContext.current as ComponentActivity
     var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
     // Definisci il contratto per l'activity result
     val getContent = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
         // Gestisci l'URI dell'immagine selezionata
         // Puoi eseguire ulteriori operazioni qui, come caricare l'immagine
         uri?.let {
             selectedImageUri = it
         }
     }*/


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
                                if (currentPage.value == 0) {
                                    navController.navigate("SchermataHome")
                                } else {
                                    currentPage.value = 0
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

            Column(
                modifier = Modifier

                    .fillMaxHeight()
                    .offset(y = 60.dp)
            ) {
                // TabRow con schede
                TabRow(selectedTabIndex.intValue, modifier = Modifier.fillMaxWidth()) {
                    // Itera attraverso le schede e le aggiunge a TabRow
                    for (index in tabNames.indices) {
                        Tab(
                            selected = selectedTabIndex.intValue == index,
                            onClick = {
                                selectedTabIndex.intValue = index
                                currentPage.value = 0
                            },
                            // Puoi personalizzare l'aspetto delle schede qui, ad esempio aggiungendo icone, testo, etc.
                            text = {
                                Text(text = tabNames[index], fontSize = 15.sp)
                            }
                        )
                    }
                }
                @SuppressLint("SimpleDateFormat")
                fun convertMillisToDate(millis: Long): String {
                    val formatter = SimpleDateFormat("dd/MM/yyyy")
                    return formatter.format(Date(millis))
                }

                // Contenuto dinamico in base alla scheda corrente
                when (selectedTabIndex.value) {
                    0 -> {
                        when (currentPage.value) {
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

                                        OutlinedTextField(value = prezzoBase,
                                            onValueChange = { prezzoBase = it },
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                imeAction = ImeAction.Next
                                            ),
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
                                            Text(text = "$categoriaSelezionata")
                                        }

                                    }
                                    HorizontalDivider(
                                        // Aggiungi una linea divisoria
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .offset(y = 260.dp)
                                    )
                                    OutlinedTextField(
                                        value = descrizione, onValueChange = { descrizione = it },
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
                                            onClick = { currentPage.value = 1 }, modifier = Modifier
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
                                            .offset(y = 10.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {

                                        Box(modifier = Modifier.size(250.dp)) {
                                            Image(
                                                painter = defaultImage,
                                                contentDescription = null,
                                                modifier = Modifier.matchParentSize()
                                            )
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Edit",
                                                tint = Color.Black,
                                                modifier = Modifier
                                                    .size(48.dp)
                                                    .align(Alignment.Center)
                                                    .shadow(15.dp)
                                                    .clickable {  /*getContent.launch("image/*") */*/ }
                                            )
                                        }


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

                                            fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                        )
                                        OutlinedTextField(
                                            value = sogliaRialzo,
                                            onValueChange = {
                                                if (it.toIntOrNull() ?: 0 >= 10) {
                                                    sogliaRialzo = it
                                                }
                                            },
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                imeAction = ImeAction.Next,
                                                keyboardType = KeyboardType.Number // Imposta la tastiera numerica
                                            ),
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
                                                if (it.isDigitsOnly() && it.toInt() in 0..3) {
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
                                                        minutiIntervallo.toIntOrNull() ?: 0, 60
                                                    ), 30
                                                ), 60
                                            )
                                            minutiIntervallo = minuti.toString()
                                        }
                                        if (oreIntervallo.toIntOrNull() == 3) {
                                            val minuti = 0
                                            minutiIntervallo = minuti.toString()
                                        }


                                        OutlinedTextField(
                                            value = minutiIntervallo, onValueChange = {
                                                minutiIntervallo = it.take(2)
                                                if (it.isDigitsOnly() && it.toInt() in 0..60) {
                                                    minutiIntervallo = it

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
                                            onClick = { isConfirmDialogVisible = true },
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
                        var prezzoMinimo by remember { mutableStateOf("") }
                        val prezzoMinimoFocusRequested = remember { FocusRequester() }
                        val dataScadenzaFocusRequested = remember { FocusRequester() }






                        when (currentPage.value) {
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
                                            onNext = { prezzoMinimoFocusRequested.requestFocus() }
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
                                            text = "Prezzo massimo: € ",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        )

                                        OutlinedTextField(value = prezzoMinimo,
                                            onValueChange = { prezzoMinimo = it },
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                imeAction = ImeAction.Next
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onNext = { prezzoMinimoFocusRequested.requestFocus() }
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
                                            Text(text = "$categoriaSelezionata")
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
                                                if (selectedHour == 0 && selectedMinute == 0) "__ : __" else {
                                                    "$selectedHour : $selectedMinute"
                                                }
                                            )
                                        }

                                    }
                                    OutlinedTextField(
                                        value = descrizione,
                                        onValueChange = { descrizione = it },
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
                                            onClick = { currentPage.value = 1 },
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
                                            .offset(y = 10.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {

                                        Box(modifier = Modifier.size(250.dp)) {
                                            Image(
                                                painter = defaultImage,
                                                contentDescription = null,
                                                modifier = Modifier.matchParentSize()
                                            )
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Edit",
                                                tint = Color.Black,
                                                modifier = Modifier
                                                    .size(48.dp)
                                                    .align(Alignment.Center)
                                                    .shadow(15.dp)
                                                    .clickable {  /*getContent.launch("image/*") */*/ }
                                            )
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                    ) {
                                        ElevatedButton(
                                            onClick = { isConfirmDialogVisible = true },
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
                        var prezzoMassimo by remember { mutableStateOf("") }
                        val prezzoMassimoFocusRequested = remember { FocusRequester() }
                        val dataScadenzaFocusRequested = remember { FocusRequester() }
                        when (currentPage.value) {
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
                                            text = "Prezzo minimo: € ",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        )

                                        OutlinedTextField(value = prezzoMassimo,
                                            onValueChange = { prezzoMassimo = it },
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                imeAction = ImeAction.Next
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onNext = { prezzoMassimoFocusRequested.requestFocus() }
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
                                            Text(text = "$categoriaSelezionata")
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
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                    ) {
                                        ElevatedButton(
                                            onClick = { currentPage.value = 1 },
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
                                            .offset(y = 10.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {

                                        Box(modifier = Modifier.size(250.dp)) {
                                            Image(
                                                painter = defaultImage,
                                                contentDescription = null,
                                                modifier = Modifier.matchParentSize()
                                            )
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Edit",
                                                tint = Color.Black,
                                                modifier = Modifier
                                                    .size(48.dp)
                                                    .align(Alignment.Center)
                                                    .shadow(15.dp)
                                                    .clickable {  /*getContent.launch("image/*") */*/ }
                                            )
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                    ) {
                                        ElevatedButton(
                                            onClick = { isConfirmDialogVisible = true },
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
                            .padding(16.dp)
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
                                                    categoriaSelezionata = categorie[index]
                                                    isDialogVisible = false
                                                }
                                        ) {
                                            Text(text = categorie[index])

                                        }
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
                            TextButton(onClick = { isConfirmDialogVisible = false }) {
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
}


@Preview(showBackground = true)
@Composable
fun PreviewSchermataCreazioneAsta() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataCreazioneAsta(navController)
    }
}