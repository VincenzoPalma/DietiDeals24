@file:Suppress("KotlinConstantConditions")

package com.example.dietideals_app.view


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.model.ContoCorrente
import com.example.dietideals_app.model.dto.UtenteRegistrazione
import com.example.dietideals_app.model.enum.RuoloUtente
import com.example.dietideals_app.ui.theme.DietidealsappTheme
import com.example.dietideals_app.ui.theme.titleCustomFont
import com.example.dietideals_app.viewmodel.PaginaRegistrazioneViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Formatter

class RegistrazioneActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietidealsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SchermataRegistrazione(navController)
                }
            }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SchermataRegistrazione(navController: NavController) {
    val background = painterResource(id = R.drawable.sfondo3) // Variabile per memorizzare l'immagine di sfondo.

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

    val viewModel = PaginaRegistrazioneViewModel()
    val isDialogVisible = remember { mutableStateOf(false) }
    val state = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }
    val currentPage = remember { mutableIntStateOf(0) }


    //TODO IN TEORIA RISOLTO INIZIALIZZANDO LE VARIABILI QUI ALTRIMENTI SI RESETTAVANO A FALSE E QUINDI USCIVA TUTTO ROSSO
    var isValidEmail by remember { mutableStateOf(false) }
    var emailExists by remember { mutableStateOf(false) }
    var isValidUsername by remember { mutableStateOf(false) }
    var usernameExists by remember { mutableStateOf(false) }
    var isValidPassword by remember { mutableStateOf(false) }
    var matchedPassword by remember { mutableStateOf(false) }


    when (currentPage.intValue) {
        0 -> {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (backgroundImage, title, emailTextField, usernameTextfield, passwordTextField, confermaPasswordTextfield, datiAngraficiText, nomeTextfield, dataDiNascitafield, bottoneAvanti) = createRefs()
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


// Text Field E-mail
                OutlinedTextField(
                    supportingText = { Text(text = if (emailExists) "E-mail non disponibile" else "", color = MaterialTheme.colorScheme.error)},
                    label = {
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
                        isValidEmail = viewModel.isEmailValid(it)
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

                OutlinedTextField(
                    supportingText = { Text(text = if (usernameExists) "Username non disponibile" else "", color = MaterialTheme.colorScheme.error)},
                    value = username,
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
                        isValidUsername = viewModel.isUsernameValid(username)
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
                        unfocusedBorderColor = if (isValidPassword && matchedPassword) Color(
                            0xFF0EA639
                        ) else if (password.isNotEmpty() && (!matchedPassword || !isValidPassword)) Color(
                            0xFF9B0404
                        ) else Color.Gray,
                        focusedBorderColor = if (isValidPassword && matchedPassword) Color(
                            0xFF0EA639
                        ) else if (password.isNotEmpty() && (!matchedPassword || !isValidPassword)) Color(
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
                        isValidPassword = viewModel.isPasswordValid(it)
                        matchedPassword = viewModel.isPasswordMatching(it, confermaPassword)
                    },
                    label = {
                        Text(
                            "Password",
                            color = if (isValidPassword) Color(0xFF0EA639) else if (password.isNotEmpty() && !isValidPassword) Color(
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
                        matchedPassword = viewModel.isPasswordMatching(password, it)
                        isValidConfirmedPassword = viewModel.isPasswordValid(confermaPassword)
                    },
                    label = {
                        Text(
                            "Conferma Password",
                            color = if (isValidConfirmedPassword && matchedPassword) Color(
                                0xFF0EA639
                            ) else if (confermaPassword.isNotEmpty() && (!isValidConfirmedPassword || !matchedPassword)) Color(
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
                            isValidName = viewModel.isNomeValid(it)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (isValidName) Color(0xFF0EA639) else Color.Gray,
                            focusedBorderColor = if (isValidName) Color(0xFF0EA639) else Color.Gray,
                        ),
                        shape = RoundedCornerShape(15.dp),
                        label = {
                            Text(
                                "Nome",
                                color = if (nome.isNotEmpty()) Color(0xFF0EA639) else Color.Black
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
                    OutlinedTextField(
                        value = cognome,
                        onValueChange = {
                            cognome = it
                            isValidCognome = viewModel.isCognomeValid(it)
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
                        tint = if (viewModel.isUserAdult(state.selectedDateMillis) && state.selectedDateMillis != null) Color(
                            0xFF0EA639
                        ) else if (!viewModel.isUserAdult(state.selectedDateMillis) && state.selectedDateMillis != null) Color(
                            0xFF9B0404
                        ) else (Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (state.selectedDateMillis == null) "Nato il DD/MM/YYYY" else "Nato il " + viewModel.convertMillisToDate(
                            state.selectedDateMillis!!
                        ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(),
                        color = if (viewModel.isUserAdult(state.selectedDateMillis) && state.selectedDateMillis != null) Color(
                            0xFF0EA639
                        ) else if (!viewModel.isUserAdult(state.selectedDateMillis) && state.selectedDateMillis != null) Color(
                            0xFF9B0404
                        ) else (Color.Gray)
                    )
                }
                ElevatedButton(
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            emailExists = viewModel.doesEmailExist(email)
                            usernameExists = viewModel.doesUsernameExist(username)
                            if(!emailExists && !usernameExists){
                                currentPage.intValue = 1
                            }
                        }
                    },
                    enabled = viewModel.checkFields(email, password, confermaPassword, nome, cognome, username, state),
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

        1 -> {


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
                                currentPage.intValue = 0
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
                    currentPage.intValue = 2
                }, modifier = Modifier.constrainAs(bottoneAvanti) {
                    top.linkTo(testoOpzionale.bottom, margin = 100.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }) {
                    Text(text = "AVANTI", fontSize = 20.sp)
                }
            }

        }

        2 -> {

            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (backgroundImage, title, sellerText, contentBox, notNowButton) = createRefs()


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
                    Spacer(modifier = Modifier.height(4.dp))

                    // Icona indietro
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                currentPage.intValue = 1
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

                // Testo "Sei un venditore?"
                Text(
                    text = "Sei un venditore?",
                    color = Color.White,
                    fontFamily = titleCustomFont,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.constrainAs(sellerText) {
                        top.linkTo(title.top, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

                // Contenuto centrato
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .constrainAs(contentBox) {
                            top.linkTo(sellerText.top, margin = 125.dp)
                            bottom.linkTo(notNowButton.top, margin = 100.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .size(450.dp)
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    // Testi e contenuti della Box
                    Text(
                        text = "PASSA ALL'ACCOUNT VENDITORE!\n\n" +
                                "I VANTAGGI:\n\n" +
                                "1) CREARE ASTE ALL'INGLESE\n" +
                                "2) CREARE ASTE SILENZIOSE\n" +
                                "3) PARTECIPARE ALLE ASTE INVERSE",
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp,
                    )


                    Spacer(modifier = Modifier.height(30.dp))

                    // Bottone "DIVENTA UN VENDITORE"
                    ElevatedButton(
                        onClick = {
                            currentPage.intValue = 3
                        },

                        modifier = Modifier
                            .padding(bottom = 16.dp) // Aggiungi un margine inferiore
                            .fillMaxWidth() // Usa tutta la larghezza disponibile
                    ) {
                        Text(
                            text = "DIVENTA UN VENDITORE",
                            fontSize = 20.sp, // Imposta la dimensione del font desiderata
                            modifier = Modifier.padding(8.dp) // Aggiungi spaziatura interna al testo
                        )

                    }
                    TextButton(
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                viewModel.registraUtente(UtenteRegistrazione(username, RuoloUtente.COMPRATORE, nome, cognome, viewModel.convertMillisToDate(state.selectedDateMillis!!).toString(), email, password, null, null, null, null))
                            }
                            isDialogVisible.value = true
                        },
                        // Aggiungi un margine inferiore


                    ) {
                        Text(
                            text = "NON ORA",
                            fontSize = 20.sp, // Imposta la dimensione del font desiderata
                            modifier = Modifier.padding(8.dp),
                            color = Color.Red// Aggiungi spaziatura interna al testo
                        )
                    }

                    // Testo sotto il bottone "NON ORA"
                    Text(
                        text = "POTRAI PASSARE AD UN ACCOUNT VENDITORE IN UN SECONDO MOMENTO",
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        fontSize = 9.sp,
                    )
                }


                // Bottone "NON ORA"

            }
        }

        3 -> {


            var partitaIva by remember { mutableStateOf("") } //11 caratteri numerici

            var nomeTitolare by remember { mutableStateOf("") }

            var codiceBicSwift by remember { mutableStateOf("") } //8-11 cifre

            var iban by remember { mutableStateOf("") } /*27caratteri lettere e numeri*/


            var isCodiceBicSwiftValid by remember { mutableStateOf(false) }
            var isNomeTitolareValid by remember { mutableStateOf(false) }
            var isIbanValid by remember { mutableStateOf(false) }
            var isPartitaIvaValid by remember { mutableStateOf(false) }

            val nomeTitolareFocusRequester = remember { FocusRequester() }
            val bicSwiftCodeFocusReqeuster = remember { FocusRequester() }
            val ibanFocusRequest = remember { FocusRequester() }




            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (backgroundImage, title, subTitle, documentRow, partitaIvaTextField, bankAccountTitle, ownerTextField, bicSwiftCodeTextField, ibanTextField, buttons) = createRefs()

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
                Text(
                    text = "REGISTRAZIONE",
                    color = Color.White,
                    fontFamily = titleCustomFont,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(title)
                        {
                            top.linkTo(parent.top, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = "Dati Aggiuntivi",
                    color = Color.White,
                    fontFamily = titleCustomFont,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.constrainAs(subTitle) {
                        top.linkTo(title.top, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Row(
                    modifier = Modifier
                        .constrainAs(documentRow) {
                            top.linkTo(subTitle.bottom, margin = 50.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically, // Allinea verticalmente rispetto al centro
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Aggiunge uno spazio di 8.dp tra gli elementi
                ) {
                    Text(
                        text = "CARICA DOCUMENTO IN FORMATO PDF",
                        fontSize = 12.sp,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )




                    Spacer(modifier = Modifier.width(8.dp))

                    LocalContext.current as ComponentActivity
                    var selectedDocument by remember { mutableStateOf<Uri?>(null) }
                    // Definisci il contratto per l'activity result
                    val getContent =
                        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                            // Gestisci l'URI dell'immagine selezionata
                            // Puoi eseguire ulteriori operazioni qui, come caricare l'immagine
                            uri?.let {
                                selectedDocument = it
                            }
                        }




                    ElevatedButton(
                        onClick = { getContent.launch("application/pdf") },
                    ) {
                        Text(
                            text = if (selectedDocument == null) "CARICA" else "CARICATO",
                            fontSize = 10.sp,
                        )
                    }
                }
                OutlinedTextField(
                    value = partitaIva,
                    onValueChange = {
                        partitaIva = it
                        isPartitaIvaValid = viewModel.isValidPartitaIva(it)
                    },
                    label = {
                        Text(
                            "Partita Iva",
                            color = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                                0xFF9B0404
                            ) else Color.Black
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_account_balance_24),
                            contentDescription = null,
                            tint = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                                0xFF9B0404
                            ) else Color.Gray,
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                            0xFF9B0404
                        ) else Color.Gray,
                        focusedBorderColor = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                            0xFF9B0404
                        ) else Color.Gray,
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (isPartitaIvaValid) R.drawable.baseline_done_24 else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) R.drawable.baseline_close_24 else R.drawable.empty),
                            contentDescription = null,
                            tint = if (isPartitaIvaValid) Color(0xFF0EA639) else if (!isPartitaIvaValid && partitaIva.isNotEmpty()) Color(
                                0xFF9B0404
                            ) else Color.Gray,
                            modifier = if (partitaIva.isEmpty()) Modifier.alpha(0f) else Modifier
                        )
                    },

                    modifier = Modifier
                        .constrainAs(partitaIvaTextField) {
                            top.linkTo(documentRow.bottom, margin = 10.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        }
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { nomeTitolareFocusRequester.requestFocus() }
                    ),
                    shape = RoundedCornerShape(15.dp),
                )

                Text(text = "DATI CONTO CORRENTE", textAlign = TextAlign.Center,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,
                    modifier = Modifier.constrainAs(bankAccountTitle) {
                        top.linkTo(partitaIvaTextField.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                OutlinedTextField(
                    value = nomeTitolare,
                    onValueChange = { nomeTitolare = it
                        isNomeTitolareValid = viewModel.isValidNomeTitolare(it)
                                    },
                    label = {
                        Text(
                            "Nome Titolare",
                            color = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Black
                        )
                    },

                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Gray,
                        focusedBorderColor = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Gray,
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_account_circle_24),
                            contentDescription = null,
                            tint = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Gray,
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (isNomeTitolareValid) R.drawable.baseline_done_24 else R.drawable.empty),
                            contentDescription = null,
                            tint = if (isNomeTitolareValid) Color(0xFF0EA639) else Color.Gray,
                            modifier = if (isNomeTitolareValid) Modifier.alpha(0f) else Modifier
                        )
                    },
                    modifier = Modifier
                        .constrainAs(ownerTextField) {
                            top.linkTo(bankAccountTitle.bottom, margin = 10.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        }
                        .padding(8.dp)
                        .focusRequester(nomeTitolareFocusRequester),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { bicSwiftCodeFocusReqeuster.requestFocus() }
                    ),
                    shape = RoundedCornerShape(15.dp),
                )


                OutlinedTextField(
                    value = codiceBicSwift,
                    onValueChange = {
                        codiceBicSwift = it
                        isCodiceBicSwiftValid = viewModel.isValidCodiceBicSwift(it)
                    },
                    label = {
                        Text(
                            "Codice BIC-SWIFT",
                            color = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                                0xFF9B0404
                            ) else Color.Black,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_assured_workload_24),
                            contentDescription = null,
                            tint = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                                0xFF9B0404
                            ) else Color.Gray,

                            )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                            0xFF9B0404
                        ) else Color.Gray,
                        focusedBorderColor = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                            0xFF9B0404
                        ) else Color.Gray,
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (isCodiceBicSwiftValid) R.drawable.baseline_done_24 else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) R.drawable.baseline_close_24 else R.drawable.empty),
                            contentDescription = null,
                            tint = if (isCodiceBicSwiftValid) Color(0xFF0EA639) else if (!isCodiceBicSwiftValid && codiceBicSwift.isNotEmpty()) Color(
                                0xFF9B0404
                            ) else Color.Gray,
                            modifier = if (codiceBicSwift.isEmpty()) Modifier.alpha(0f) else Modifier
                        )
                    },
                    modifier = Modifier
                        .constrainAs(bicSwiftCodeTextField) {
                            top.linkTo(ownerTextField.bottom, margin = 10.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        }
                        .padding(8.dp)
                        .focusRequester(bicSwiftCodeFocusReqeuster),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { ibanFocusRequest.requestFocus() }
                    ),
                    shape = RoundedCornerShape(15.dp),
                )


                OutlinedTextField(
                    value = iban,
                    onValueChange = {
                        iban = it
                        isIbanValid = viewModel.isValidIban(it)
                    },
                    label = {
                        Text(
                            "IBAN",
                            color = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                                0xFF9B0404
                            ) else Color.Black
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                            0xFF9B0404
                        ) else Color.Gray,
                        focusedBorderColor = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                            0xFF9B0404
                        ) else Color.Gray,
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_account_balance_wallet_24),
                            contentDescription = null,
                            tint = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                                0xFF9B0404
                            ) else Color.Gray,
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (isIbanValid) R.drawable.baseline_done_24 else if (!isIbanValid && iban.isNotEmpty()) R.drawable.baseline_close_24 else R.drawable.empty),
                            contentDescription = null,
                            tint = if (isIbanValid) Color(0xFF0EA639) else if (!isIbanValid && iban.isNotEmpty()) Color(
                                0xFF9B0404
                            ) else Color.Gray,
                            modifier = if (iban.isEmpty()) Modifier.alpha(0f) else Modifier
                        )
                    },

                    modifier = Modifier
                        .constrainAs(ibanTextField) {
                            top.linkTo(bicSwiftCodeTextField.bottom, margin = 10.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        }
                        .padding(8.dp)
                        .focusRequester(ibanFocusRequest),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(15.dp),
                )
                Row(
                    modifier = Modifier
                        .constrainAs(buttons) {
                            top.linkTo(ibanTextField.bottom, margin = 10.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        }
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Bottone Annulla
                    ElevatedButton(
                        onClick = {
                            currentPage.intValue = 2
                        },

                        colors = ButtonColors(
                            containerColor = Color(0xFF9b0404),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Black
                        )

                    ) {
                        Text(
                            text = "ANNULLA",
                            fontSize = 12.sp,
                        )
                    }

                    // Bottone Conferma
                    ElevatedButton(
                        enabled = viewModel.checkFieldsDatiVenditore(nomeTitolare, codiceBicSwift, partitaIva, iban),
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                viewModel.registraUtente(UtenteRegistrazione(username, RuoloUtente.VENDITORE, nome, cognome, viewModel.convertMillisToDate(state.selectedDateMillis!!).toString(), email, password, partitaIva, null, null, ContoCorrente(nomeTitolare, codiceBicSwift, iban)))
                            }
                            isDialogVisible.value = true
                        },
                        ) {
                        Text(
                            text = "CONFERMA",
                            fontSize = 12.sp,
                        )
                    }
                }
            }

        }

    }
    if (isDialogVisible.value) {
        Dialog(onDismissRequest = {}) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ACCOUNT CREATO!\n\n" +
                                "REGISTRAZIONE AVVENUTA CON SUCCESSO " +
                                "TORNA ALLA SCHERMATA DI LOGIN PER EFFETTUARE L'ACCESSO",
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = {
                        isDialogVisible.value = false
                        navController.navigate("SchermataAutenticazione")
                    }) {
                        Text(text = "ACCEDI", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}


@SuppressLint("NewApi")
@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataRegistrazione(navController)
    }
}


