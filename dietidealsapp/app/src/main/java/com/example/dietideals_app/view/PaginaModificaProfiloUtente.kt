package com.example.dietideals_app.view


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.viewmodel.PaginaModificaProfiloUtenteViewModel
import com.example.dietideals_app.viewmodel.listener.DatiUtenteListener
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PaginaModificaProfiloUtente : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SchermataModificaProfilo(navController)
                }
            }
        }

    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataModificaProfilo(navController: NavController) {
    val MAX_LENGTH = 300
    val viewModel = PaginaModificaProfiloUtenteViewModel()
    val sitoWebFocusRequester = remember { FocusRequester() }
    val addressFocusRequester = remember { FocusRequester() }
    val storage = Firebase.storage
    val storageRef = storage.reference
    val facebookLinkFocusRequester = remember { FocusRequester() }
    val twitterLinkFocusRequester = remember { FocusRequester() }
    var datiProfiloUtente by remember { mutableStateOf<DatiProfiloUtente?>(null) }
    var shortBio by remember { mutableStateOf("") }
    var sitoWeb by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var instagramLink by remember { mutableStateOf("") }
    var facebookLink by remember { mutableStateOf("") }
    var twitterLink by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        datiProfiloUtente = Gson().fromJson(
            navController.previousBackStackEntry?.savedStateHandle?.get<String>("datiProfiloUtente"),
            DatiProfiloUtente::class.java
        )
        shortBio =
            if (datiProfiloUtente?.descrizione == null) "" else datiProfiloUtente!!.descrizione.toString()
        sitoWeb =
            if (datiProfiloUtente?.sitoWeb == null) "" else datiProfiloUtente!!.sitoWeb.toString()
        address =
            if (datiProfiloUtente?.indirizzo == null) "" else datiProfiloUtente!!.indirizzo.toString()
        instagramLink =
            if (datiProfiloUtente?.instagram == null) "" else datiProfiloUtente!!.instagram.toString()
        facebookLink =
            if (datiProfiloUtente?.facebook == null) "" else datiProfiloUtente!!.facebook.toString()
        twitterLink =
            if (datiProfiloUtente?.twitter == null) "" else datiProfiloUtente!!.twitter.toString()
    }

    val listener = remember {
        object : DatiUtenteListener {
            override fun onDataLoaded(datiUtente: DatiProfiloUtente?) {
                //
            }

            override fun onDatiModificati(utente: Utente) {
                //
            }

            override fun onError() {
                // Gestisci l'errore
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (background, topBar, immagineProfilo, nomeUtente, usernameUtente, shortBioLabel, shortBioUtente, sitoWebUtente, social, bottoneModifica, indirizzoUtente, bottoneConferma) = createRefs()
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
            start.linkTo(parent.start)
            end.linkTo(parent.end)

        }, title = {
            Text(
                text = "PROFILO",
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
        val screenWidth = LocalDensity.current.run {
            LocalConfiguration.current.screenWidthDp.dp
        }

        LocalContext.current as ComponentActivity
        val getContent =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    selectedImageUri = it
                }
            }
        Box(
            modifier = Modifier
                .offset(x = (screenWidth / 2) - 40.dp)
                .constrainAs(immagineProfilo)
                {
                    top.linkTo(parent.top, margin = 70.dp)
                }
                .size(80.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        ) {

            AsyncImage(
                model = if (selectedImageUri == null) {
                    datiProfiloUtente?.urlFotoProfilo
                } else {
                    selectedImageUri
                },
                placeholder = painterResource(id = com.facebook.login.R.drawable.com_facebook_profile_picture_blank_portrait),
                error = painterResource(id = com.facebook.login.R.drawable.com_facebook_profile_picture_blank_portrait),
                contentDescription = "Immagine del profilo dell'utente",
                modifier = Modifier
                    .clickable { getContent.launch("image/*") }
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

        }
        Text(
            text = datiProfiloUtente?.nome + " " + datiProfiloUtente?.cognome,
            modifier = Modifier
                .constrainAs(nomeUtente)
                {
                    top.linkTo(immagineProfilo.bottom)
                }

                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "@" + datiProfiloUtente?.username,
            modifier = Modifier
                .constrainAs(usernameUtente) {
                    top.linkTo(nomeUtente.bottom)
                }

                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.Gray// Imposta il grassetto
        )
        Text(
            text = "Short Bio:",
            modifier = Modifier
                .constrainAs(shortBioLabel)
                {
                    top.linkTo(usernameUtente.bottom)
                }

                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold

        )

        //.offset(y = 235.dp)
        OutlinedTextField(value = shortBio,
            onValueChange = { shortBio = it },
            supportingText = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End)
                {
                    Text(
                        text = shortBio.length.toString() + "/$MAX_LENGTH",
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                }
            },
            shape = RoundedCornerShape(15.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { sitoWebFocusRequester.requestFocus() }
            ),
            modifier = Modifier
                .height(150.dp)
                .constrainAs(shortBioUtente)
                {
                    top.linkTo(shortBioLabel.bottom)
                }
                .fillMaxWidth()
                .padding(8.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(sitoWebUtente)
                {
                    top.linkTo(shortBioUtente.bottom)
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sito Web:",
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            LocalContext.current

            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
                // Handle the result if needed
            }

            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp
                    )
                ) {
                    append("www.mariorossi.com")
                    addStringAnnotation("URL", "https://www.example.com", 0, length)
                }
            }
            Spacer(modifier = Modifier.width(7.dp))

            OutlinedTextField(value = sitoWeb, onValueChange = { sitoWeb = it },
                shape = RoundedCornerShape(15.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .focusRequester(sitoWebFocusRequester)
                    .height(50.dp),
                keyboardActions = KeyboardActions(
                    onNext = { addressFocusRequester.requestFocus() }
                ))


        }



        @Composable
        fun IconWithText(iconId: Int, text: String, color: Color) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(2.dp)
            ) {
                Image(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )

                Text(
                    text = text,
                    fontSize = 15.sp,
                    color = color
                )//
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(social)
                {
                    top.linkTo(sitoWebUtente.bottom)
                }
                .padding(8.dp)


        ) {
            Spacer(
                modifier = Modifier
                    .width(20.dp)

            )
            IconWithText(
                iconId = R.drawable.instagramicon,
                text = "Instagram",
                Color.Black
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
            IconWithText(
                iconId = R.drawable.facebookicon,
                text = "Facebook",
                Color.Black
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
            IconWithText(
                iconId = R.drawable.twittericon,
                text = "Twitter",
                Color.Black
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
        }
        var isDialogVisible by remember { mutableStateOf(false) }
        ElevatedButton(
            onClick = { isDialogVisible = true }, modifier = Modifier
                .constrainAs(bottoneModifica)
                {
                    top.linkTo(social.bottom)
                }
                .padding(8.dp)
        ) {
            Text(text = "Modifica Account Social")


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
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Instagram",

                                )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.instagramicon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(value = instagramLink,
                                onValueChange = { instagramLink = it },
                                shape = RoundedCornerShape(15.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .height(50.dp),
                                keyboardActions = KeyboardActions(
                                    onNext = { facebookLinkFocusRequester.requestFocus() }
                                ))
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Facebook",

                                )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.facebookicon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(value = facebookLink,
                                onValueChange = { facebookLink = it },
                                shape = RoundedCornerShape(15.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .height(50.dp)
                                    .focusRequester(facebookLinkFocusRequester),
                                keyboardActions = KeyboardActions(
                                    onNext = { twitterLinkFocusRequester.requestFocus() }
                                ))
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Twitter",

                                )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.twittericon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                value = twitterLink, onValueChange = { twitterLink = it },
                                shape = RoundedCornerShape(15.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                modifier = Modifier
                                    .height(50.dp)
                                    .focusRequester(twitterLinkFocusRequester),
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {


                            TextButton(onClick = { isDialogVisible = false }) {
                                Text("OK")
                            }
                        }
                    }

                }

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(indirizzoUtente)
                {
                    top.linkTo(bottoneModifica.bottom)
                }

                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Indirizzo:",
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                value = address, onValueChange = { address = it },
                shape = RoundedCornerShape(15.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ), modifier = Modifier
                    .focusRequester(addressFocusRequester)
                    .height(50.dp)
            )


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottoneConferma) {
                    top.linkTo(indirizzoUtente.bottom)
                    end.linkTo(parent.end)
                }

                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.End

        ) {
            ElevatedButton(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        var downloadUrl: String? = null
                        if (selectedImageUri != null) {
                            val immagineProfiloRef =
                                storageRef.child("ImmaginiProfilo/${selectedImageUri?.lastPathSegment}")
                            selectedImageUri?.let { immagineProfiloRef.putFile(it).await() }
                            immagineProfiloRef.downloadUrl.addOnSuccessListener { uri ->
                                downloadUrl = uri.toString()
                            }.addOnFailureListener { exception ->
                                println("Errore durante il recupero dell'URL di download: $exception")
                            }
                        }
                        delay(500)
                        viewModel.modificaDatiUtente(
                            DatiProfiloUtente(
                                shortBio.ifEmpty { null },
                                facebookLink.ifEmpty { null },
                                instagramLink.ifEmpty { null },
                                twitterLink.ifEmpty { null },
                                sitoWeb.ifEmpty { null },
                                address.ifEmpty { null },
                                if (downloadUrl == null) datiProfiloUtente?.urlFotoProfilo else downloadUrl,
                                datiProfiloUtente?.username,
                                datiProfiloUtente?.nome,
                                datiProfiloUtente?.cognome
                            ), listener
                        )
                        delay(800)
                        navController.popBackStack()
                    }
                }
            ) {
                Text(text = "Conferma")

            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewSchermataModificaProfilo() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataModificaProfilo(navController)
    }
}
