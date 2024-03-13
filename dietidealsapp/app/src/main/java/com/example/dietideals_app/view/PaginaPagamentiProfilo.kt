package com.example.dietideals_app.view


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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.model.Carta
import com.example.dietideals_app.model.dto.CreaCarta
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.viewmodel.PaginaPagamentiViewModel
import com.example.dietideals_app.viewmodel.listener.CartaListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

class PaginaPagamentiProfilo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SchermataPagamentiProfilo(navController)

                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataPagamentiProfilo(navController: NavController) {

    val viewModel = PaginaPagamentiViewModel()
    var nomeTitolare by remember { mutableStateOf("") }
    var numeroCarta by remember { mutableStateOf("") }
    var codiceCvvCvc by remember { mutableStateOf("") }
    val numeroCartaFocusrequested = remember { FocusRequester() }
    val meseScadenzaFocusRequested = remember { FocusRequester() }
    val annoScadenzaFocusRequested = remember { FocusRequester() }
    val cvcFocusRequested = remember { FocusRequester() }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var deleteAlertDialog by remember { mutableStateOf(false) }
    val state = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }
    var listaCarte by remember { mutableStateOf<List<Carta>>(emptyList()) }
    var meseScadenza by remember { mutableStateOf("") }
    var annoScadenza by remember { mutableStateOf("") }

    val listener = remember {
        object : CartaListener {
            override fun onCarteLoaded(carte: List<Carta>) {
                listaCarte = carte
            }

            override fun onCartaSaved(carta: Carta) {
                val newListaCarte = listaCarte.toMutableList()
                newListaCarte.add(carta)
                listaCarte = newListaCarte.toList()
            }

            override fun onCartaDeleted() {
                //
            }

            override fun onError() {
                println("Impossibile trovare le carte")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.mostraCarte(listener)
    }

    fun pulisciCampi() {
        nomeTitolare = ""
        numeroCarta = ""
        codiceCvvCvc = ""
        state.selectedDateMillis = null
    }

    fun checkField(): Boolean {
        return nomeTitolare.isNotEmpty() && numeroCarta.isNotEmpty() && codiceCvvCvc.isNotEmpty()
    }

    fun isNumeroCartaValid(): Boolean {
        // Controllo se il numero di carta ha una lunghezza valida
        return numeroCarta.length == 16
    }

    fun isCvcValid(): Boolean {
        // Controllo se il numero di carta ha una lunghezza valida
        return codiceCvvCvc.length == 3
    }
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
                    text = "LE MIE CARTE",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold, // Imposta il grassetto
                    fontSize = 40.sp
                ) // Imposta la dimensione del testo
            },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("SchermataProfiloUtente") {
                                    popUpTo("SchermataPagamentiProfilo") {
                                        inclusive = true
                                    }
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Imposta due colonne
                modifier = Modifier
                    .offset(y = 80.dp)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Visualizza tutte le Box esistenti
                items(listaCarte.size) { index ->
                    val carta = listaCarte[index]
                    ElevatedCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(200.dp)
                            .background(Color.White),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(painter = painterResource(id = R.drawable.trash_svgrepo_com),
                                    contentDescription = "deleteCard",
                                    modifier = Modifier
                                        .clickable {
                                            selectedIndex = index
                                            deleteAlertDialog = true
                                        }
                                        .padding(6.dp))

                            }


                            // Immagine
                            Image(
                                painter = painterResource(id = R.drawable.baseline_credit_card_24),
                                contentDescription = "Image",
                                modifier = Modifier
                                    .size(80.dp)
                            )

                            // Titolo
                            Text(
                                text = if (carta.numero.first() == '5') {
                                    "Mastercard\n"
                                } else {
                                    "Visa\n"
                                } + carta.nomeTitolare + "\n" + "**** " + carta.numero.substring(12),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(4.dp)
                            )


                        }
                    }
                }


                item {
                    ElevatedCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(200.dp)
                            .background(Color.White)
                            .clickable {
                                pulisciCampi()
                                isDialogVisible = true
                            },
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize() // Occupa tutto lo spazio disponibile
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_add_card_24),
                                contentDescription = "deleteCard",
                                modifier = Modifier.size(100.dp) // Imposta la dimensione dell'icona
                            )
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
                            Text(text = "Nome Titolare")
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = nomeTitolare, onValueChange = { nomeTitolare = it },
                                shape = RoundedCornerShape(15.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .height(50.dp),
                                keyboardActions = KeyboardActions(
                                    onNext = { numeroCartaFocusrequested.requestFocus() }
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = if (nomeTitolare.isNotEmpty()) Color(
                                        0xFF0EA639
                                    ) else Color.Gray,
                                    focusedBorderColor = if (nomeTitolare.isNotEmpty()) Color(
                                        0xFF0EA639
                                    ) else Color.Gray,
                                ),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Numero Carta")
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = numeroCarta,
                                onValueChange = {
                                    numeroCarta = it
                                    isNumeroCartaValid()
                                },
                                shape = RoundedCornerShape(15.dp),
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(
                                            id = if (isNumeroCartaValid()) R.drawable.baseline_done_24 else if (!isNumeroCartaValid() && numeroCarta.isNotEmpty()) R.drawable.baseline_close_24 else R.drawable.empty
                                        ),
                                        contentDescription = "",
                                        tint = if (isNumeroCartaValid()) Color(0xFF0EA639) else if (!isNumeroCartaValid() && numeroCarta.isNotEmpty()) Color(
                                            0xFF9B0404
                                        ) else Color.Gray,
                                        modifier = if (numeroCarta.isEmpty()) Modifier.alpha(0f) else Modifier
                                    )
                                },

                                modifier = Modifier
                                    .height(50.dp)
                                    .focusRequester(numeroCartaFocusrequested),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number // Tastiera numerica
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { meseScadenzaFocusRequested.requestFocus() }
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = if (isNumeroCartaValid()) Color(
                                        0xFF0EA639
                                    ) else if (!isNumeroCartaValid() && numeroCarta.isNotEmpty()) Color(
                                        0xFF9B0404
                                    ) else Color.Gray,
                                    focusedBorderColor = if (isNumeroCartaValid()) Color(0xFF0EA639) else if (!isNumeroCartaValid() && numeroCarta.isNotEmpty()) Color(
                                        0xFF9B0404
                                    ) else Color.Gray,
                                ),
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(modifier = Modifier.fillMaxWidth())
                            {
                                Text(text = "Data Scadenza")
                                Spacer(modifier = Modifier.width(16.dp))

                                Text(text = "CCV/CVC")

                            }



                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextField(value = meseScadenza,
                                    label = {Text(text = "Mese")},
                                    singleLine = true,
                                    onValueChange = { newValue ->
                                        val input = newValue.take(2)
                                        val month = input.toIntOrNull()
                                        meseScadenza = if (month != null && month in 1..12) {
                                            input
                                        } else {
                                            ""
                                        }
                                    },
                                    modifier = Modifier
                                        .width(80.dp)
                                        .focusRequester(meseScadenzaFocusRequested),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Number // Tastiera numerica
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { annoScadenzaFocusRequested.requestFocus() }
                                    ),
                                )
                                Text(text = "/")
                                TextField(
                                    value = annoScadenza,
                                    singleLine = true,
                                    label = {Text(text = "Anno")},
                                    onValueChange = {
                                        it.take(4)
                                        annoScadenza = it
                                    },
                                    modifier = Modifier
                                        .width(80.dp)
                                        .focusRequester(annoScadenzaFocusRequested),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Number // Tastiera numerica
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { cvcFocusRequested.requestFocus() }
                                    ),
                                )
                                OutlinedTextField(
                                    value = codiceCvvCvc, onValueChange = {
                                        codiceCvvCvc = it
                                        isCvcValid()
                                    },
                                    shape = RoundedCornerShape(15.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Number // Tastiera numerica
                                    ),
                                    modifier = Modifier
                                        .height(50.dp)
                                        .focusRequester(cvcFocusRequested),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedBorderColor = if (isCvcValid()) Color(
                                            0xFF0EA639
                                        ) else if (!isCvcValid() && codiceCvvCvc.isNotEmpty()) Color(
                                            0xFF9B0404
                                        ) else Color.Gray,
                                        focusedBorderColor = if (isCvcValid()) Color(0xFF0EA639) else if (!isCvcValid() && codiceCvvCvc.isNotEmpty()) Color(
                                            0xFF9B0404
                                        ) else Color.Gray,
                                    )
                                )

                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),

                                ) {
                                TextButton(onClick = { isDialogVisible = false }) {
                                    Text("ANNULLA", color = Color(0xFF9B0404))
                                }
                                Spacer(modifier = Modifier.width(80.dp))


                                TextButton(
                                    onClick = {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            viewModel.salvaCarta(
                                                CreaCarta(numeroCarta, nomeTitolare, codiceCvvCvc,
                                                    if (meseScadenza.toInt() < 10) {"$annoScadenza-0$meseScadenza-01T00:00:00+00:00"} else {"$annoScadenza-$meseScadenza-01T00:00:00+00:00"}
                                                ), listener)
                                            isDialogVisible = false
                                        }
                                    },
                                    enabled = checkField()
                                ) {
                                    Text("CONFERMA")
                                }
                            }
                            if (openDialog.value) {
                                DatePickerDialog(onDismissRequest = {
                                    openDialog.value = false
                                }, confirmButton = {
                                    TextButton(onClick = {
                                        cvcFocusRequested.requestFocus()
                                        openDialog.value = false
                                    }) {
                                        Text("CONFERMA")
                                    }
                                }, dismissButton = {
                                    TextButton(onClick = {
                                        openDialog.value = false
                                    }) {
                                        Text("CANCEL")
                                    }
                                }) {
                                    DatePicker(
                                        state = state
                                    )
                                }
                            }


                        }
                    }
                }
            }


            if (deleteAlertDialog) {
                Dialog(onDismissRequest = { deleteAlertDialog = false }) {
                    AlertDialog(title = {
                        Text(
                            text = "ATTENZIONE!",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                        text = {
                            Text(
                                text = "Sei sicuro di rimuovere questa carta?",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        onDismissRequest = { deleteAlertDialog = false }, confirmButton = {
                            TextButton(
                                onClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        viewModel.deleteCarta(
                                            listaCarte[selectedIndex].id,
                                            listener
                                        )
                                        delay(300)
                                        viewModel.mostraCarte(listener)
                                        deleteAlertDialog = false
                                    }
                                }, modifier = Modifier.align(Alignment.CenterEnd),
                                content = { Text(text = "Cancella", fontSize = 15.sp) }
                            )
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    deleteAlertDialog = false
                                },
                                content = {
                                    Text(
                                        text = "Annulla",
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }, modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .offset((-80).dp)
                            )

                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSchermataPagamentiProfilo() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataPagamentiProfilo(navController)
    }
}



