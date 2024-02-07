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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
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
import com.example.dietideals_app.ui.theme.DietidealsappTheme

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
                    NavHost(navController = navController, startDestination = "SchermataAutenticazione") {
                        composable("SchermataAutenticazione") { SchermataAutenticazione(navController = navController) }
                        composable("SchermataRegistrazione") { SchermataRegistrazione(navController = navController) }
                        composable("SchermataImmagineProfilo") { SchermataImmagineProfilo(navController = navController) }
                        composable("SchermataDiventaVenditore") { SchermataDiventaVenditore(navController = navController) }
                        composable("SchermataRegistrazioneSuccesso") { SchermataRegistrazioneSuccesso(navController = navController) }
                        composable("SchermataRegistrazioneDatiVenditore"){SchermataRegistrazioneDatiVenditore(navController = navController)}
                        composable("SchermataHome"){SchermataHome(navController = navController)}
                        composable("SchermataProfiloUtente"){SchermataProfiloUtente(navController = navController)}
                        composable("SchermataModificaProfilo"){SchermataModificaProfilo(navController = navController)}
                        composable("SchermataPagamentiProfilo"){SchermataPagamentiProfilo(navController = navController)}
                        composable("SchermataGestioneAste"){SchermataGestioneAste(navController = navController)}
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
    val categorie = arrayOf("Elettronica","Informatica","Giocattoli","Alimentari","Servizi","Arredamento","Auto e Moto","Libri","Abbigliamento","Attrezzi ed utensili","Bellezza","Musica","Arte")
    val colorGreen = 0xFF0EA639
    val colorRed = 0xFF9B0404
    val defaultImage = painterResource(id = R.drawable.defaultimage)
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val tabNames = listOf("All'inglese","Silenziosa","Inversa")
    var nomeAsta by remember{ mutableStateOf("")}
    var sogliaRialzo by remember{ mutableStateOf("")}
    var sogliaRialzoFocusRequested = remember { FocusRequester() }

    var oreIntervallo by remember{ mutableStateOf("")}
    var oreIntervalloFocusRequested = remember { FocusRequester() }
    var minutiIntervallo by remember{ mutableStateOf("")}
    var minutiIntervalloFocusRequested = remember { FocusRequester() }
    var prezzoBase by remember { mutableStateOf("") }
    var descrizione by remember { mutableStateOf("") }
    var prezzoBaseFocusRequested = remember { FocusRequester() }
    var descrizioneFocusRequested = remember { FocusRequester() }
    var currentPage = remember { mutableStateOf(0) }

    var categoriaSelezionata by remember {
        mutableStateOf("Nessuna Categoria")
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
                            onClick = { selectedTabIndex.intValue = index
                                      currentPage.value = 0},
                            // Puoi personalizzare l'aspetto delle schede qui, ad esempio aggiungendo icone, testo, etc.
                            text = {
                                Text(text = tabNames[index],fontSize = 15.sp)
                            }
                        )
                    }
                }

                // Contenuto dinamico in base alla scheda corrente
                when (selectedTabIndex.value) {
                    0 -> {
                        when(currentPage.value){
                            0-> {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp))
                                {
                                    Text(
                                        text = "DETTAGLI ASTA",
                                        fontSize = 25.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                   OutlinedTextField(value = nomeAsta, onValueChange = {nomeAsta = it},
                                   label = { Text(text = "Nome Asta")}, modifier = Modifier
                                           .fillMaxWidth()
                                           .offset(y = 40.dp),
                                       keyboardOptions = KeyboardOptions.Default.copy(
                                           imeAction = ImeAction.Next
                                       ),
                                       keyboardActions = KeyboardActions(
                                           onNext = { prezzoBaseFocusRequested.requestFocus() }
                                       ))


                                    Row (modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = 120.dp),
                                        verticalAlignment = Alignment.CenterVertically){

                                        Text(text = "Prezzo Base: € ", fontWeight = FontWeight.Bold,fontSize = 20.sp)

                                        TextField(value = prezzoBase, onValueChange = {prezzoBase = it},
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
                                    ElevatedButton(onClick = { isDialogVisible = true},modifier = Modifier.offset(y=200.dp)) {
                                        Text(text = "Categoria:\n"+categoriaSelezionata, )

                                    }
                                    OutlinedTextField(value = descrizione, onValueChange = {descrizione= it},
                                        label = {Text (text="Descrizione")},
                                        modifier = Modifier
                                            .offset(y = 270.dp)
                                            .fillMaxWidth()
                                            .height(150.dp)
                                            .focusRequester(descrizioneFocusRequested),
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            imeAction = ImeAction.Done
                                        ),
                                        )
                                    Row(modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        ){
                                        ElevatedButton(onClick = { currentPage.value= 1 },modifier = Modifier
                                            .offset(y = 430.dp)
                                            .padding(8.dp)) {
                                            Text (text = "Avanti",fontSize = 20.sp)

                                        }

                                    }

                                }
                            }
                                1->{
                                    Box(modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp))
                                    {
                                        Text(
                                            text = "DETTAGLI OPZIONALI",
                                            fontSize = 25.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .offset(y = 10.dp), horizontalArrangement = Arrangement.Center){

                                            Image(
                                                painter = defaultImage, // Rimpiazza con la tua immagine
                                                contentDescription = null,
                                                modifier= Modifier.size (250.dp)

                                            )


                                        }

                                        Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .offset(y = 250.dp), verticalAlignment = Alignment.CenterVertically)
                                        {
                                            Text(
                                                text = "Soglia di rialzo € ",
                                                fontSize = 25.sp,

                                                fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                            )
                                            TextField(value = sogliaRialzo, onValueChange = {sogliaRialzo = it},
                                                keyboardOptions = KeyboardOptions.Default.copy(
                                                    imeAction = ImeAction.Next
                                                ),
                                                keyboardActions = KeyboardActions(
                                                    onNext = { oreIntervalloFocusRequested.requestFocus() }
                                                ),
                                                modifier = Modifier
                                                    .width(100.dp)
                                                    .height(50.dp),
                                                textStyle = TextStyle(fontSize = 15.sp)
                                            )


                                        }
                                        Text(
                                            text = "INTERVALLO DI TEMPO OFFERTA",
                                            fontSize = 25.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .offset(y = 320.dp)
                                        )
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .offset(y = 400.dp), verticalAlignment = Alignment.CenterVertically)
                                        {
                                            TextField(value = oreIntervallo, onValueChange = {oreIntervallo = it},
                                                keyboardOptions = KeyboardOptions.Default.copy(
                                                    imeAction = ImeAction.Next
                                                ),
                                                keyboardActions = KeyboardActions(
                                                    onNext = { minutiIntervalloFocusRequested.requestFocus() }
                                                ),
                                                modifier = Modifier
                                                    .focusRequester(oreIntervalloFocusRequested)
                                                    .width(100.dp)
                                                    .height(50.dp),
                                                textStyle = TextStyle(fontSize = 15.sp)
                                            )
                                            Text(
                                                text = "ORE",
                                                fontSize = 20.sp,

                                                fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            TextField(value = minutiIntervallo, onValueChange = {minutiIntervallo = it},
                                                keyboardOptions = KeyboardOptions.Default.copy(
                                                    imeAction = ImeAction.Done
                                                ),

                                                modifier = Modifier
                                                    .focusRequester(minutiIntervalloFocusRequested)
                                                    .width(100.dp)
                                                    .height(50.dp),
                                                textStyle = TextStyle(fontSize = 15.sp)
                                            )
                                            Text(
                                                text = "MINUTI",
                                                fontSize = 20.sp,

                                                fontWeight = FontWeight.Bold, // Imposta il testo in grassetto
                                            )



                                        }
                                        Row(modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End,
                                        ){
                                            ElevatedButton(onClick = { isConfirmDialogVisible = true },modifier = Modifier
                                                .offset(y = 450.dp)
                                                .padding(8.dp)) {
                                                Text (text = "Conferma",fontSize = 20.sp)

                                            }

                                        }




                                    }
                                }

                            }
                        }



                    1 -> {

                        when(currentPage.value){
                            0-> {
                                // Pagina 1 della scheda 1
                                Text("Contenuto della pagina 1 della scheda 2")
                                Button(onClick = { currentPage.value = 1 }) {
                                    Text("Passa alla pagina 2")
                                }
                            }
                            1->{
                                Text("Contenuto della pagina 2 della scheda 2")
                                Button(onClick = { currentPage.value = 0 }) {
                                    Text("Torna Indietro")
                                }
                            }

                        }
                    }
                    2 -> {
                        // Pagina 3 della scheda 1
                        Text("Contenuto della pagina 3 della scheda 1")
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
                        ){

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
                            Text(text = "ASTA CREATA!\n\n" +
                                    "ASTA CREATA CON SUCCESSO " +
                                    "POTRAI VEDERE LA TUA ASTA IN ASTE ATTIVE", textAlign = TextAlign.Center)
                            TextButton(onClick = {isConfirmDialogVisible = false}) {
                                Text(text = "OK",fontSize = 20.sp)
                                
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