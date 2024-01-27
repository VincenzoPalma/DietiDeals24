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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
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
fun SchermataDiventaVenditore(navController: NavController) {
    // Ottieni l'immagine di sfondo da resources
    val background = painterResource(id = R.drawable.sfondo3)

    // Crea un presenter per la registrazione
    val presenter = AutenticazionePresenter()

    // Placeholder per l'immagine del profilo

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backgroundImage, title, testoVenditore, boxVenditore, contenutoBox,nonOraButton, sottoTitolo) = createRefs()

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
            Spacer(modifier = Modifier.height(4.dp))

            // Icona indietro
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        // Azione quando l'icona indietro viene cliccata
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
        Text(
            text = "Sei un venditore?",
            color = Color.White,
            fontFamily = titleCustomFont,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(testoVenditore) {
                top.linkTo(title.top, margin = 40.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end,)
            }
        )
        Box(
            modifier = Modifier
                .constrainAs(boxVenditore) {
                    top.linkTo(testoVenditore.top,margin = 200.dp)
                    bottom.linkTo(parent.bottom,margin = 300.dp)
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
                    text = "PASSA ALL'ACCOUNT VENDITORE",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "I VANTAGGI",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "1) CREARE ASTE ALL'INGLESE",
                    textAlign = TextAlign.Left,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "2) CREARE ASTE SILENZIOSE",
                    textAlign = TextAlign.Left,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "3) PARTECIPARE ALLE ASTE INVERSE",
                    textAlign = TextAlign.Left,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {},
                    shape = CutCornerShape(topStart = 0.dp, bottomEnd = 0.dp),
                    modifier = Modifier
                        .padding(bottom = 16.dp) // Aggiungi un margine inferiore
                        .fillMaxWidth()
                        // Usa tutta la larghezza disponibile
                ) {
                    Text(
                        text = "DIVENTA UN VENDITORE",
                        fontSize = 20.sp, // Imposta la dimensione del font desiderata
                        modifier = Modifier.padding(8.dp) // Aggiungi spaziatura interna al testo
                    )
                }
            }
        }
        Button(
            onClick = {presenter.effettuaRegistrazione()
                      navController.navigate("SchermataRegistrazioneSuccesso")},
            shape = CutCornerShape(topStart = 0.dp, bottomEnd = 0.dp),
            modifier = Modifier
                .padding(bottom = 16.dp) // Aggiungi un margine inferiore
                .constrainAs(nonOraButton){
                    top.linkTo(boxVenditore.top, margin = 400.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end,)
                }, colors = ButtonColors(containerColor=Color(0xFF9b0404), contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.Black) // Usa tutta la larghezza disponibile
        ) {
            Text(
                text = "NON ORA",
                fontSize = 20.sp, // Imposta la dimensione del font desiderata
                modifier = Modifier.padding(8.dp) // Aggiungi spaziatura interna al testo
            )
        }
        Text(
            text = "POTRAI PASSARE AD UN ACCOUNT VENDITORE IN UN SECONDO MOMENTO",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 8.sp,
            modifier = Modifier.constrainAs(sottoTitolo){
                top.linkTo(nonOraButton.top, margin = 60.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end,)
            }
        )

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
