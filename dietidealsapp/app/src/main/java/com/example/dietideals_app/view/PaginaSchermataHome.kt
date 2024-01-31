package com.example.dietideals_app.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import kotlin.random.Random

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
                    NavHost(navController = navController, startDestination = "SchermataAutenticazione") {
                        composable("SchermataAutenticazione") { SchermataAutenticazione(navController = navController) }
                        composable("SchermataRegistrazione") { SchermataRegistrazione(navController = navController) }
                        composable("SchermataImmagineProfilo") { SchermataImmagineProfilo(navController = navController) }
                        composable("SchermataDiventaVenditore") { SchermataDiventaVenditore(navController = navController) }
                        composable("SchermataRegistrazioneSuccesso") { SchermataRegistrazioneSuccesso(navController = navController) }
                        composable("SchermataRegistrazioneDatiVenditore"){SchermataRegistrazioneDatiVenditore(navController = navController)}
                        composable("SchermataHome"){SchermataHome(navController = navController)}
                    }
                }
            }
        }
    }
}

@Composable
fun SchermataHome(navController: NavController) {
    val logoApp = painterResource(id = R.drawable.iconaapp)

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ){

        val (header,subTitle,buttons,boxes) = createRefs()
        Box(
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .height(80.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ){
            Row (verticalAlignment = Alignment.CenterVertically
            ){

                Image(
                    painter = logoApp,
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "DIETIDEALS 24",
                    color = Color.White,
                    fontFamily = titleCustomFont,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "searchButton",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.width(0.dp))
                Icon(painter = painterResource(id = R.drawable.baseline_notifications_24),
                    contentDescription = "notificationButton",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp))




            }

        }
        Row(
            modifier = Modifier
                .constrainAs(subTitle) {
                    top.linkTo(header.bottom, margin = 5.dp)
                }
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Aste in Corso",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                modifier = Modifier.weight(1f) // Utilizza weight per far sì che la Text occupi lo spazio rimanente
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = "filterButton",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(30.dp)// Aggiunge uno spazio tra la Text e l'Icon
            )
        }
        LazyColumn(
            modifier = Modifier
                .constrainAs(boxes) {
                    top.linkTo(subTitle.bottom, margin = 200.dp)
                    bottom.linkTo(buttons.top, margin = 50.dp)
                }
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(6) { index ->
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
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Immagine
                                Image(
                                    painter = painterResource(id = R.drawable.defaultimage),
                                    contentDescription = "Image",
                                    modifier = Modifier
                                        .size(100.dp)
                                )

                                // Titolo
                                Text(
                                    text = "Elemento $index-A",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(4.dp)
                                )

                                // Bottone
                                Button(
                                    onClick = { /* Azione del bottone */ },
                                    modifier = Modifier.padding(4.dp),
                                    shape = RoundedCornerShape(0, 0)
                                ) {
                                    Text(String.format("€%.2f", 80.0 + Random.nextDouble() * 9.0), fontSize = 10.sp)
                                }
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
                                // Immagine
                                Image(
                                    painter = painterResource(id = R.drawable.defaultimage),
                                    contentDescription = "Image",
                                    modifier = Modifier
                                        .size(100.dp)
                                )

                                // Titolo
                                Text(
                                    text = "Elemento $index-B",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(4.dp)
                                )

                                // Bottone
                                Button(
                                    onClick = { /* Azione del bottone */ },
                                    modifier = Modifier,
                                    shape = RoundedCornerShape(0, 0)
                                ) {
                                    Text(
                                        text = String.format("€%.2f", 80.0 + Random.nextDouble() * 9.0),
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }



        Box(
            modifier = Modifier
                .constrainAs(buttons) {
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White)
                .border(1.dp, Color.Black)
                .padding(16.dp)
        ){
            Row (verticalAlignment = Alignment.CenterVertically,
            ){

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