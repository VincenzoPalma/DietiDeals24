package com.example.dietideals_app.view

import android.content.pm.PackageManager
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.dietideals_app.presenter.AutenticazionePresenter
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.ui.theme.titleCustomFont

class RegistrazioneDatiVenditore : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "SchermataAutenticazione") {
                        composable("SchermataAutenticazione") { SchermataAutenticazione(navController = navController) }
                        composable("SchermataRegistrazione") { SchermataRegistrazione(navController = navController) }
                        composable("SchermataImmagineProfilo") { SchermataImmagineProfilo(navController = navController) }
                        composable("SchermataDiventaVenditore") { SchermataDiventaVenditore(navController = navController) }
                        composable("SchermataRegistrazioneSuccesso") { SchermataRegistrazioneSuccesso(navController = navController) }
                        composable("SchermataRegistrazioneDatiVenditore"){SchermataRegistrazioneDatiVenditore(navController = navController)}
                    }
                }
            }
        }
    }
}

@Composable
fun SchermataRegistrazioneDatiVenditore(navController: NavController) {
    // Ottieni l'immagine di sfondo da resources
    val background = painterResource(id = R.drawable.sfondo3)

    // Crea un presenter per la registrazione
    val presenter = AutenticazionePresenter()
    var partitaIva  by remember { mutableStateOf("")}
    var nomeTitolare by remember { mutableStateOf("")}
    var codiceBicSwift by remember { mutableStateOf("")}
    var iban by remember { mutableStateOf("")}


    val nomeTitolareFocusRequester = remember { FocusRequester() }
    val bicSwiftCodeFocusReqeuster = remember { FocusRequester() }
    val ibanFocusRequest = remember { FocusRequester() }




        ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backgroundImage, title, subTitle,documentRow,partitaIvaTextField,bankAccountTitle,ownerTextField,bicSwiftCodeTextField,ibanTextField,buttons) = createRefs()

        // Immagine di sfondo
        Image(
            painter = background,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(backgroundImage){
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
        ){
            Text(text = "CARICA DOCUMENTO IN FORMATO PDF",
                fontSize = 12.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )




            Spacer(modifier = Modifier.width(8.dp))

            LocalContext.current as ComponentActivity
            var selectedDocument by remember { mutableStateOf<Uri?>(null)}
            // Definisci il contratto per l'activity result
            val getContent = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                // Gestisci l'URI dell'immagine selezionata
                // Puoi eseguire ulteriori operazioni qui, come caricare l'immagine
                uri?.let {
                    selectedDocument = it
                }
            }




            Button(onClick = { getContent.launch("application/pdf") },shape = CutCornerShape(topStart = 0.dp, bottomEnd = 0.dp)) {
                Text(text = if(selectedDocument == null) "CARICA" else "CARICATO",
                    fontSize = 10.sp,
                    )
            }
        }
            OutlinedTextField(value = partitaIva,
                onValueChange = { partitaIva = it},
                label = { Text("PARTITA IVA") },
                leadingIcon = { Icon(painter = painterResource(id =R.drawable.baseline_account_balance_24), contentDescription = null, tint = Color(0xFF0EA639)) },
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
                shape = RoundedCornerShape(15.dp),)

            Text(text = "DATI CONTO CORRENTE"
            , textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(bankAccountTitle){
                    top.linkTo(partitaIvaTextField.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            OutlinedTextField(value = nomeTitolare,
                onValueChange = { nomeTitolare = it},
                label = { Text("Nome Titolare") },
                leadingIcon = { Icon(painter = painterResource(id =R.drawable.baseline_account_circle_24), contentDescription = null, tint = Color(0xFF0EA639)) },
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
                shape = RoundedCornerShape(15.dp),)


            OutlinedTextField(value = codiceBicSwift,
                onValueChange = { codiceBicSwift = it},
                label = { Text("Codice BIC-SWIFT") },
                leadingIcon = { Icon(painter = painterResource(id =R.drawable.baseline_assured_workload_24), contentDescription = null, tint = Color(0xFF0EA639)) },
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
                shape = RoundedCornerShape(15.dp),)
            OutlinedTextField(value = iban,
                onValueChange = { iban = it},
                label = { Text("IBAN") },
                leadingIcon = { Icon(painter = painterResource(id =R.drawable.baseline_account_balance_wallet_24), contentDescription = null, tint = Color(0xFF0EA639)) },
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
                shape = RoundedCornerShape(15.dp),)
            Row(
                modifier = Modifier
                    .constrainAs(buttons){
                        top.linkTo(ibanTextField.bottom, margin = 10.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        bottom.linkTo(parent.bottom,margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Bottone Annulla
                Button(
                    onClick = { presenter.effettuaRegistrazione()
                        navController.navigate("SchermataDiventaVenditore") },
                    shape = CutCornerShape(topStart = 0.dp, bottomEnd = 0.dp),
                    colors = ButtonColors(containerColor=Color(0xFF9b0404), contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.Black)

                ) {
                    Text(
                        text = "ANNULLA",
                        fontSize = 12.sp,
                    )
                }

                // Bottone Conferma
                Button(
                    onClick = { presenter.effettuaRegistrazione()
                navController.navigate("SchermataRegistrazioneSuccesso") },
                    shape = CutCornerShape(topStart = 0.dp, bottomEnd = 0.dp),

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
fun PreviewSchermataRegistrazioneDatiVenditore() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataRegistrazioneDatiVenditore(navController)
    }
}