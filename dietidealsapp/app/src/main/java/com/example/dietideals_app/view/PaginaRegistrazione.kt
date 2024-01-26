package com.example.dietideals_app.view
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable


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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import java.text.SimpleDateFormat
import java.util.Date

class RegistrazioneActivity : ComponentActivity() {
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
                        composable("SchermataAutenticazione") {SchermataAutenticazione(navController = navController)}
                        composable("SchermataRegistrazione") {SchermataRegistrazione(navController = navController)}
                        composable("SchermataImmagineProfilo") {SchermataImmagineProfilo(navController = navController)}
                    }
                }
                }

                }
            }
        }



@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SchermataRegistrazione(navController: NavController) {
    val background =
        painterResource(id = R.drawable.sfondo3) // Variabile per memorizzare l'immagine di sfondo.

// Variabili per memorizzare le informazioni dell'utente.
    var username by remember { mutableStateOf("") } // Username
    var email by remember { mutableStateOf("") } // Indirizzo email
    var password by remember { mutableStateOf("") } // Password
    var confermaPassword by remember { mutableStateOf("") } // Conferma della password
    var nome by remember { mutableStateOf("") } // Nome dell'utente
    var cognome by remember { mutableStateOf("") } // Cognome dell'utente

    var passwordVisibile by remember { mutableStateOf(false) } // Variabile per tenere traccia della visibilità della password.


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

    val presenter = AutenticazionePresenter()


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
        val (backgroundImage, title, emailTextField, usernameTextfield, passwordTextField, confermaPasswordTextfield, datiAngraficiText, nomeTextfield, dataDiNascitafield,bottoneAvanti) = createRefs()

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
        Row(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.fillMaxWidth()
                .padding(6.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)  // Imposta la dimensione dell'icona
                    .clickable {
                        // Aggiungi l'azione desiderata quando viene cliccata l'icona
                        presenter.effettuaRegistrazione()
                        navController.navigate("SchermataAutenticazione")
                    }, tint = Color.White
            )
            Spacer(modifier = Modifier.width(5.dp))  // Aggiunge uno spazio tra l'icona e il testo
            Text(
                text = "REGISTRAZIONE",
                color = Color.White,
                fontFamily = titleCustomFont,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }


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
            onValueChange = { username = it },
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
        val state = rememberDatePickerState()
        val openDialog = remember { mutableStateOf(false) }


        @SuppressLint("SimpleDateFormat")
        fun convertMillisToDate(millis: Long): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            return formatter.format(Date(millis))
        }


        OutlinedButton(
            onClick = { openDialog.value = true },

            modifier = Modifier
                .constrainAs(dataDiNascitafield) {
                    top.linkTo(nomeTextfield.bottom, margin = 2.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .width(250.dp)
                .height(48.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.iconcalendario),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (state.selectedDateMillis == null) "Nato il DD/MM/YYYY" else "Nato il " + convertMillisToDate(
                    state.selectedDateMillis!!
                ), color = if (state.selectedDateMillis == null) Color.Gray else (Color.Black)
            )
        }
        Button(
            onClick = {
                presenter.effettuaRegistrazione()
                navController.navigate("SchermataImmagineProfilo")
            },
            modifier = Modifier// Posiziona il pulsante in basso a destra
                .padding(16.dp)
                .constrainAs(bottoneAvanti){
                    top.linkTo(dataDiNascitafield.bottom, margin = 2.dp)
                    start.linkTo(parent.start, margin = 200.dp)
                    end.linkTo(parent.end, margin = 2.dp)

                },shape = CutCornerShape(topStart = 0.dp, bottomEnd = 0.dp),

        ) {
            Text(text = "AVANTI", fontSize = 20.sp)


            if (openDialog.value) {
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false

                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("CANCEL")
                        }
                    }
                ) {
                    DatePicker(
                        state = state

                    )
                }
            }

        }

    }
}



@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataRegistrazione(navController)
    }
}


