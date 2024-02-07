package com.example.dietideals_app.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

class RegistrazioneSuccesso : ComponentActivity() {
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
fun SchermataRegistrazioneSuccesso(navController: NavController) {
    // Ottieni l'immagine di sfondo da resources
    val background = painterResource(id = R.drawable.sfondo3)


    // Crea un presenter per la registrazione
    val presenter = AutenticazionePresenter()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backgroundImage, title, textColumn, loginButton) = createRefs()

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
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "REGISTRAZIONE",
                color = Color.White,
                fontFamily = titleCustomFont,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Contenuto centrato
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .constrainAs(textColumn) {
                    top.linkTo(parent.top, margin = 40.dp)
                    bottom.linkTo(
                        loginButton.top
                    ) // Aggiungi il margine desiderato tra il testo e il pulsante
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(16.dp)
        ) {
            Text(
                text = "ACCOUNT CREATO!",
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 35.sp,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Registrazione avvenuta con successo! Torna alla schermata di login per effettuare l'accesso",
                textAlign = TextAlign.Center,
                style = TextStyle(lineHeight = 40.sp),
                fontSize = 30.sp,
            )
        }

        // Bottone centrato sotto il testo
        ElevatedButton(
            onClick = {
                presenter.effettuaRegistrazione()
                navController.navigate("SchermataAutenticazione")
            },

            modifier = Modifier
                .padding(bottom = 16.dp) // Aggiungi il margine inferiore desiderato
                .constrainAs(loginButton) {
                    top.linkTo(textColumn.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = "ACCEDI",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrazioneSuccesso() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataRegistrazioneSuccesso(navController)
    }
}
