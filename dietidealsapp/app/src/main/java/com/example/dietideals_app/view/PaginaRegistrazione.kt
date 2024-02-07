@file:Suppress("KotlinConstantConditions")

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import java.util.Calendar
import java.util.Date

class RegistrazioneActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = "SchermataAutenticazione"
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

    val presenter = AutenticazionePresenter()

    val state = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }


    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {


        val (backgroundImage, title, emailTextField, usernameTextfield, passwordTextField, confermaPasswordTextfield, datiAngraficiText, nomeTextfield, dataDiNascitafield, bottoneAvanti) = createRefs()
        fun isEmailValid(email: String): Boolean {
            // In questo esempio, si utilizza un'espressione regolare per verificare il formato dell'email
            val emailRegex = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)")
            return email.matches(emailRegex)
        }


        // Funzione di validazione dell'username
        fun isUsernameValid(username: String): Boolean {
            // Puoi definire i criteri di validazione dell'username qui
            return username.isNotBlank()
        }

        // Funzione di validazione della password
        fun isPasswordValid(password: String): Boolean {
            // Puoi definire i criteri di validazione della password qui (es. lunghezza minima)
            return password.length >= 8
        }

        // Funzione di verifica della corrispondenza tra password e conferma password
        fun isPasswordMatching(password: String, confirmPassword: String): Boolean {
            return password == confirmPassword
        }

        // Funzione di validazione del nome
        fun isNomeValid(nome: String): Boolean {
            // Puoi definire i criteri di validazione del nome qui
            return nome.isNotBlank()
        }

        // Funzione di validazione del cognome
        fun isCognomeValid(cognome: String): Boolean {
            // Puoi definire i criteri di validazione del cognome qui
            return cognome.isNotBlank()
        }

        fun calculateAge(birthDate: Date, currentDate: Date): Int {
            val birthCalendar = Calendar.getInstance()
            val currentCalendar = Calendar.getInstance()

            birthCalendar.time = birthDate
            currentCalendar.time = currentDate

            var age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

            // Riduci l'età di uno se la data di nascita non è ancora arrivata quest'anno
            if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--
            }

            return age
        }

        fun isUserAdult(selectedDateMillis: Long?): Boolean {
            // Verifica se la data di nascita è stata selezionata
            if (selectedDateMillis == null) {
                return false
            }

            // Calcola la data corrente
            val currentDate = Calendar.getInstance().time

            // Ottieni la data di nascita dall'input dell'utente
            val birthDate = Date(selectedDateMillis)

            // Calcola l'età dell'utente
            val age = calculateAge(birthDate, currentDate)

            // Verifica se l'utente è maggiorenne (ad esempio, se ha almeno 18 anni)
            return age >= 18
        }

        fun checkFields(): Boolean {
            // Verifica qui tutti i campi e restituisci true solo se sono tutti compilati correttamente
            return isEmailValid(email) && isUsernameValid(username) && isPasswordValid(password) && isPasswordMatching(
                password, confermaPassword
            ) && isNomeValid(nome) && isCognomeValid(cognome) && isUserAdult(state.selectedDateMillis)
        }
        // Immagine di background
        Image(
            painter = background,
            contentDescription = null,
            modifier = Modifier.constrainAs(backgroundImage) {
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
        Row(modifier = Modifier
            .constrainAs(title) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth()
            .padding(6.dp)) {
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
                    },
                tint = Color.White
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

        var isValidEmail by remember { mutableStateOf(false) }
// Text Field E-mail
        OutlinedTextField(label = {
            Text(
                "E-mail",
                color = if (isValidEmail) Color(0xFF0EA639) else if (!isValidEmail && email.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Black,
            )
        },
            shape = RoundedCornerShape(15.dp),
            value = email,
            onValueChange = {
                email = it
                isValidEmail = isEmailValid(it)
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(
                        id = if (isValidEmail) R.drawable.baseline_done_24 else if (!isValidEmail && email.isNotEmpty()) R.drawable.baseline_close_24 else R.drawable.empty

                    ),
                    contentDescription = null,
                    tint = if (isValidEmail) Color(0xFF0EA639) else if (!isValidEmail && email.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                    modifier = if (email.isEmpty()) Modifier.alpha(0f) else Modifier

                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (isValidEmail) Color(0xFF0EA639) else if (!isValidEmail && email.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
                focusedBorderColor = if (isValidEmail) Color(0xFF0EA639) else if (!isValidEmail && email.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = if (isValidEmail) Color(0xFF0EA639) else if (!isValidEmail && email.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                )

            },
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
        var isValidUsername by remember { mutableStateOf(false) }
        OutlinedTextField(value = username,
            shape = RoundedCornerShape(15.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = null,
                    tint = if (isValidUsername) Color(0xFF0EA639) else if (!isValidUsername && username.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (isValidUsername) Color(0xFF0EA639) else if (!isValidUsername && username.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
                focusedBorderColor = if (isValidUsername) Color(0xFF0EA639) else if (!isValidUsername && username.isNotEmpty()) Color(
                    0xFF9B0404
                ) else Color.Gray,
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(
                        id = if (isValidUsername) R.drawable.baseline_done_24 else if (!isValidUsername && username.isNotEmpty()) R.drawable.baseline_close_24 else R.drawable.empty
                    ),
                    contentDescription = null,
                    tint = if (isValidUsername) Color(0xFF0EA639) else if (!isValidUsername && username.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                    modifier = if (username.isEmpty()) Modifier.alpha(0f) else Modifier

                )
            },
            onValueChange = {
                username = it
                isValidUsername = isUsernameValid(username)
            },

            label = {
                Text(
                    "Username",
                    color = if (isValidUsername) Color(0xFF0EA639) else if (!isValidUsername && username.isNotEmpty()) Color(
                        0xFF9B0404
                    ) else Color.Black,
                )
            },
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
        var isValidPassword by remember { mutableStateOf(false) }
        var matchedPassword by remember { mutableStateOf(false) }
        OutlinedTextField(value = password,
            visualTransformation = if (passwordVisibile) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(15.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null,
                    tint = if (isValidPassword && matchedPassword) Color(0xFF0EA639) else if (password.isNotEmpty() && (!matchedPassword || !isValidPassword)) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (isValidPassword && matchedPassword) Color(0xFF0EA639) else if (password.isNotEmpty() && (!matchedPassword || !isValidPassword)) Color(
                    0xFF9B0404
                ) else Color.Gray,
                focusedBorderColor = if (isValidPassword && matchedPassword) Color(0xFF0EA639) else if (password.isNotEmpty() && (!matchedPassword || !isValidPassword)) Color(
                    0xFF9B0404
                ) else Color.Gray,
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (!passwordVisibile) R.drawable.baseline_remove_red_eye_24 else R.drawable.closed_eye_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable { passwordVisibile = !passwordVisibile },
                    tint = if (isValidPassword && matchedPassword) Color(0xFF0EA639) else if (password.isNotEmpty() && (!matchedPassword || !isValidPassword)) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                )
            },
            onValueChange = {
                password = it
                isValidPassword = isPasswordValid(it)
                matchedPassword = isPasswordMatching(it, confermaPassword)
            },
            label = {
                Text(
                    "Password",
                    color = if (isValidPassword && matchedPassword) Color(0xFF0EA639) else if (password.isNotEmpty() && (!matchedPassword || !isValidPassword)) Color(
                        0xFF9B0404
                    ) else Color.Black,
                )
            },
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
        var isValidConfirmedPassword by remember { mutableStateOf(false) }

        OutlinedTextField(value = confermaPassword,
            visualTransformation = if (passwordVisibile) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(15.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null,
                    tint = if (isValidConfirmedPassword && matchedPassword) Color(0xFF0EA639) else if (confermaPassword.isNotEmpty() && (!isValidConfirmedPassword || !matchedPassword)) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (isValidConfirmedPassword && matchedPassword) Color(
                    0xFF0EA639
                ) else if (confermaPassword.isNotEmpty() && (!isValidConfirmedPassword || !matchedPassword)) Color(
                    0xFF9B0404
                ) else Color.Gray,
                focusedBorderColor = if (isValidConfirmedPassword && matchedPassword) Color(
                    0xFF0EA639
                ) else if (confermaPassword.isNotEmpty() && (!isValidConfirmedPassword || !matchedPassword)) Color(
                    0xFF9B0404
                ) else Color.Gray,
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (!passwordVisibile) R.drawable.baseline_remove_red_eye_24 else R.drawable.closed_eye_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable { passwordVisibile = !passwordVisibile },
                    tint = if (isValidConfirmedPassword && matchedPassword) Color(0xFF0EA639) else if (confermaPassword.isNotEmpty() && (!isValidConfirmedPassword || !matchedPassword)) Color(
                        0xFF9B0404
                    ) else Color.Gray,
                )
            },
            onValueChange = {
                confermaPassword = it
                matchedPassword = isPasswordMatching(password, it)
                isValidConfirmedPassword = isPasswordValid(confermaPassword)
            },
            label = {
                Text(
                    "Conferma Password",
                    color = if (isValidConfirmedPassword && matchedPassword) Color(0xFF0EA639) else if (confermaPassword.isNotEmpty() && (!isValidConfirmedPassword || !matchedPassword)) Color(
                        0xFF9B0404
                    ) else Color.Black,
                )
            },
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
        Text(text = "DATI ANAGRAFICI",
            color = Color.Black,
            fontFamily = titleCustomFont,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            modifier = Modifier.constrainAs(datiAngraficiText) {
                top.linkTo(confermaPasswordTextfield.top, margin = 90.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

// Gruppo di TextField per Nome e Cognome
        Row(
            modifier = Modifier
                .constrainAs(nomeTextfield) {
                    top.linkTo(datiAngraficiText.bottom)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp),
        ) {
            // Primo TextField (Nome)
            var isValidName by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    isValidName = isNomeValid(it)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = if (isValidName) Color(0xFF0EA639) else Color.Gray,
                    focusedBorderColor = if (isValidName) Color(0xFF0EA639) else Color.Gray,
                ),
                shape = RoundedCornerShape(15.dp),
                label = {
                    Text(
                        "Nome", color = if (nome.isNotEmpty()) Color(0xFF0EA639) else Color.Black
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .focusRequester(nomeFocusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { cognomeFocusRequester.requestFocus() })
            )

            Spacer(modifier = Modifier.width(8.dp)) // Spazio tra i TextField

            // Secondo TextField (Cognome)
            var isValidCognome by remember { mutableStateOf(false) }
            OutlinedTextField(value = cognome,
                onValueChange = {
                    cognome = it
                    isValidCognome = isCognomeValid(it)
                },
                shape = RoundedCornerShape(15.dp),
                label = {
                    Text(
                        "Cognome",
                        color = if (cognome.isNotEmpty()) Color(0xFF0EA639) else Color.Black
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = if (isValidCognome) Color(0xFF0EA639) else Color.Gray,
                    focusedBorderColor = if (isValidCognome) Color(0xFF0EA639) else Color.Gray,
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .focusRequester(cognomeFocusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
        }



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
                painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                contentDescription = null,
                tint = if (isUserAdult(state.selectedDateMillis) && state.selectedDateMillis != null) Color(
                    0xFF0EA639
                ) else if (!isUserAdult(state.selectedDateMillis) && state.selectedDateMillis != null) Color(
                    0xFF9B0404
                ) else (Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (state.selectedDateMillis == null) "Nato il DD/MM/YYYY" else "Nato il " + convertMillisToDate(
                    state.selectedDateMillis!!
                ),
                color = if (isUserAdult(state.selectedDateMillis) && state.selectedDateMillis != null) Color(
                    0xFF0EA639
                ) else if (!isUserAdult(state.selectedDateMillis) && state.selectedDateMillis != null) Color(
                    0xFF9B0404
                ) else (Color.Gray)
            )
        }
        ElevatedButton(
            onClick = {
                presenter.effettuaRegistrazione()
                navController.navigate("SchermataImmagineProfilo")
            },
            enabled = checkFields(),
            modifier = Modifier// Posiziona il pulsante in basso a destra
                .padding(16.dp)
                .constrainAs(bottoneAvanti) {
                    top.linkTo(dataDiNascitafield.bottom, margin = 2.dp)
                    start.linkTo(parent.start, margin = 200.dp)
                    end.linkTo(parent.end, margin = 2.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)

                },

            ) {
            Text(text = "AVANTI", fontSize = 20.sp)




        }
        if (openDialog.value) {
            DatePickerDialog(onDismissRequest = {
                openDialog.value = false
            }, confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false

                }) {
                    Text("OK")
                }
            }, dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                }) {
                    Text("CANCEL")
                }
            }) {
                DatePicker(
                    state = state

                )
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


