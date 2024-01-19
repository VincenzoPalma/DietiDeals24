package com.example.dietideals_app

import android.graphics.fonts.FontFamily
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.ui.theme.customfont

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passwordFocusRequester = remember { FocusRequester() }
    val background = painterResource(id = R.drawable.sfondoprova)
    val logoApp = painterResource(id = R.drawable.iconaapp)
    var passwordVisibile by remember { mutableStateOf(false) } //variabile per tenere traccia della visibilità della password
    var isLoginEnabled by remember { mutableStateOf(false) } //variabile per tenere traccia dello stato del bottone di login

    Box(
        modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {
        Image(
            painter = background,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        //aggiungere icona dell'applicazione
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "DIETIDEALS 24", color = Color.White, fontFamily = customfont,textAlign = TextAlign.Center,fontSize = 40.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth()) //aggiungere font
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = logoApp,
                contentDescription = null,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(35.dp))

            TextField(
                label = { Text("E-mail") },
                //aggiungere icone personalizzate
                leadingIcon = {Icon(imageVector = Icons.Default.Email, contentDescription = null)},
                value = username,
                onValueChange = {
                    username = it
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
            TextField(
                value = password,
                visualTransformation = if(passwordVisibile) VisualTransformation.None else PasswordVisualTransformation(),
                //aggiungere icone personalizzate
                leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_lock_24), contentDescription = null)},
                //icona cliccabile per nascondere e mostrare password, non funzionante
                trailingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_remove_red_eye_24), contentDescription = null,
                    modifier = Modifier.clickable{passwordVisibile = !passwordVisibile})},
                onValueChange = {
                    password = it
                    isLoginEnabled = it.isNotBlank() && username.isNotBlank()
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
                            effettuaLogin(username, password)
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isLoginEnabled) {
                        // Perform login action
                        effettuaLogin(username, password)
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
            {//aggiungere icone effettive dei mockup
                IconButton(onClick = { /*login google*/ }) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "Bottone per accesso tramite Google.")
                }
                Spacer(modifier = Modifier.width(85.dp))
                IconButton(onClick = { /*login facebook*/ }) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "Bottone per accesso tramite Facebook.")
                }
                Spacer(modifier = Modifier.width(85.dp))
                IconButton(onClick = { /*login github*/ }) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "Bottone per accesso tramite GitHub.")
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text("Non hai un account?", color = Color.Gray, fontSize = 19.sp)
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    effettuaRegistrazione()
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

fun effettuaLogin(username: String, password: String) {
    // Placeholder function for the login action
    // You can implement your authentication logic here
}

fun effettuaRegistrazione() {
    // Placeholder function for the sign up action
    // You can implement your authentication logic here
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    DietidealsappTheme {
        LoginScreen()
    }
}
