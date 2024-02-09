package com.example.dietideals_app.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

class Diventavenditore : ComponentActivity() {
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
                        composable("SchermataImmagineProfilo") {
                            SchermataImmagineProfilo(
                                navController = navController
                            )
                        }
                        composable("SchermataDiventaVenditore") {
                            SchermataDiventaVenditore(
                                navController = navController
                            )
                        }
                        composable("SchermataRegistrazioneSuccesso") {
                            SchermataRegistrazioneSuccesso(
                                navController = navController
                            )
                        }
                        composable("SchermataRegistrazioneDatiVenditore") {
                            SchermataRegistrazioneDatiVenditore(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SchermataDiventaVenditore(navController: NavController) {
    // Ottieni l'immagine di sfondo da resources
    val background = painterResource(id = R.drawable.sfondo3)

    // Crea un presenter per la registrazione
    val presenter = AutenticazionePresenter()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backgroundImage, title, sellerText, contentBox, notNowButton) = createRefs()


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
        Row(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Icona indietro
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        // Azione quando l'icona indietro viene clicata
                        presenter.effettuaRegistrazione()
                        navController.navigate("SchermataImmagineProfilo")
                    },
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(5.dp))  // Aggiunge uno spazio tra l'icona e il testo

            // Testo titolo
            Text(
                text = "REGISTRAZIONE",
                color = Color.White,
                fontFamily = titleCustomFont,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Testo "Sei un venditore?"
        Text(
            text = "Sei un venditore?",
            color = Color.White,
            fontFamily = titleCustomFont,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(sellerText) {
                top.linkTo(title.top, margin = 40.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Contenuto centrato
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .constrainAs(contentBox) {
                    top.linkTo(sellerText.top, margin = 125.dp)
                    bottom.linkTo(notNowButton.top, margin = 100.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(450.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Testi e contenuti della Box
            Text(
                text = "PASSA ALL'ACCOUNT VENDITORE!\n\n" +
                        "I VANTAGGI:\n\n" +
                        "1) CREARE ASTE ALL'INGLESE\n" +
                        "2) CREARE ASTE SILENZIOSE\n" +
                        "3) PARTECIPARE ALLE ASTE INVERSE",
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
            )


            Spacer(modifier = Modifier.height(30.dp))

            // Bottone "DIVENTA UN VENDITORE"
            ElevatedButton(
                onClick = {
                    presenter.effettuaRegistrazione()
                    navController.navigate("SchermataRegistrazioneDatiVenditore")
                },

                modifier = Modifier
                    .padding(bottom = 16.dp) // Aggiungi un margine inferiore
                    .fillMaxWidth() // Usa tutta la larghezza disponibile
            ) {
                Text(
                    text = "DIVENTA UN VENDITORE",
                    fontSize = 20.sp, // Imposta la dimensione del font desiderata
                    modifier = Modifier.padding(8.dp) // Aggiungi spaziatura interna al testo
                )

            }
            TextButton(
                onClick = {
                    presenter.effettuaRegistrazione()
                    navController.navigate("SchermataRegistrazioneSuccesso")
                },
                // Aggiungi un margine inferiore


            ) {
                Text(
                    text = "NON ORA",
                    fontSize = 20.sp, // Imposta la dimensione del font desiderata
                    modifier = Modifier.padding(8.dp),
                    color = Color.Red// Aggiungi spaziatura interna al testo
                )
            }

            // Testo sotto il bottone "NON ORA"
            Text(
                text = "POTRAI PASSARE AD UN ACCOUNT VENDITORE IN UN SECONDO MOMENTO",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 9.sp,
            )
        }

        // Bottone "NON ORA"

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSchermataDiventaVenditore() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataDiventaVenditore(navController)
    }
}
