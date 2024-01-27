package com.example.dietideals_app.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
                        composable("SchermataImmagineProfilo") {SchermataImmagineProfilo(navController = navController) }
                        composable("SchermataDiventaVenditore") {SchermataDiventaVenditore(navController = navController) }
                        composable("SchermataRegistrazioneSuccesso") { SchermataRegistrazioneSuccesso(navController = navController) }
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

    // Placeholder per l'immagine del profilo

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backgroundImage, title, boxSuccesso, contenutoBox, nonOraButton, sottoTitolo) = createRefs()

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
        Box(
            modifier = Modifier
                .constrainAs(boxSuccesso) {
                    top.linkTo(title.top, margin = 50.dp)
                    bottom.linkTo(parent.bottom,margin = 60.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }
                .size(380.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(5.dp, Color.Black, shape = RoundedCornerShape(5.dp)),

            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
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
                    style = TextStyle(
                        lineHeight = 40.sp),
                    fontSize = 35.sp,
                )
            }

        }
        Button(onClick = { presenter.effettuaRegistrazione()
            navController.navigate("SchermataAutenticazione")},
            shape = CutCornerShape(topStart = 0.dp, bottomEnd = 0.dp),
            modifier = Modifier
                .padding(bottom = 16.dp) // Aggiungi un margine inferiore
                .constrainAs(nonOraButton){
                    top.linkTo(boxSuccesso.top, margin = 400.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end,)
                }) {
            Text(
                text = "ACCEDI",
                fontSize = 30.sp, // Imposta la dimensione del font desiderata
                modifier = Modifier.padding(8.dp) // Aggiungi spaziatura interna al testo
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