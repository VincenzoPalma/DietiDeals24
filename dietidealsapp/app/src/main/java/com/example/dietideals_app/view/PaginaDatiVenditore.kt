package com.example.dietideals_app.view


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.model.ContoCorrente
import com.example.dietideals_app.model.dto.CreaContoCorrente
import com.example.dietideals_app.model.enum.RuoloUtente
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.viewmodel.PaginaDatiVenditoreViewModel
import com.example.dietideals_app.viewmodel.PaginaRegistrazioneViewModel
import com.example.dietideals_app.viewmodel.listener.ContoCorrenteListener
import com.example.dietideals_app.viewmodel.listener.UtenteListener
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class PaginaDatiVenditore : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()


                }
            }
        }

    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataDatiVenditore(navController: NavController) {

    var partitaIva by remember { mutableStateOf("") } //11 caratteri numerici
    var nomeTitolare by remember { mutableStateOf("") }
    var codiceBicSwift by remember { mutableStateOf("") } //8-11 cifre
    var iban by remember { mutableStateOf("") } /*27caratteri lettere e numeri*/
    var isCodiceBicSwiftValid by remember { mutableStateOf(false) }
    var isNomeTitolareValid by remember { mutableStateOf(false) }
    var isIbanValid by remember { mutableStateOf(false) }
    var isPartitaIvaValid by remember { mutableStateOf(false) }
    val nomeTitolareFocusRequester = remember { FocusRequester() }
    val bicSwiftCodeFocusReqeuster = remember { FocusRequester() }
    val ibanFocusRequest = remember { FocusRequester() }
    val viewModelControlloDati = PaginaRegistrazioneViewModel()
    val viewModelDatiVenditore = PaginaDatiVenditoreViewModel()
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val currentPage = remember { mutableIntStateOf(0) }
    var isUtenteVenditore by remember { mutableStateOf(false) }
    var campiModificabili by remember { mutableStateOf(true) }
    val storage = Firebase.storage
    val storageRef = storage.reference
    var idContoCorrente by remember { mutableStateOf<UUID?>(null) }

    val listenerUtente = remember {
        object : UtenteListener {
            override fun onRuoloLoaded(ruolo: RuoloUtente) {
                if (ruolo == RuoloUtente.VENDITORE) {
                    isUtenteVenditore = true
                    campiModificabili = !isUtenteVenditore
                }
            }

            override fun onPartitaIvaLoaded(partitaIvaUtente: String) {
                partitaIva = partitaIvaUtente
                isPartitaIvaValid = viewModelControlloDati.isValidPartitaIva(partitaIva)
            }

            override fun onError() {
                //
            }
        }
    }

    val listenerContoCorrente = remember {
        object : ContoCorrenteListener {
            override fun onContoCorrenteLoaded(contoCorrenteUtente: ContoCorrente) {
                nomeTitolare = contoCorrenteUtente.nomeTitolare
                codiceBicSwift = contoCorrenteUtente.codiceBicSwift
                iban = contoCorrenteUtente.iban
                isNomeTitolareValid = viewModelControlloDati.isValidNomeTitolare(nomeTitolare)
                isCodiceBicSwiftValid = viewModelControlloDati.isValidCodiceBicSwift(codiceBicSwift)
                isIbanValid = viewModelControlloDati.isValidIban(iban)
                idContoCorrente = contoCorrenteUtente.id
            }

            override fun onError() {
                //
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModelDatiVenditore.isUtenteVenditore(listenerUtente)
        delay(300)
        if (isUtenteVenditore) {
            viewModelDatiVenditore.getPartitaIva(listenerUtente)
            viewModelDatiVenditore.getContoCorrente(listenerContoCorrente)
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (background, topBar, documentRow, partitaIvaTextField, bankAccountTitle, ownerTextField, bicSwiftCodeTextField, ibanTextField, buttons) = createRefs()

        // Immagine di sfondo
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
        TopAppBar(
            title = {
                Text(
                    text = "DATI VENDITORE",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold, // Imposta il grassetto
                    fontSize = 40.sp
                ) // Imposta la dimensione del testo)
            },
            modifier = Modifier.constrainAs(topBar)
            {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            },
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()


                        }
                        .size(35.dp)
                )

            },
            colors = TopAppBarColors(
                containerColor = (MaterialTheme.colorScheme.primary),
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
                scrolledContainerColor = Color.White
            ), actions = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_brush_24),
                    contentDescription = "Modifica Dati",
                    modifier = if (!isUtenteVenditore) {
                        Modifier.alpha(0f)
                    } else {
                        Modifier
                            .size(35.dp)
                            .clickable { campiModificabili = !campiModificabili }
                    }
                )
            }
        )
        Row(
            modifier = Modifier
                .constrainAs(documentRow) {
                    top.linkTo(topBar.bottom, margin = 25.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically, // Allinea verticalmente rispetto al centro
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Aggiunge uno spazio di 8.dp tra gli elementi
        ) {
            Text(
                text = "CARICA DOCUMENTO IN FORMATO PDF",
                fontSize = 12.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.width(8.dp))
            LocalContext.current as ComponentActivity
            val getContent =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                    uri?.let {
                        selectedFileUri = it
                    }
                }

            ElevatedButton(
                enabled = campiModificabili,
                onClick = {
                    getContent.launch("application/pdf")

                },
            ) {
                Text(
                    text = if (selectedFileUri == null) "CARICA" else "CARICATO",
                    fontSize = 10.sp,
                )
            }
        }
        OutlinedTextField(
            enabled = campiModificabili,
            value = partitaIva,
            onValueChange = {
                partitaIva = it
                isPartitaIvaValid = viewModelControlloDati.isValidPartitaIva(it)
            },
            label = {
                Text(
                    "Partita Iva",
                    color = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Black
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_balance_24),
                    contentDescription = null,
                    tint = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
                focusedBorderColor = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (isPartitaIvaValid) R.drawable.baseline_done_24 else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) R.drawable.baseline_close_24 else R.drawable.empty),
                    contentDescription = null,
                    tint = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                    modifier = if (partitaIva.isEmpty()) Modifier.alpha(0f) else Modifier
                )
            },

            modifier = Modifier
                .constrainAs(partitaIvaTextField) {
                    top.linkTo(documentRow.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(
                onNext = { nomeTitolareFocusRequester.requestFocus() }
            ),
            shape = RoundedCornerShape(15.dp),
        )

        Text(text = "DATI CONTO CORRENTE", textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(bankAccountTitle) {
                top.linkTo(partitaIvaTextField.bottom, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        OutlinedTextField(
            enabled = campiModificabili,
            value = nomeTitolare,
            onValueChange = {
                nomeTitolare = it
                isNomeTitolareValid = viewModelControlloDati.isValidNomeTitolare(it)
            },
            label = {
                Text(
                    "Nome Titolare",
                    color = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Black
                )
            },

            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Gray,
                focusedBorderColor = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Gray,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = null,
                    tint = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Gray,
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (nomeTitolare.isNotEmpty()) R.drawable.baseline_done_24 else R.drawable.empty),
                    contentDescription = null,
                    tint = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Gray,
                    modifier = if (nomeTitolare.isEmpty()) Modifier.alpha(0f) else Modifier
                )
            },
            modifier = Modifier
                .constrainAs(ownerTextField) {
                    top.linkTo(bankAccountTitle.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp)
                .focusRequester(nomeTitolareFocusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { bicSwiftCodeFocusReqeuster.requestFocus() }
            ),
            shape = RoundedCornerShape(15.dp),
        )


        OutlinedTextField(
            enabled = campiModificabili,
            value = codiceBicSwift,
            onValueChange = {
                codiceBicSwift = it
                isCodiceBicSwiftValid = viewModelControlloDati.isValidCodiceBicSwift(it)
            },
            label = {
                Text(
                    "Codice BIC-SWIFT",
                    color = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Black,
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_assured_workload_24),
                    contentDescription = null,
                    tint = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,

                    )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
                focusedBorderColor = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (isCodiceBicSwiftValid) R.drawable.baseline_done_24 else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) R.drawable.baseline_close_24 else R.drawable.empty),
                    contentDescription = null,
                    tint = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                    modifier = if (codiceBicSwift.isEmpty()) Modifier.alpha(0f) else Modifier
                )
            },
            modifier = Modifier
                .constrainAs(bicSwiftCodeTextField) {
                    top.linkTo(ownerTextField.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp)
                .focusRequester(bicSwiftCodeFocusReqeuster),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { ibanFocusRequest.requestFocus() }
            ),
            shape = RoundedCornerShape(15.dp),
        )


        OutlinedTextField(
            enabled = campiModificabili,
            value = iban,
            onValueChange = {
                iban = it
                isIbanValid = viewModelControlloDati.isValidIban(it)
            },
            label = {
                Text(
                    "IBAN",
                    color = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Black
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
                focusedBorderColor = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_balance_wallet_24),
                    contentDescription = null,
                    tint = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (isIbanValid) R.drawable.baseline_done_24 else if (!isIbanValid && iban.isNotEmpty()) R.drawable.baseline_close_24 else R.drawable.empty),
                    contentDescription = null,
                    tint = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                    modifier = if (iban.isEmpty()) Modifier.alpha(0f) else Modifier
                )
            },

            modifier = Modifier
                .constrainAs(ibanTextField) {
                    top.linkTo(bicSwiftCodeTextField.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp)
                .focusRequester(ibanFocusRequest),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(15.dp),
        )
        Row(
            modifier = Modifier
                .constrainAs(buttons) {
                    top.linkTo(ibanTextField.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {

            // Bottone Conferma
            if (campiModificabili) {
                ElevatedButton(
                    enabled = viewModelControlloDati.checkFieldsDatiVenditore(
                        nomeTitolare,
                        codiceBicSwift,
                        partitaIva,
                        iban,
                        selectedFileUri
                    ),
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            var fileDownloadUrl: String? = null
                            if (selectedFileUri != null) {
                                val documentoVenditoreRef =
                                    storageRef.child("DocumentiVenditore/${selectedFileUri?.lastPathSegment}")
                                selectedFileUri?.let { documentoVenditoreRef.putFile(it).await() }
                                documentoVenditoreRef.downloadUrl.addOnSuccessListener { uri ->
                                    fileDownloadUrl = uri.toString()
                                }.addOnFailureListener { exception ->
                                    println("Errore durante il recupero dell'URL di download: $exception")
                                }
                            }
                            delay(500)
                            if (fileDownloadUrl != null) {
                                viewModelDatiVenditore.modificaDocumentoVenditore(fileDownloadUrl!!)
                            }
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            if (isUtenteVenditore) {
                                idContoCorrente?.let {
                                    ContoCorrente(
                                        it,
                                        nomeTitolare,
                                        codiceBicSwift,
                                        iban
                                    )
                                }?.let {
                                    viewModelDatiVenditore.modifyContoCorrente(
                                        it
                                    )
                                }
                            } else {
                                viewModelDatiVenditore.saveContoCorrente(
                                    CreaContoCorrente(
                                        iban,
                                        nomeTitolare,
                                        codiceBicSwift
                                    )
                                )
                            }
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            viewModelDatiVenditore.modificaPartitaIva(partitaIva)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            if (!isUtenteVenditore) {
                                viewModelDatiVenditore.setUtenteVenditore()
                            }
                        }
                        campiModificabili = false
                    }
                ) {
                    Text(
                        text = "CONFERMA",
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewSchermataDatiVenditore() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataDatiVenditore(navController)
    }
}