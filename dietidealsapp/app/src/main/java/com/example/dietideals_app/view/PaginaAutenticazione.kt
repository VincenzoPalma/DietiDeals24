package com.example.dietideals_app.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.presenter.AutenticazionePresenter
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.ui.theme.titleCustomFont

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
                    NavHost(navController = navController, startDestination = "SchermataAutenticazione") {
                        composable("SchermataAutenticazione") { SchermataAutenticazione(navController = navController) }
                        composable("prova") { SimpleScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun SchermataAutenticazione(navController: NavController) {
    val presenter = AutenticazionePresenter() //instanza del presenter
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passwordFocusRequester = remember { FocusRequester() }
    //valori per le immagini della schermata
    val background = painterResource(id = R.drawable.sfondoprova)
    val logoFacebook = painterResource(id = R.drawable.facebookicon)
    val logoGoogle = painterResource(id = R.drawable.googleicon)
    val logoGitHub = painterResource(id = R.drawable.githubicon)
    val logoApp = painterResource(id = R.drawable.iconaapp)
    var passwordVisibile by remember { mutableStateOf(false) } //variabile per tenere traccia della visibilità della password
    var isLoginEnabled by remember { mutableStateOf(false) } //variabile per tenere traccia dello stato del bottone di login

    Box(
        modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {
        //immagine di background
        Image(
            painter = background,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //testo titolo
            Text(text = "DIETIDEALS 24", color = Color.White, fontFamily = titleCustomFont,textAlign = TextAlign.Center,fontSize = 40.sp, modifier = Modifier.fillMaxWidth()) //aggiungere font
            Spacer(modifier = Modifier.height(10.dp))
            //icona applicazione
            Image(
                painter = logoApp,
                contentDescription = null,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(35.dp))
            //text field email
            TextField(
                label = { Text("E-mail") },
                //aggiungere icone personalizzate
                leadingIcon = {Icon(imageVector = Icons.Default.Email, contentDescription = null)},
                value = email,
                onValueChange = {
                    email = it
                    isLoginEnabled = it.isNotBlank() && password.isNotBlank()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { passwordFocusRequester.requestFocus() }
                )
            )
            //text field password
            TextField(
                value = password,
                visualTransformation = if(passwordVisibile) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_lock_24), contentDescription = null)},
                trailingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_remove_red_eye_24), contentDescription = null,
                    modifier = Modifier.clickable{passwordVisibile = !passwordVisibile})},
                onValueChange = {
                    password = it
                    isLoginEnabled = it.isNotBlank() && email.isNotBlank() //ricontrollare
                },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
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

            Spacer(modifier = Modifier.height(16.dp))
            //bottone accesso
            Button(
                onClick = {
                    if (isLoginEnabled) {
                        presenter.effettuaLogin(email, password)
                    }
                },
                modifier = Modifier
                    .width(140.dp)
                    .height(48.dp),
                shape = CutCornerShape(topStart = 0.dp, bottomEnd = 0.dp),
                enabled = isLoginEnabled
            ) {
                Text("ACCEDI", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text("oppure accedi con", color = Color.Gray, fontSize = 19.sp)

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
            )
            {
                Image(
                    painter = logoGoogle,
                    contentDescription = null,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .clickable {/*login google*/ },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(65.dp))
                Image(
                    painter = logoFacebook,
                    contentDescription = null,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .clickable {/*login facebook*/ },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(65.dp))
                Image(
                    painter = logoGitHub,
                    contentDescription = null,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .clickable {/*login github*/ },
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text("Non hai un account?", color = Color.Gray, fontSize = 19.sp)
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    presenter.effettuaRegistrazione()
                    navController.navigate("prova")
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(48.dp),
                shape = CutCornerShape(topStart = 0.dp, bottomEnd = 0.dp),
            ) {
                Text("REGISTRATI", fontSize = 20.sp)
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    DietidealsappTheme {
        //SchermataAutenticazione()
    }
}
