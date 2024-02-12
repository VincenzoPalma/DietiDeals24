package com.example.dietideals_app.view


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietideals_app.R
import com.example.dietideals_app.ui.theme.DietidealsappTheme

class PaginaModificaProfiloUtente : ComponentActivity() {
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

                    }
                }
            }
        }

    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataModificaProfilo(navController: NavController) {
    var shortBio by remember { mutableStateOf("") }
    var sitoWeb by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var instagramLink by remember { mutableStateOf("") }
    var facebookLink by remember { mutableStateOf("") }
    var twitterLink by remember { mutableStateOf("") }
    val sitoWebFocusRequester = remember { FocusRequester() }
    val addressFocusRequester = remember { FocusRequester() }
    val facebookLinkFocusRequester = remember { FocusRequester() }
    val twitterLinkFocusRequester = remember { FocusRequester() }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (background) = createRefs()
        Box(
            modifier = Modifier
                .constrainAs(background) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(Color.White)
        ) {
            TopAppBar(modifier = Modifier.align(Alignment.TopCenter), title = {
                Text(
                    text = "PROFILO",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold, // Imposta il grassetto
                    fontSize = 40.sp
                ) // Imposta la dimensione del testo)
            },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("SchermataProfiloUtente")


                            }
                            .size(35.dp)
                    )
                }, colors = TopAppBarColors(
                    containerColor = (MaterialTheme.colorScheme.primary),
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
                actions = {

                }
            )
            val screenWidth = LocalDensity.current.run {
                LocalConfiguration.current.screenWidthDp.dp
            }
            Box(
                modifier = Modifier
                    .offset(x = (screenWidth / 2) - 40.dp, y = 70.dp)
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            ) {
                // Immagine all'interno della Box circolare
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Rimpiazza con la tua immagine
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )

            }
            Text(
                text = "MARIO ROSSI",
                modifier = Modifier
                    .offset(y = 150.dp)

                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold, // Imposta il grassetto
            )
            Text(
                text = "@mariorossi",
                modifier = Modifier
                    .offset(y = 180.dp)

                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.Gray// Imposta il grassetto
            )
            Text(
                text = "Short Bio:",
                modifier = Modifier
                    .offset(y = 200.dp)

                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold

            )

            //.offset(y = 235.dp)
            OutlinedTextField(value = shortBio, onValueChange = { shortBio = it },
                shape = RoundedCornerShape(15.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { sitoWebFocusRequester.requestFocus() }
                ),
                modifier = Modifier
                    .height(150.dp)
                    .offset(y = 235.dp)
                    .fillMaxWidth()
                    .padding(8.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 400.dp)


                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sito Web:",
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                LocalContext.current

                rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
                    // Handle the result if needed
                }

                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp
                        )
                    ) {
                        append("www.mariorossi.com")
                        addStringAnnotation("URL", "https://www.example.com", 0, length)
                    }
                }
                Spacer(modifier = Modifier.width(7.dp))

                OutlinedTextField(value = sitoWeb, onValueChange = { sitoWeb = it },
                    shape = RoundedCornerShape(15.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .focusRequester(sitoWebFocusRequester)
                        .height(50.dp),
                    keyboardActions = KeyboardActions(
                        onNext = { addressFocusRequester.requestFocus() }
                    ))


            }



            @Composable
            fun IconWithText(iconId: Int, text: String, route: String, color: Color) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(2.dp)
                ) {
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { navController.navigate(route) },
                        tint = color

                    )

                    Text(
                        text = text,
                        fontSize = 15.sp,
                        color = color
                    )//
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 480.dp)


            ) {
                Spacer(
                    modifier = Modifier
                        .width(20.dp)

                )
                IconWithText(
                    iconId = R.drawable.instagramicon,
                    text = "Instagram",
                    "",
                    Color.Black
                )
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                IconWithText(
                    iconId = R.drawable.facebookicon,
                    text = "Facebook",
                    "", Color.Black
                )
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                IconWithText(
                    iconId = R.drawable.twittericon,
                    text = "Twitter",
                    "", Color.Black
                )
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
            }
            var isDialogVisible by remember { mutableStateOf(false) }
            ElevatedButton(
                onClick = { isDialogVisible = true }, modifier = Modifier
                    .offset(x = 10.dp, y = 550.dp)
            ) {
                Text(text = "Modifica Account Social")


            }
            if (isDialogVisible) {
                Dialog(onDismissRequest = { isDialogVisible = false }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Instagram",

                                    )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.instagramicon),
                                    contentDescription = "iconaInstagram"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(value = instagramLink,
                                    onValueChange = { instagramLink = it },
                                    shape = RoundedCornerShape(15.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next
                                    ),
                                    modifier = Modifier
                                        .height(20.dp),
                                    keyboardActions = KeyboardActions(
                                        onNext = { facebookLinkFocusRequester.requestFocus() }
                                    ))
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Facebook",

                                    )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.facebookicon),
                                    contentDescription = "iconaFacebook"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(value = facebookLink,
                                    onValueChange = { facebookLink = it },
                                    shape = RoundedCornerShape(15.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next
                                    ),
                                    modifier = Modifier
                                        .height(20.dp)
                                        .focusRequester(facebookLinkFocusRequester),
                                    keyboardActions = KeyboardActions(
                                        onNext = { twitterLinkFocusRequester.requestFocus() }
                                    ))
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Twitter",

                                    )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.twittericon),
                                    contentDescription = "iconaInstagram"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(
                                    value = twitterLink, onValueChange = { twitterLink = it },
                                    shape = RoundedCornerShape(15.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done
                                    ),
                                    modifier = Modifier
                                        .height(20.dp)
                                        .focusRequester(twitterLinkFocusRequester),
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {


                                TextButton(onClick = { isDialogVisible = false }) {
                                    Text("OK")
                                }
                            }
                        }

                    }

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 600.dp)

                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Indirizzo:",
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    value = address, onValueChange = { address = it },
                    shape = RoundedCornerShape(15.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ), modifier = Modifier
                        .focusRequester(addressFocusRequester)
                        .height(50.dp)
                )


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 660.dp)

                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.End

            ) {
                ElevatedButton(
                    onClick = { navController.navigate("SchermataProfiloUtente") },
                    modifier = Modifier

                ) {
                    Text(text = "Conferma")

                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSchermataModificaProfilo() {
    DietidealsappTheme {
        val navController = rememberNavController()
        SchermataModificaProfilo(navController)
    }
}
