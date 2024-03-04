package com.example.dietideals_app.view


import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.transform.CircleCropTransformation
import com.example.dietideals_app.R
import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.model.enum.RuoloUtente
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.viewmodel.PaginaProfiloUtenteViewModel
import com.example.dietideals_app.viewmodel.listener.DatiUtenteListener
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.util.UUID


class PaginaProfiloUtente : ComponentActivity() {
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
fun SchermataProfiloUtente(navController: NavController) {

    val viewModel = PaginaProfiloUtenteViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val previousBackStackEntry = navController.previousBackStackEntry
    val previousDestination = previousBackStackEntry?.destination?.route
    val upgradeIcon = painterResource(id = R.drawable.baseline_upgrade_24)
    val accountIcon = painterResource(id = R.drawable.baseline_account_circle_24)
    val menuIcon = painterResource(id = R.drawable.baseline_menu_24)
    val backIcon = painterResource(id = R.drawable.baseline_arrow_back_24)
    val logoutIcon = painterResource(id = R.drawable.baseline_logout_24)
    val paymentIcon = painterResource(id = R.drawable.baseline_attach_money_24)
    val profilePicture = painterResource(id = R.drawable.ic_launcher_foreground)
    val homeIcon = R.drawable.baseline_home_24
    val gestisciAste = R.drawable.line_chart_svgrepo_com
    val creaAsta = R.drawable.hand_money_cash_hold_svgrepo_com
    val account = R.drawable.baseline_manage_accounts_24
    val colorGreen = 0xFF0EA639
    var datiProfiloUtente by remember { mutableStateOf<DatiProfiloUtente?>(null) }

    val listener = remember {
        object : DatiUtenteListener {
            override fun onDataLoaded(datiUtente: DatiProfiloUtente?) {
                datiProfiloUtente = datiUtente
            }

            override fun onError() {
                // Gestisci l'errore
            }
        }
    }

    LaunchedEffect(Unit) {
        val uuidString =
            navController.previousBackStackEntry?.savedStateHandle?.get<String>("idUtente")
        if (uuidString.isNullOrBlank()) {
            viewModel.visualizzaDatiUtente(null, listener)
        } else {
            viewModel.visualizzaDatiUtente(UUID.fromString(uuidString), listener)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.7f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { navController.navigate("SchermataProfiloUtente") }
                    )
                    {
                        Icon(
                            painter = accountIcon,
                            contentDescription = "Profilo Utente",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Dati Utente", fontSize = 25.sp)
                    }



                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { navController.navigate("SchermataPagamentiProfilo") }
                    )
                    {
                        Icon(
                            painter = paymentIcon,
                            contentDescription = "Pagamenti",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Pagamenti", fontSize = 25.sp)
                    }



                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { navController.navigate("SchermataRegistrazioneDatiVenditore") }
                    )
                    {
                        Icon(
                            painter = upgradeIcon,
                            contentDescription = "Diventa Venditore",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Diventa Venditore", fontSize = 25.sp)
                    }


                    // Bottone per chiudere il drawer


                }

                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { navController.navigate("SchermataAutenticazione") }

                )
                {
                    Icon(
                        painter = logoutIcon,
                        contentDescription = "Logout",
                        modifier = Modifier.size(30.dp),
                        tint = Color(0xFF9B0404)

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Logout", fontSize = 25.sp, color = Color(0xFF9B0404))
                }
            }
        },
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (bottomBar, background, topBar, immagineProfilo, nomeUtente, usernameUtente, shortBioLabel, shortBioUtente, sitoWebUtente, social, indirizzoUtente) = createRefs()
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
                            painter = if (previousDestination == "SchermataPaginaAsta") backIcon else menuIcon,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    if (previousDestination == "SchermataPaginaAsta") {
                                        navController.popBackStack()
                                    } else {

                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }
                                    // Azione da eseguire quando si clicca sull'icona di navigazione
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
                        if (previousDestination == "SchermataPaginaAsta") {
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_brush_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate("SchermataModificaProfilo")

                                    }
                                    .size(40.dp)

                            )
                        }
                    }
                )
                val screenWidth = LocalDensity.current.run {
                    LocalConfiguration.current.screenWidthDp.dp
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
                    // Immagine all'interno della Box circolare
                    //sistemare l'immagine in modo che copra l'intero cerchio
                    AsyncImage(
                        model = datiProfiloUtente?.urlFotoProfilo,
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                        error = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Immagine del profilo dell'utente",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
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
                    fontWeight = FontWeight.Bold, // Imposta il grassetto
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

            //SE E NULL CREA UN TESTO VUOTO ALTRIMENTO LO METTO, non riusciva giustamente a metteren null come testo e impazziva
                Text(
                    text = ((if(datiProfiloUtente?.descrizione == null) "" else datiProfiloUtente!!.descrizione).toString()),
                    modifier = Modifier
                        .constrainAs(shortBioUtente)
                        {
                            top.linkTo(shortBioLabel.bottom)
                        }
                        .padding(8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    color = Color.Black,// Imposta il grassetto
                    maxLines = 6, // Imposta il numero massimo di righe
                    overflow = TextOverflow.Ellipsis// Aggiunge tre puntini alla fine del testo se viene troncato
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(sitoWebUtente) {
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
                    val context = LocalContext.current

                    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                        // Handle the result if needed
                    }

                    val text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 20.sp
                            )
                        ) {
                            append(datiProfiloUtente?.sitoWeb)
                            addStringAnnotation("URL", "https://www.example.com", 0, length)
                        }
                    }
                    Spacer(modifier = Modifier.width(7.dp))

                    ClickableText(
                        text = text,
                        modifier = Modifier.padding(8.dp),
                        onClick = { offset ->
                            text.getStringAnnotations("URL", offset, offset)
                                .firstOrNull()?.let { annotation ->
                                    annotation.item
                                    val intent = Intent(Intent.ACTION_VIEW, null)
                                    ContextCompat.startActivity(context, intent, null)
                                }
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }



                @Composable
                fun IconWithText(iconId: Int, text: String, route: String, color: Color) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Image(
                            painter = painterResource(id = iconId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { navController.navigate(route) }

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


                ) {
                    Spacer(
                        modifier = Modifier
                            .width(20.dp)

                    )
                    IconWithText(
                        iconId = R.drawable.instagramicon,
                        text = "Instagram",
                        "", Color.Black

                    )
                    Spacer(
                        modifier = Modifier
                            .width(0.dp)
                            .weight(1f)
                    )
                    IconWithText(
                        iconId = R.drawable.facebookicon,
                        text = "Facebook",
                        "", Color.Black
                    )
                    Spacer(
                        modifier = Modifier
                            .width(0.dp)
                            .weight(1f)
                    )
                    IconWithText(
                        iconId = R.drawable.twittericon,
                        text = "Twitter",
                        "", Color.Black
                    )
                    Spacer(
                        modifier = Modifier
                            .width(0.dp)
                            .weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(indirizzoUtente)
                        {
                            top.linkTo(social.bottom)
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
                    datiProfiloUtente?.indirizzo?.let {
                        Text(
                            text = it,
                            modifier = Modifier.weight(1f),
                            fontSize = 15.sp,
                            color = Color.Black,

                            )
                    }


                }
                if (previousDestination == "SchermataPaginaAsta") {
                } else {
                    NavigationBar(
                        tonalElevation = 30.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)// Add a black border
                            .constrainAs(bottomBar)
                            {
                                bottom.linkTo(parent.bottom)
                            },
                        content = {
                            NavigationBarItem(
                                label = { Text(text = "Home") },
                                selected = false,
                                onClick = {
                                    navController.navigate("SchermataHome") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = false
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.homeicon),
                                        contentDescription = "Home"
                                    )
                                }
                            )
                            NavigationBarItem(
                                label = { Text(text = "Gestisci") },
                                selected = false,
                                onClick = {
                                    navController.navigate("SchermataGestioneAste") {
                                        popUpTo("SchermataGestioneAste") {
                                            inclusive = false
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.gestisci_icon),
                                        contentDescription = "Gestione Aste"
                                    )
                                }
                            )
                            NavigationBarItem(
                                label = { Text(text = "Crea Asta") },
                                selected = false,
                                onClick = {
                                    navController.navigate("SchermataCreazioneAsta") {
                                        popUpTo("SchermataCreazioneAsta") {
                                            inclusive = false
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.crea_asta_icon),
                                        contentDescription = "Crea Asta"
                                    )
                                }
                            )
                            NavigationBarItem(
                                label = { Text(text = "Profilo") },
                                selected = true,
                                onClick = {
                                    navController.navigate("SchermataProfiloUtente") {
                                        popUpTo("SchermataProfiloUtente") {
                                            inclusive = false
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.usericon),
                                        contentDescription = "ProfiloUtente"
                                    )
                                }
                            )

                        }
                    )

                }

            }

        }
    )
}

/*@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataUtente(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    val upgradeIcon = painterResource(id = R.drawable.baseline_upgrade_24)
    val accountIcon = painterResource(id = R.drawable.baseline_account_circle_24)
    val menuIcon = painterResource(id = R.drawable.baseline_menu_24)
    val logoutIcon = painterResource(id = R.drawable.baseline_logout_24)
    val paymentIcon = painterResource(id = R.drawable.baseline_attach_money_24)
    val profilePicture = painterResource(id = R.drawable.ic_launcher_foreground)
    val homeIcon = R.drawable.baseline_home_24
    val gestisciAste = R.drawable.line_chart_svgrepo_com
    val creaAsta = R.drawable.hand_money_cash_hold_svgrepo_com
    val account = R.drawable.baseline_manage_accounts_24
    val colorGreen = 0xFF0EA639

    val utente = Utente(
        "mrossi",
        "Mario",
        "Rossi",
        LocalDate.of(1990, 5, 15),
        "12345678999",
        File("path/file"),
        File("path/file"),
        null,
        mutableSetOf(),
        mutableSetOf(),
        mutableSetOf(),
        mutableSetOf(),
        RuoloUtente.COMPRATORE,
        null,/*"Sono Mario Rossi, un appassionato venditore di aste con una vasta esperienza nel settore." +
                " Offro una selezione di oggetti unici e preziosi, curando ogni dettaglio delle mie aste per garantire esperienze indimenticabili ai miei acquirenti." +
                "Conosco il valore degli oggetti che metto all'asta e mi impegno a offrire un servizio clienti impeccabile." + "Sono qui per fornire autenticità, qualità e emozioni nel mondo delle aste.",
        */
        "www.marione.com",
        "Via Roma, 123, 00100, Roma, Italia",
        "instagram",
        "facebook",
        "twitter"
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (bottomBar, background, topBar, immagineProfilo, nomeUtente, usernameUtente, shortBioLabel, shortBioUtente, sitoWebUtente, social, indirizzoUtente) = createRefs()
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
                    painter = menuIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                            // Azione da eseguire quando si clicca sull'icona di navigazione
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
                Icon(
                    painter = painterResource(id = R.drawable.baseline_brush_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("SchermataModificaProfilo")

                        }
                        .size(40.dp)

                )
            }
        )
        val screenWidth = LocalDensity.current.run {
            LocalConfiguration.current.screenWidthDp.dp
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
            // Immagine all'interno della Box circolare
            Image(
                painter = profilePicture, // Rimpiazza con la tua immagine
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )

        }
        Text(
            text = "MARIO ROSSI",
            modifier = Modifier
                .constrainAs(nomeUtente)
                {
                    top.linkTo(immagineProfilo.bottom)
                }

                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold, // Imposta il grassetto
        )
        Text(
            text = "@" + utente.username,
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
        utente.descrizione?.let {
            Text(
                text = it,
                modifier = Modifier
                    .constrainAs(shortBioUtente)
                    {
                        top.linkTo(shortBioLabel.bottom)
                    }
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                color = Color.Black,// Imposta il grassetto
                maxLines = 6, // Imposta il numero massimo di righe
                overflow = TextOverflow.Ellipsis// Aggiunge tre puntini alla fine del testo se viene troncato
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(sitoWebUtente) {
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
            val context = LocalContext.current

            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                // Handle the result if needed
            }

            val text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp
                    )
                ) {
                    append(utente.sitoWeb)
                    addStringAnnotation("URL", "https://www.example.com", 0, length)
                }
            }
            Spacer(modifier = Modifier.width(7.dp))

            ClickableText(
                text = text,
                modifier = Modifier.padding(8.dp),
                onClick = { offset ->
                    text.getStringAnnotations("URL", offset, offset)
                        .firstOrNull()?.let { annotation ->
                            annotation.item
                            val intent = Intent(Intent.ACTION_VIEW, null)
                            ContextCompat.startActivity(context, intent, null)
                        }
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }



        @Composable
        fun IconWithText(iconId: Int, text: String, route: String, color: Color) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(2.dp)
            ) {
                Image(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { navController.navigate(route) }

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


        ) {
            Spacer(
                modifier = Modifier
                    .width(20.dp)

            )
            IconWithText(
                iconId = R.drawable.instagramicon,
                text = "Instagram",
                "", Color.Black

            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
            IconWithText(
                iconId = R.drawable.facebookicon,
                text = "Facebook",
                "", Color.Black
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
            IconWithText(
                iconId = R.drawable.twittericon,
                text = "Twitter",
                "", Color.Black
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(indirizzoUtente)
                {
                    top.linkTo(social.bottom)
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
            utente.indirizzo?.let {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    fontSize = 15.sp,
                    color = Color.Black,

                    )
            }


        }
        NavigationBar(
            tonalElevation = 30.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)// Add a black border
                .constrainAs(bottomBar)
                {
                    bottom.linkTo(parent.bottom)
                },
            content = {
                NavigationBarItem(
                    label = { Text(text = "Home") },
                    selected = false,
                    onClick = {
                        navController.navigate("SchermataHome") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.homeicon),
                            contentDescription = "Home"
                        )
                    }
                )
                NavigationBarItem(
                    label = { Text(text = "Gestisci") },
                    selected = false,
                    onClick = {
                        navController.navigate("SchermataGestioneAste") {
                            popUpTo("SchermataGestioneAste") {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.gestisci_icon),
                            contentDescription = "Gestione Aste"
                        )
                    }
                )
                NavigationBarItem(
                    label = { Text(text = "Crea Asta") },
                    selected = false,
                    onClick = {
                        navController.navigate("SchermataCreazioneAsta") {
                            popUpTo("SchermataCreazioneAsta") {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.crea_asta_icon),
                            contentDescription = "Crea Asta"
                        )
                    }
                )
                NavigationBarItem(
                    label = { Text(text = "Profilo") },
                    selected = true,
                    onClick = {
                        navController.navigate("SchermataProfiloUtente") {
                            popUpTo("SchermataProfiloUtente") {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.usericon),
                            contentDescription = "ProfiloUtente"
                        )
                    }
                )

            }
        )
    }
} */


@Preview(showBackground = true)
@Composable
fun PreviewSchermataProfiloUtente() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataProfiloUtente(navController)
    }
}