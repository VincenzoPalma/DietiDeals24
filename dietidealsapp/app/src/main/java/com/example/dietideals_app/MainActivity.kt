package com.example.dietideals_app

import com.example.dietideals_app.ui.theme.DietidealsappTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    var isLoginEnabled by remember {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Login"
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                isLoginEnabled = it.isNotBlank() && password.isNotBlank()
            },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { /* Handle next action if needed */ }
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isLoginEnabled = it.isNotBlank() && username.isNotBlank()
            },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isLoginEnabled) {
                        // Perform login action
                        performLogin(username, password)
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isLoginEnabled) {
                    // Perform login action
                    performLogin(username, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = isLoginEnabled
        ) {
            Text("Login")
        }
    }
}

fun performLogin(username: String, password: String) {
    // Placeholder function for the login action
    // You can implement your authentication logic here
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    DietidealsappTheme {
        LoginScreen()
    }
}
