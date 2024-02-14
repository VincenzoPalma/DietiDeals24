package com.example.dietideals_app.view


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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.dietideals_app.model.Offerta
import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.enum.StatoOfferta
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class PaginaOfferte : ComponentActivity() {
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
fun SchermataOfferte(navController: NavController) {

    val homeIcon = R.drawable.baseline_home_24
    val gestisciAste = R.drawable.line_chart_svgrepo_com
    val creaAsta = R.drawable.hand_money_cash_hold_svgrepo_com
    val account = R.drawable.baseline_manage_accounts_24

    val defaultImage = painterResource(id = R.drawable.defaultimage)
    val colorGreen = 0xFF0EA639
    val colorRed = 0xFF9B0404
    val context = LocalContext.current
    val currentPage = remember { mutableStateOf(0) }
    val openDialog = remember { mutableStateOf(false) }
    var offerta by remember { mutableStateOf("") }








    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (background, topBar, bottomBar,listaOfferte) = createRefs()

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
        )
        TopAppBar(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, title = {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "OFFERTE",
                        fontWeight = FontWeight.Bold, // Imposta il grassetto
                        fontSize = 40.sp
                    ) // Imposta la dimensione del testo)

                }

        },
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { navController.navigate("SchermataPaginaAsta") }
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


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(listaOfferte) {
                        top.linkTo(topBar.bottom, margin = 90.dp)
                        bottom.linkTo(bottomBar.top)
                    }
                    .padding(16.dp)
            ) {
                items(7) { index ->
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)){
                            Column {
                                Image(painter = painterResource(id =R.drawable.user_image ), contentDescription = "immagine Profilo",Modifier.size(80.dp) )
                            }
                            Column{
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                    Text(text = "Mario Rossi",fontWeight = FontWeight.Bold)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,modifier = Modifier.fillMaxWidth()) {
                                    Text(text = "OFFERTA : ")
                                    Text(text = "€200,00",color = Color(colorGreen))
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,modifier = Modifier.fillMaxWidth()) {
                                    TextButton(onClick = { }) {
                                        Text(text = "RIFIUTA", color = Color(colorRed))

                                    }
                                    Spacer(Modifier.width(50.dp))
                                    TextButton(onClick = {
                                        }) {
                                        Text(text = "ACCETTA", color = Color(colorGreen))

                                    }
                                }


                            }

                        }
                        HorizontalDivider()

                    }
                }
            }

        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomBar) {
                    bottom.linkTo(parent.bottom)
                }

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
                iconId = homeIcon,
                text = "Home",
                "SchermataHome",
                Color(colorGreen)
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )

            IconWithText(
                iconId = gestisciAste,
                text = "Gestisci Aste",
                "",
                Color(colorGreen)
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )

            IconWithText(
                iconId = creaAsta,
                text = "Crea Asta",
                "",
                Color(colorGreen)
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )

            IconWithText(
                iconId = account,
                text = "Profilo",
                "SchermataProfiloUtente",
                Color(colorGreen)
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewSchermataOfferte() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataOfferte(navController)
    }
}