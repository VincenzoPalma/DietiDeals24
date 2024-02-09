package com.example.dietideals_app.view


import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

class RegistrazioneImmagineProfilo : ComponentActivity() {
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
fun SchermataImmagineProfilo(navController: NavController) {
    // Ottieni l'immagine di sfondo da resources
    val background = painterResource(id = R.drawable.sfondo3)

    // Crea un presenter per la registrazione
    val presenter = AutenticazionePresenter()

    // Placeholder per l'immagine del profilo

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backgroundImage, title, profileImage, testoImmagine, testoOpzionale, bottoneAvanti) = createRefs()

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

        // Titolo e icona indietro
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
                        navController.navigate("SchermataRegistrazione")
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

        LocalContext.current as ComponentActivity
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
        // Definisci il contratto per l'activity result
        val getContent =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                // Gestisci l'URI dell'immagine selezionata
                // Puoi eseguire ulteriori operazioni qui, come caricare l'immagine
                uri?.let {
                    selectedImageUri = it
                }
            }

        // Cerchio con icona di una matita al centro
        Box(
            modifier = Modifier
                .constrainAs(profileImage) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(150.dp)
                .clip(CircleShape)
                .background(
                    Color.White
                )
                .clickable {
                    getContent.launch("image/*")
                }
                .border(5.dp, Color(0xFF0EA639), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = if (selectedImageUri == null) painterResource(id = R.drawable.baseline_brush_24) else painterResource(
                    id = R.drawable.baseline_done_24
                ),
                contentDescription = null,
                tint = Color(0xFF0EA639),
                modifier = Modifier.size(70.dp)
            )
        }
        Text(
            text = "IMMAGINE DEL PROFILO",
            modifier = Modifier.constrainAs(testoImmagine) {
                top.linkTo(profileImage.bottom, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            color = Color(0xFF0EA639), fontSize = 20.sp, fontFamily = titleCustomFont,
        )
        Text(
            text = "(OPZIONALE)", color = Color(0xFF0EA639), fontSize = 20.sp,
            modifier = Modifier.constrainAs(testoOpzionale) {
                top.linkTo(testoImmagine.bottom, margin = 1.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, fontFamily = titleCustomFont
        )
        ElevatedButton(onClick = {
            presenter.effettuaRegistrazione()
            navController.navigate("SchermataDiventaVenditore")
        }, modifier = Modifier.constrainAs(bottoneAvanti) {
            top.linkTo(testoOpzionale.bottom, margin = 100.dp)
            bottom.linkTo(parent.bottom, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }) {
            Text(text = "AVANTI", fontSize = 20.sp)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewSchermataImmagineProfilo() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataImmagineProfilo(navController)
    }
}