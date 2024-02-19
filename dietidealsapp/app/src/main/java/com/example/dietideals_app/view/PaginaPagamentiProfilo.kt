package com.example.dietideals_app.view


import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.dietideals_app.model.Carta
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import java.text.SimpleDateFormat
import java.util.Date

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
fun SchermataPagamentiProfilo(navController: NavController) {

    var nomeTitolare by remember { mutableStateOf("") }
    var numeroCarta by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    val numeroCartaFocusrequested = remember { FocusRequester() }
    val dataScadenzaFocusRequested = remember { FocusRequester() }
    val cvcFocusRequested = remember { FocusRequester() }

    var isDialogVisible by remember { mutableStateOf(false) }
    val state = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }
    val listaCarte = mutableListOf<Carta>()

    fun pulisciCampi() {
        nomeTitolare = ""
        numeroCarta = ""
        cvc = ""
        state.selectedDateMillis = null
    }

    fun checkField(): Boolean {
        return nomeTitolare.isNotEmpty() && numeroCarta.isNotEmpty() && cvc.isNotEmpty() && state.selectedDateMillis != null
    }

    fun isNumeroCartaValid(): Boolean {
        // Controllo se il numero di carta ha una lunghezza valida
        return numeroCarta.length == 16
    }

    fun isCvcValid(): Boolean {
        // Controllo se il numero di carta ha una lunghezza valida
        return cvc.length == 3
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
                ) // Imposta la dimensione del testo)
            },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("SchermataProfiloUtente")


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

            LazyColumn(
                modifier = Modifier
                    .offset(y = 80.dp)

                    .fillMaxSize()
                    .padding(16.dp)

            ) {
                items(listaCarte.size) { index ->
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
                                            modifier = Modifier.clickable {
                                                listaCarte.removeAt(index)
                                            })

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
                                        text = "Carta Mastercard\nMario Rossi\n **** 4902",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        modifier = Modifier
                                            .padding(4.dp)
                                    )


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

                                }

                                Icon(painter = painterResource(id = R.drawable.baseline_add_card_24),
                                    contentDescription = "deleteCard",
                                    modifier = Modifier
                                        .fillMaxSize(0.7f)
                                        .clickable {
                                            pulisciCampi()
                                            isDialogVisible = true
                                        }
                                )

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
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next
                                ),
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
                                keyboardActions = KeyboardActions(
                                    onNext = { dataScadenzaFocusRequested.requestFocus() }
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



                            @SuppressLint("SimpleDateFormat")
                            fun convertMillisToDate(millis: Long): String {
                                val formatter = SimpleDateFormat("MM/yyyy")
                                return formatter.format(Date(millis))
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                TextButton(
                                    onClick = { openDialog.value = true },
                                ) {

                                    Text(
                                        text = if (state.selectedDateMillis == null) "__/____" else convertMillisToDate(
                                            state.selectedDateMillis!!
                                        ),
                                        modifier = Modifier

                                            .focusRequester(dataScadenzaFocusRequested)


                                    )
                                    Spacer(modifier = Modifier.width(70.dp))
                                    OutlinedTextField(
                                        value = cvc, onValueChange = {
                                            cvc = it
                                            isCvcValid()
                                        },
                                        shape = RoundedCornerShape(15.dp),
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            imeAction = ImeAction.Done
                                        ),
                                        modifier = Modifier
                                            .height(50.dp)
                                            .focusRequester(cvcFocusRequested),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedBorderColor = if (isCvcValid()) Color(
                                                0xFF0EA639
                                            ) else if (!isCvcValid() && cvc.isNotEmpty()) Color(
                                                0xFF9B0404
                                            ) else Color.Gray,
                                            focusedBorderColor = if (isCvcValid()) Color(0xFF0EA639) else if (!isCvcValid() && cvc.isNotEmpty()) Color(
                                                0xFF9B0404
                                            ) else Color.Gray,
                                        )
                                    )

                                }


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

                                    },
                                    enabled = checkField()
                                ) {
                                    Text("OK")
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
                                        Text("OK")
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

