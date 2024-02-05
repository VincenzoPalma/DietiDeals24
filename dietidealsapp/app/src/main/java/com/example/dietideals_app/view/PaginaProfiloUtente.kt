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
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import kotlinx.coroutines.launch

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
                    }
                }
            }
        }

    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataProfiloUtente(navController: NavController) {

    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.7f)) {
                    Column(
                        modifier = Modifier
                            .weight(1f)

                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ){
                        Row(
                                verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { navController.navigate("") }
                            )
                            {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                                    contentDescription ="Profilo Utente",
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Dati Utente", fontSize = 25.sp)
                            }



                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { navController.navigate("") }
                            )
                            {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_attach_money_24),
                                    contentDescription ="Pagamenti",
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Pagamenti", fontSize = 25.sp)
                            }



                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { navController.navigate("SchermataAutenticazione") }
                            )
                            {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_upgrade_24),
                                    contentDescription ="Diventa Venditore",
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
                            .clickable { navController.navigate("") }

                    )
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription ="Logout",
                            modifier = Modifier.size(30.dp),
                            tint = Color(0xFF9B0404)

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Logout", fontSize = 25.sp,color = Color(0xFF9B0404))
                    }
                }
            },
            content = {
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
                            }.background(Color.White)
                    ) {
                        TopAppBar(modifier = Modifier.align(Alignment.TopCenter),title = {
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
                                    painter = painterResource(id = R.drawable.baseline_menu_24),
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
                            modifier = Modifier.offset(x=(screenWidth/2)-40.dp,y = 70.dp)
                                .size(80.dp)
                                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                        ) {
                            // Immagine all'interno della Box circolare
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Rimpiazza con la tua immagine
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )

                        }
                        Text(
                            text = "MARIO ROSSI",
                            modifier = Modifier
                                .offset(y = 150.dp)

                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold, // Imposta il grassetto
                        )
                        Text(
                            text = "@mariorossi",
                            modifier = Modifier
                                .offset(y = 180.dp)

                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = Color.Gray// Imposta il grassetto
                        )
                        Text(
                            text = "Short Bio:",
                            modifier = Modifier
                                .offset(y = 200.dp)

                                .padding(8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Ciao! Sono Mario Rossi, venditore di aste con esperienza nel mondo delle opere d'arte. " +
                                    "Offro oggetti unici e rari con un servizio di alta qualità. S" +
                                    "copri tesori per la tua collezione personale! Grazie per il tuo interesse!",
                            modifier = Modifier
                                .offset(y = 235.dp)

                                .padding(8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            color = Color.Black// Imposta il grassetto
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 400.dp)


                            .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Sito Web:",
                                textAlign = TextAlign.Left,
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            val context = LocalContext.current

                            val openUrlLauncher =
                                rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                                    // Handle the result if needed
                                }

                            val text = buildAnnotatedString {
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

                            ClickableText(
                                text = text,
                                modifier = Modifier.padding(8.dp),
                                onClick = { offset ->
                                    text.getStringAnnotations("URL", offset, offset)
                                        .firstOrNull()?.let { annotation ->
                                            val url = annotation.item
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
                                Icon(
                                    painter = painterResource(id = iconId),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable { navController.navigate(route) },
                                    tint = color

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
                                .offset(y = 480.dp)


                        ) {
                            Spacer(
                                modifier = Modifier
                                    .width(20.dp)

                            )
                            IconWithText(
                                iconId = R.drawable.instagramicon,
                                text = "Instagram",
                                "",
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
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 550.dp)

                            .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Indirizzo:",
                                textAlign = TextAlign.Left,
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Via San Michele 25, Napoli(NA),Italia",
                                modifier = Modifier.weight(1f),
                                fontSize = 15.sp,
                                color = Color.Black,

                                )


                        }
                        BottomAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(color = Color.White) // Set the background color to white
                                .border(1.dp, color = Color.Black)
                                .height(60.dp)// Add a black border
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(1f)
                            )
                            IconWithText(
                                iconId = R.drawable.baseline_home_24,
                                text = "Home",
                                "SchermataHome",
                                Color(0xFF0EA639)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(1f)
                            )
                            IconWithText(
                                iconId = R.drawable.line_chart_svgrepo_com,
                                text = "Gestisci Aste",
                                "",
                                Color(0xFF0EA639)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(1f)
                            )
                            IconWithText(
                                iconId = R.drawable.hand_money_cash_hold_svgrepo_com,
                                text = "Crea Asta",
                                "",
                                Color(0xFF0EA639)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(1f)
                            )
                            IconWithText(
                                iconId = R.drawable.baseline_manage_accounts_24,
                                text = "Profilo",
                                "SchermataProfiloUtente",
                                Color(0xFF0EA639)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(1f)
                            )


                        }


                    }


                }
            })
            }







@Preview(showBackground = true)
@Composable
fun PreviewSchermataProfiloUtente() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataProfiloUtente(navController)
    }
}