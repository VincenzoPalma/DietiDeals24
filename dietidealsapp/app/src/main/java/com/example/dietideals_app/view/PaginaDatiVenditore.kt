package com.example.dietideals_app.view


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.ui.theme.titleCustomFont

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
    val background = painterResource(id = R.drawable.sfondo1)
    var partitaIva by remember { mutableStateOf("") } //11 caratteri numerici
    fun isValidPartitaIva(partitaIva: String): Boolean {
        return partitaIva.length == 11 && partitaIva.all { it.isDigit() }
    }

    var nomeTitolare by remember { mutableStateOf("") }
    fun isValidNomeTitolare(nomeTitolare: String): Boolean {
        return nomeTitolare.isNotBlank()
    }

    var codiceBicSwift by remember { mutableStateOf("") } //8-11 cifre
    fun isValidCodiceBicSwift(codiceBicSwift: String): Boolean {
        return codiceBicSwift.length in 8..11
    }

    var iban by remember { mutableStateOf("") } /*27caratteri lettere e numeri*/
    fun isValidIban(iban: String): Boolean {
        return iban.length == 27 && iban.matches(Regex("[A-Za-z0-9]+"))
    }


    var isCodiceBicSwiftValid by remember { mutableStateOf(false) }
    var isIbanValid by remember { mutableStateOf(false) }
    var isPartitaIvaValid by remember { mutableStateOf(false) }

    fun checkFields(): Boolean {
        return isValidNomeTitolare(nomeTitolare) && isValidCodiceBicSwift(codiceBicSwift) && isValidIban(
            iban
        ) && isValidPartitaIva(partitaIva)
    }

    val nomeTitolareFocusRequester = remember { FocusRequester() }
    val bicSwiftCodeFocusReqeuster = remember { FocusRequester() }
    val ibanFocusRequest = remember { FocusRequester() }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backgroundImage, title, subTitle, documentRow, partitaIvaTextField, bankAccountTitle, ownerTextField, bicSwiftCodeTextField, ibanTextField, buttons) = createRefs()

        // Immagine di sfondo
        Image(
            painter = background,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(backgroundImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentScale = ContentScale.Crop
        )

        // Titolo
        Text(
            text = "REGISTRAZIONE",
            color = Color.White,
            fontFamily = titleCustomFont,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title)
                {
                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = "Dati Aggiuntivi",
            color = Color.White,
            fontFamily = titleCustomFont,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(subTitle) {
                top.linkTo(title.top, margin = 40.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Row(
            modifier = Modifier
                .constrainAs(documentRow) {
                    top.linkTo(subTitle.bottom, margin = 50.dp)
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
            var selectedDocument by remember { mutableStateOf<Uri?>(null) }
            // Definisci il contratto per l'activity result
            val getContent =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                    // Gestisci l'URI dell'immagine selezionata
                    // Puoi eseguire ulteriori operazioni qui, come caricare l'immagine
                    uri?.let {
                        selectedDocument = it
                    }
                }




            ElevatedButton(
                onClick = { getContent.launch("application/pdf") },
            ) {
                Text(
                    text = if (selectedDocument == null) "CARICA" else "CARICATO",
                    fontSize = 10.sp,
                )
            }
        }
        OutlinedTextField(
            value = partitaIva,
            onValueChange = {
                partitaIva = it
                isPartitaIvaValid = isValidPartitaIva(it)
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
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
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
            value = nomeTitolare,
            onValueChange = { nomeTitolare = it },
            label = {
                Text(
                    "Nome Titolare",
                    color = if (nomeTitolare.isNotEmpty()) Color(0xFF0EA639) else Color.Black
                )
            },

            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (nomeTitolare.isNotEmpty()) Color(0xFF0EA639) else Color.Gray,
                focusedBorderColor = if (nomeTitolare.isNotEmpty()) Color(0xFF0EA639) else Color.Gray,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = null,
                    tint = if (nomeTitolare.isNotEmpty()) Color(0xFF0EA639) else Color.Gray,
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (nomeTitolare.isNotEmpty()) R.drawable.baseline_done_24 else R.drawable.empty),
                    contentDescription = null,
                    tint = if (nomeTitolare.isNotEmpty()) Color(0xFF0EA639) else Color.Gray,
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
            value = codiceBicSwift,
            onValueChange = {
                codiceBicSwift = it
                isCodiceBicSwiftValid = isValidCodiceBicSwift(it)
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
            value = iban,
            onValueChange = {
                iban = it
                isIbanValid = isValidIban(it)
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Bottone Annulla
            ElevatedButton(
                onClick = {
                },

                colors = ButtonColors(
                    containerColor = Color(0xFF9b0404),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )

            ) {
                Text(
                    text = "ANNULLA",
                    fontSize = 12.sp,
                )
            }

            // Bottone Conferma
            ElevatedButton(
                enabled = checkFields(),
                onClick = {

                },

                ) {
                Text(
                    text = "CONFERMA",
                    fontSize = 12.sp,
                )
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