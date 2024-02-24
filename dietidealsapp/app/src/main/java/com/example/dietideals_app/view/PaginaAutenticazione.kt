package com.example.dietideals_app.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.dietideals_app.model.enum.TipoAsta
import com.example.dietideals_app.presenter.AutenticazionePresenter
import com.example.dietideals_app.repository.ApiAsta
import com.example.dietideals_app.repository.ApiUtente
import com.example.dietideals_app.repository.AstaRepository
import com.example.dietideals_app.repository.UtenteRepository
import com.example.dietideals_app.repository.interfacceRepository.AstaService
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.ui.theme.titleCustomFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {
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
                        startDestination = "SchermataAutenticazione",
                        enterTransition = { fadeIn(tween(300)) },
                        exitTransition = { fadeOut(tween(300)) }

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
                        composable("schermataDatiVenditore") { SchermataDatiVenditore(navController = navController) }

                    }
                }
            }
        }
    }
}


@Composable
fun SchermataAutenticazione(navController: NavController) {
    val presenter =
        AutenticazionePresenter() // Istanza del presenter per la gestione dell'autenticazione
    var email by remember { mutableStateOf("") } // Variabile per memorizzare l'email


    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }


    var password by remember { mutableStateOf("") } // Variabile per memorizzare la password
    val passwordFocusRequester =
        remember { FocusRequester() } // Richiede il focus per l'input della password
    // Valori per le immagini della schermata
    val background = painterResource(id = R.drawable.sfondo1)
    val logoFacebook = R.drawable.facebookicon
    val logoGoogle = R.drawable.googleicon
    val logoGitHub = R.drawable.githubicon
    val logoApp = painterResource(id = R.drawable.iconaapp)

    var passwordVisibile by remember { mutableStateOf(false) } // Variabile per tenere traccia della visibilità della password
    var isLoginEnabled by remember { mutableStateOf(false) } // Variabile per tenere traccia dello stato del bottone di login

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (backgroundImage, title, appIcon, emailTextField, passwordTextField, sociaText, accessButton, socialIcons, registratiText, registerButton) = createRefs()

        // Immagine di background
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

        // Testo titolo
        Text(
            text = "DIETIDEALS 24",
            color = Color.White,
            fontFamily = titleCustomFont,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
        )

        // Icona applicazione
        Image(
            painter = logoApp,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(appIcon) {
                    top.linkTo(title.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(60.dp)
                .height(60.dp),
            contentScale = ContentScale.Crop
        )

        // Text field email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isLoginEnabled = it.isNotBlank() && password.isNotBlank()
            },
            label = { Text("E-mail") },
            placeholder = { Text("Inserisci il tuo indirizzo email") }, // Suggerimento personalizzato
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color(0xFF0EA639)
                )
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .constrainAs(emailTextField) {
                    top.linkTo(appIcon.bottom, margin = 35.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            ), // Aggiungi una logica per la validazione dell'email
            singleLine = true, // Assicurati che il campo sia a una sola riga

        )

        // Text field password
        OutlinedTextField(
            value = password,
            visualTransformation = if (passwordVisibile) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(15.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null,
                    tint = Color(0xFF0EA639)
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (!passwordVisibile) R.drawable.baseline_remove_red_eye_24 else R.drawable.closed_eye_icon), /*Cambio dinamico dell'icona per visualizzare la password*/
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        passwordVisibile = !passwordVisibile
                    },
                    tint = Color(0xFF0EA639)
                )
            },
            onValueChange = {
                password = it
                isLoginEnabled = it.length >= 5 && email.isNotBlank()
            },
            label = { Text("Password") },
            placeholder = { Text("Inserisci la tua password") },
            modifier = Modifier
                .constrainAs(passwordTextField) {
                    top.linkTo(emailTextField.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp)
                .focusRequester(passwordFocusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isLoginEnabled) {
                        // Perform login action
                        presenter.effettuaLogin(email, password)
                    }
                }
            )
        )

        // Bottone accesso
        ElevatedButton(
            onClick = {
                if (isLoginEnabled) {
                    presenter.effettuaLogin(email, password)
                }
                navController.navigate("SchermataHome") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = false
                    }
                }
            },
            modifier = Modifier
                .constrainAs(accessButton) {
                    top.linkTo(passwordTextField.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .width(140.dp)
                .height(48.dp),
            enabled = true, //isLoginEnabled,
        ) {
            Text("ACCEDI", fontSize = 20.sp)
        }

        // Icone social

        @Composable
        fun IconWithText(iconId: Int, text: String, route: String) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(2.dp)
            ) {
                Image(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navController.navigate(route) },
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = text,
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )//
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(sociaText)
            {
                top.linkTo(accessButton.bottom, margin = 16.dp)
            }, horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Oppure accedi con", textAlign = TextAlign.Center, color = Color.Gray)

        }
        Row(
            modifier = Modifier
                .constrainAs(socialIcons) {
                    top.linkTo(sociaText.bottom)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconWithText(logoGoogle, "GOOGLE", "")
            Spacer(modifier = Modifier.width(65.dp))
            IconWithText(logoFacebook, "FACEBOOK", "")
            Spacer(modifier = Modifier.width(65.dp))
            IconWithText(logoGitHub, "GITHUB", "")
        }

        // Testo registrazione
        Text(
            "Non hai un account?",
            color = Color.Gray,
            fontSize = 19.sp,
            modifier = Modifier
                .constrainAs(registratiText) {
                    top.linkTo(socialIcons.bottom, margin = 5.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        )
        // Bottone registrazione
        ElevatedButton(
            onClick = {

                navController.navigate("SchermataRegistrazione")
            },
            modifier = Modifier
                .constrainAs(registerButton) {
                    top.linkTo(registratiText.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)

                }
                .width(180.dp)
                .height(48.dp)
        ) {
            Text("REGISTRATI", fontSize = 20.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataAutenticazione(navController)
    }
}
