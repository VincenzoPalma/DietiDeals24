package com.example.dietideals_app.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.Notifica
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.viewmodel.SchermataHomeViewModel
import com.example.dietideals_app.viewmodel.listener.AsteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PaginaSchermataHome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SchermataHome(navController)
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataHome(navController: NavController) {

    val viewModel = SchermataHomeViewModel()
    val auth: FirebaseAuth = Firebase.auth
    val logoApp = painterResource(id = R.drawable.iconaapp)
    var isSearchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isFilterVisible by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val auctionType = listOf("Inglese", "Silenziosa", "Inversa")
    val searchFocusRequester =
        remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    var asteVisualizzate = emptyList<Asta>()

    val listener = remember {
        object : AsteListener {
            override fun onAsteLoaded(aste: List<Asta>) {
                asteVisualizzate = aste
            }

            override fun onError() {
                // Gestisci l'errore
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.mostraAste(0, null, null, null, listener)
        println("home")
    }

    fun generateNotifications(): List<Notifica> {
        // Implementa questa funzione secondo le tue esigenze
        // Restituisce una lista di notifiche
        return listOf(
            //Notifica("Notifica 1", LocalDateTime.now()),
            //Notifica("Notifica 2", LocalDateTime.now()),
            //Notifica("Notifica 3", LocalDateTime.now()),
            //Notifica("Notifica 4", LocalDateTime.now()),
            //Notifica("Notifica 5", LocalDateTime.now()),
            //Notifica("Notifica 6", LocalDateTime.now())
        )
    }

    var notifications by remember { mutableStateOf(generateNotifications()) }

    var checkInglese by remember { mutableStateOf(false) }
    var checkInversa by remember { mutableStateOf(false) }
    var checkSilenziosa by remember { mutableStateOf(false) }

    var openCategoryDialog by remember { mutableStateOf(false) }
    var categoriaSelezionata by remember {
        mutableStateOf("Seleziona Categoria")
    }

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

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.7f),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_close_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable {
                                            scope.launch {
                                                drawerState.apply {
                                                    close()
                                                }
                                            }
                                        },
                                    tint = Color(0xFF9B0404)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Notifiche",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.trash_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable { notifications = emptyList() },
                                    tint = Color.Black
                                )

                            }

                            if (notifications.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(16.dp)
                                ) {
                                    items(notifications.size) { notification ->

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 16.dp)
                                        ) {
                                            // Riquadro per ogni elemento della lista
                                            OutlinedCard(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(100.dp)
                                                    .padding(2.dp),
                                                border = BorderStroke(1.dp, Color.Black),

                                                ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {

                                                    // Titolo
                                                    Text(
                                                        text = "Hai Vinto l'asta $notification\n Scarpe Adidas per € 40",
                                                        color = Color.Black,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 8.sp,
                                                        textAlign = TextAlign.Left,
                                                        modifier = Modifier
                                                            .padding(4.dp)
                                                    )
                                                    VerticalDivider()
                                                    Column {
                                                        // Immagine
                                                        Image(
                                                            painter = painterResource(id = R.drawable.defaultimage),
                                                            contentDescription = "Image",
                                                            modifier = Modifier
                                                                .size(100.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Nessuna Notifica",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic,
                                        color = Color.Gray,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                        }

                    }
                }
            }

        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val (background, topBar, filterBar, bottomBar, listaAste) = createRefs()
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


                    TopAppBar(modifier = Modifier
                        .constrainAs(topBar) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    painter = logoApp,
                                    contentDescription = "logo",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp)
                                )
                                if (!isSearchVisible) {

                                    Text(
                                        text = "DIETI DEALS 24",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth(),

                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp
                                    )


                                } else {

                                    TextField(
                                        value = query,
                                        onValueChange = { nuovaQuery -> query = nuovaQuery },
                                        placeholder = {
                                            Text(
                                                "Cerca Asta",

                                                modifier = Modifier
                                                    .focusRequester(searchFocusRequester)
                                                    .fillMaxWidth()
                                            )
                                        }, keyboardOptions = KeyboardOptions.Default.copy(
                                            imeAction = ImeAction.Done
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onDone = { /*query*/ }
                                        ),
                                        modifier = Modifier
                                            .height(60.dp)
                                            .fillMaxWidth(),
                                        singleLine = true
                                    )

                                }
                            }
                        },
                        colors = TopAppBarColors(
                            containerColor = (MaterialTheme.colorScheme.primary),
                            navigationIconContentColor = Color.White,
                            titleContentColor = Color.White,
                            actionIconContentColor = Color.White,
                            scrolledContainerColor = Color.White
                        ),

                        actions = {
                            if (!isSearchVisible) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_search_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clickable {
                                            isSearchVisible = !isSearchVisible

                                        }
                                        .size(40.dp)


                                )
                            }

                            BadgedBox(badge = {
                                if (notifications.isNotEmpty()) {
                                    Badge {

                                        Text(notifications.size.toString())
                                    }
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_notifications_24),
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
                            }
                        },
                        navigationIcon = {

                        }
                    )
                    Row(
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp)
                            .constrainAs(filterBar)
                            {
                                top.linkTo(topBar.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)

                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Aste in Corso",
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            modifier = Modifier.weight(1f),
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_list_24),
                            contentDescription = "filterButton",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { isFilterVisible = true }
                        )



                        if (isFilterVisible) {
                            Dialog(onDismissRequest = { isFilterVisible = false }) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    border = BorderStroke(1.dp, Color.Black)

                                ) {
                                    TopAppBar(modifier = Modifier.height(40.dp), title = {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Text(
                                                text = "FILTRI",
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.fillMaxWidth(),
                                                fontWeight = FontWeight.Bold
                                            )

                                        }
                                    }, actions = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_close_24),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .clickable {
                                                    isFilterVisible = false

                                                }
                                                .size(30.dp)
                                                .padding(top = 6.dp, end = 6.dp)

                                        )
                                    }, colors = TopAppBarColors(
                                        containerColor = (MaterialTheme.colorScheme.primary),
                                        navigationIconContentColor = Color.White,
                                        titleContentColor = Color.White,
                                        actionIconContentColor = Color.White,
                                        scrolledContainerColor = Color.White
                                    )
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "TIPI DI ASTE",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth(),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {

                                            Text(text = "All'inglese", fontSize = 15.sp)
                                            Checkbox(
                                                checked = checkInglese,
                                                onCheckedChange = { checkInglese = it },
                                            )
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        )
                                        {
                                            Text(text = "Inversa", fontSize = 15.sp)
                                            Checkbox(
                                                checked = checkInversa,
                                                onCheckedChange = { checkInversa = it },
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Text(text = "Silenziosa", fontSize = 15.sp)

                                            Checkbox(
                                                checked = checkSilenziosa,
                                                onCheckedChange = { checkSilenziosa = it },
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Categoria  ",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        ElevatedButton(onClick = {
                                            openCategoryDialog = true
                                        }) {
                                            Text(text = categoriaSelezionata)
                                        }

                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        ElevatedButton(
                                            onClick = { openCategoryDialog = true },
                                            colors = ButtonColors(
                                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                                contentColor = Color(0xFFBA1A1A),
                                                disabledContentColor = Color.Gray,
                                                disabledContainerColor = Color.Gray
                                            )
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_close_24),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .clickable {
                                                        checkSilenziosa = false
                                                        checkInglese = false
                                                        checkInversa = false
                                                        categoriaSelezionata =
                                                            "Seleziona Categoria"
                                                    }
                                                    .size(20.dp)
                                            )
                                            Text(text = "RIMUOVI FILTRI")
                                        }

                                    }


                                }
                            }
                            if (openCategoryDialog) {
                                Dialog(onDismissRequest = { openCategoryDialog = false }) {
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
                                                                        categorie[index]
                                                                    openCategoryDialog = false
                                                                },

                                                            ) {
                                                            Text(
                                                                text = categorie[index],
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


                        }

                    }
                    var timeLeft by rememberSaveable { mutableStateOf(0L) }
                    var timerRunning by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {

                        val inputText = "00:04:30" // Inserisci qui il tempo iniziale desiderato
                        val time = inputText.split(":")
                        if (time.size == 3) {
                            val hours = time[0].toLongOrNull() ?: 0
                            val minutes = time[1].toLongOrNull() ?: 0
                            val seconds = time[2].toLongOrNull() ?: 0
                            val totalTime = TimeUnit.HOURS.toMillis(hours) +
                                    TimeUnit.MINUTES.toMillis(minutes) +
                                    TimeUnit.SECONDS.toMillis(seconds)
                            if (totalTime > 0) {
                                timeLeft = totalTime
                                timerRunning = true
                                while (timeLeft > 0 && timerRunning) {
                                    delay(1000)
                                    timeLeft -= 1000
                                }
                                timerRunning = false
                            }
                        }
                    }

                    fun formatTime(timeMillis: Long): String {
                        val hours = TimeUnit.MILLISECONDS.toHours(timeMillis)
                        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMillis) % 60
                        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 60
                        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .constrainAs(listaAste) {
                                top.linkTo(filterBar.bottom)
                            }
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {


                        items(12) { index ->
                            var open by remember {
                                mutableStateOf(false)
                            }
                            var type = auctionType.random()
                            var category = categorie.random()

                            ElevatedCard(
                                modifier = Modifier
                                    .height(if (!open) 230.dp else 290.dp)
                                    .padding(8.dp)
                                    .clickable { navController.navigate("SchermataPaginaAsta") },
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 6.dp
                                ),
                                content = {
                                    Column(
                                        modifier = Modifier.padding(bottom = 8.dp),
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.defaultimage),
                                            contentDescription = "Image",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(110.dp)
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        bottomStart = 16.dp,
                                                        bottomEnd = 16.dp
                                                    )
                                                ),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))

                                        Text(
                                            text = "Scarpe Nike",
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.category_icon),
                                                contentDescription = "category",
                                                Modifier.size(15.dp)
                                            )
                                            Text(
                                                text = category,
                                                fontSize = 12.sp,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.auction_type_icon),
                                                contentDescription = "auctionType",
                                                Modifier.size(15.dp)
                                            )
                                            Text(
                                                text = type,
                                                fontSize = 12.sp,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        if (open) {
                                            Row(
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.baseline_attach_money_24),
                                                    contentDescription = "auctionType",
                                                    Modifier.size(15.dp)
                                                )
                                                Text(
                                                    text = if (type == "Inglese" || type == "Inversa") {
                                                        "Prezzo Attuale " + "€300,00"
                                                    } else {
                                                        "Prezzo Base" + "€40,00"
                                                    },
                                                    fontSize = 12.sp,
                                                    overflow = TextOverflow.Ellipsis
                                                )

                                            }
                                            if (type == "Inglese") {
                                                Row(
                                                    horizontalArrangement = Arrangement.Start,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 8.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.growth),
                                                        contentDescription = "SogliaRialzo",
                                                        Modifier.size(15.dp)
                                                    )
                                                    Text(
                                                        text = "€5,00",
                                                        fontSize = 12.sp,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
                                                        contentDescription = "SogliaRialzo",
                                                        Modifier.size(15.dp),
                                                        tint = MaterialTheme.colorScheme.error
                                                    )
                                                    Text(
                                                        formatTime(timeLeft),
                                                        fontSize = 12.sp,
                                                        modifier = Modifier.padding(start = 4.dp),
                                                        color = MaterialTheme.colorScheme.error
                                                    )
                                                }
                                            }
                                            Row(
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                when (type) {
                                                    "Inversa" -> {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                                                            contentDescription = "TimeLeft",
                                                            Modifier.size(15.dp),
                                                            tint = MaterialTheme.colorScheme.error
                                                        )
                                                        Text(
                                                            "10/03/2024", //ToDo da sostituire con la data di scadenza dell'asta
                                                            fontSize = 12.sp,
                                                            modifier = Modifier.padding(start = 4.dp),
                                                            color = MaterialTheme.colorScheme.error
                                                        )
                                                    }

                                                    "Silenziosa" -> {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                                                            contentDescription = "TimeLeft",
                                                            Modifier.size(15.dp),
                                                            tint = MaterialTheme.colorScheme.error
                                                        )
                                                        Text(
                                                            "10/03/2024 22:00", //ToDo da sostituire con la data di scadenza dell'asta
                                                            fontSize = 12.sp,
                                                            modifier = Modifier.padding(start = 4.dp),
                                                            color = MaterialTheme.colorScheme.error
                                                        )

                                                    }
                                                }

                                            }
                                        }

                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.Bottom
                                        ) {
                                            IconButton(onClick = { open = !open }
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = if (!open) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_up_24),
                                                    contentDescription = "ViewMore"
                                                )
                                            }


                                        }


                                    }
                                }
                            )
                        }

                        item {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
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
                                selected = true,
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
                                selected = false,
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
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSchermataHome() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataHome(navController)
    }
}