package com.example.dietideals_app.view
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.dietideals_app.R
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.ui.theme.titleCustomFont
import java.util.Calendar

class RegistrazioneActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
                }

                }
            }
        }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
//funzione per la creazione della schermata di registrazionee
fun SchermataRegistrazione() {
    val background = painterResource(id = R.drawable.sfondo3) // Variabile per memorizzare l'immagine di sfondo.

// Variabili per memorizzare le informazioni dell'utente.
    var username by remember { mutableStateOf("") } // Username
    var email by remember { mutableStateOf("") } // Indirizzo email
    var password by remember { mutableStateOf("") } // Password
    var confermaPassword by remember { mutableStateOf("") } // Conferma della password
    var nome by remember { mutableStateOf("") } // Nome dell'utente
    var cognome by remember { mutableStateOf("") } // Cognome dell'utente

    var dataDiNascita by remember { mutableStateOf(Calendar.getInstance()) } // ToDo: Memorizza la data di nascita dell'utente. (Da implementare)

    var passwordVisibile by remember { mutableStateOf(false) } // Variabile per tenere traccia della visibilità della password.
    var confermaPasswordVisibile by remember { mutableStateOf(false) } // Variabile per tenere traccia della visibilità della conferma della password.

    val passwordFocusRequester =
        remember { FocusRequester() } // Richiede il focus per l'input della password.
    val usernameFocusRequester =
        remember { FocusRequester() } // Richiede il focus per l'input dell'username.
    val confermaPasswordFocusRequester =
        remember { FocusRequester() } // Richiede il focus per l'input della conferma della password.
    val nomeFocusRequester =
        remember { FocusRequester() } // Richiede il focus per l'input del nome.
    val cognomeFocusRequester =
        remember { FocusRequester() } // Richiede il focus per l'input del cognome.

    var passwordMatching by remember { mutableStateOf(false) } // Variabile per indicare se la password e la conferma della password coincidono.

    //Funzione per controllare se le due password coincidono ToDo
    fun checkPasswordMatching() {
        if (password.isNotBlank() && confermaPassword.isNotBlank()) {
            passwordMatching = password == confermaPassword
        }
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (backgroundImage, title, emailTextField, usernameTextfield, passwordTextField, confermaPasswordTextfield, datiAngraficiText, nomeTextfield, cognomeTextField, datDiNascitafield, ) = createRefs()

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
            text = "REGISTRAZIONE",
            color = Color.White,
            fontFamily = titleCustomFont,
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 35.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
        )

// Text Field E-mail
        OutlinedTextField(
            label = { Text("E-mail") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color(0xFF0EA639)
                )
            },
            shape = RoundedCornerShape(15.dp),
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .constrainAs(emailTextField) {
                    top.linkTo(title.bottom, margin = 80.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { usernameFocusRequester.requestFocus() })
        )

// Text Field Username
        OutlinedTextField(
            value = username,
            shape = RoundedCornerShape(15.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = null,
                    tint = Color(0xFF0EA639)
                )
            },
            onValueChange = {username = it },
            label = { Text("Username") },
            modifier = Modifier
                .constrainAs(usernameTextfield) {
                    top.linkTo(emailTextField.bottom, margin = 2.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp)
                .focusRequester(usernameFocusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() })
        )

// Text Field Password
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
                    painter = painterResource(id = if (!passwordVisibile) R.drawable.baseline_remove_red_eye_24 else R.drawable.closed_eye_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable { passwordVisibile = !passwordVisibile },
                    tint = Color(0xFF0EA639)
                )
            },
            onValueChange = { password = it },
            label = { Text("Password") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (!passwordMatching) Color.Red else Color(0xFF0EA639),
                unfocusedBorderColor = if (!passwordMatching) Color.Red else Color.Gray
            ),
            modifier = Modifier
                .constrainAs(passwordTextField) {
                    top.linkTo(usernameTextfield.bottom, margin = 2.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp)
                .focusRequester(passwordFocusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { confermaPasswordFocusRequester.requestFocus() })
        )

// Text Field Conferma Password
        OutlinedTextField(
            value = confermaPassword,
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
                    painter = painterResource(id = if (!passwordVisibile) R.drawable.baseline_remove_red_eye_24 else R.drawable.closed_eye_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable { passwordVisibile = !passwordVisibile },
                    tint = Color(0xFF0EA639)
                )
            },
            onValueChange = {
                confermaPassword = it
                checkPasswordMatching()
            },
            label = { Text("Conferma Password") },
            modifier = Modifier
                .constrainAs(confermaPasswordTextfield) {
                    top.linkTo(passwordTextField.bottom, margin = 2.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp)
                .focusRequester(confermaPasswordFocusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { nomeFocusRequester.requestFocus() })
        )

// Label Dati Anagrafici
        Text(
            text = "DATI ANAGRAFICI",
            color = Color.Black,
            fontFamily = titleCustomFont,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            modifier = Modifier
                .constrainAs(datiAngraficiText) {
                    top.linkTo(confermaPasswordTextfield.top, margin = 90.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

// Gruppo di TextField per Nome e Cognome
        Row(
            modifier = Modifier
                .constrainAs(nomeTextfield) {
                    top.linkTo(datiAngraficiText.bottom, margin = 2.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp),
        ) {
            // Primo TextField (Nome)
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                shape = RoundedCornerShape(15.dp),
                label = { Text("Nome") },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .focusRequester(nomeFocusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { cognomeFocusRequester.requestFocus() })
            )

            Spacer(modifier = Modifier.width(8.dp)) // Spazio tra i TextField

            // Secondo TextField (Cognome)
            OutlinedTextField(
                value = cognome,
                onValueChange = { cognome = it },
                shape = RoundedCornerShape(15.dp),
                label = { Text("Cognome") },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .focusRequester(cognomeFocusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    DietidealsappTheme {
        SchermataRegistrazione()
    }
}


