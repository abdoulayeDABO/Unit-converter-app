package com.example.unitConverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitConverter.ui.theme.UnitConverterTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(24.dp)
                    ) {
                        UnitConverter()
                    }
                }
            }
        }
    }
}




@Composable
fun UnitConverter() {
    var input by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Kilogrammes") }
    var output by remember { mutableStateOf("") }
    var outputUnit by remember { mutableStateOf("Livre") }
    var inputUnitDropdownExpanded by remember { mutableStateOf(false) }
    var outputUnitDropdownExpanded by remember { mutableStateOf(false) }
//    var converionFactor by remember { mutableStateOf(0.0) }

    fun convert (): Double {
        val inputValue = input.toDoubleOrNull() ?: 0.0
        val result = when (inputUnit) {
            "Kilogrammes" ->
                when (outputUnit) {
                    "Kilogrammes" -> inputValue * 1.0
                    "Livre" -> inputValue * 2.20462
                    "Grammes" -> inputValue * 1000.0
                    else -> 0.0
                }
            "Livre" -> when (outputUnit) {
                "Kilogrammes" -> inputValue * 0.453592
                "Livre" -> inputValue * 1.0
                "Grammes" -> inputValue * 453.592
                else -> 0.0
            }
            "Grammes" -> when (outputUnit) {
                "Kilogrammes" -> inputValue * 0.001
                "Livre" -> inputValue * 0.00220462
                "Grammes" -> inputValue * 1.0
                else -> 0.0
            }
            else -> 0.0
        }
        return result
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().
        padding(top = 50.dp)
    ) {
        Text(
            text = "Convertisseur d'unités",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                output = convert().toString()
            },
            placeholder = { Text("Entrez une valeur") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Box(){
                Button(
                    onClick = {
                        inputUnitDropdownExpanded = true
                    },
                    Modifier.width(180.dp)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = inputUnit)
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }

                }
                DropdownMenu(
                    expanded = inputUnitDropdownExpanded,
                    onDismissRequest = {
                        inputUnitDropdownExpanded = false
                    }
                ) {
                    DropdownMenuItem(text = { Text("Kilogrammes") }, onClick = {
                        inputUnit = "Kilogrammes"
                        inputUnitDropdownExpanded = false
                        output = convert().toString()
                    })
                    DropdownMenuItem(text = { Text("Livre") }, onClick = {
                        inputUnit = "Livre"
                        inputUnitDropdownExpanded = false
                        output = convert().toString()
                    })
                    DropdownMenuItem(text = { Text("Grammes") }, onClick = {
                        inputUnit = "Grammes"
                        inputUnitDropdownExpanded = false
                        output = convert().toString()
                    })
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(){
                Button(
                    onClick = {
                        outputUnitDropdownExpanded = true
                    },
                    Modifier.width(180.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = outputUnit)
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
                DropdownMenu(
                    expanded = outputUnitDropdownExpanded,
                    onDismissRequest = {
                        outputUnitDropdownExpanded = false
                    }
                ) {
                    DropdownMenuItem(text = { Text("Kilogrammes") }, onClick = {
                        outputUnit = "Kilogrammes"
                        outputUnitDropdownExpanded = false
                        output = convert().toString()
                    })
                    DropdownMenuItem(text = { Text("Livre") }, onClick = {
                        outputUnit = "Livre"
                        outputUnitDropdownExpanded = false
                        output = convert().toString()
                    })
                    DropdownMenuItem(text = { Text("Grammes") }, onClick = {
                        outputUnit = "Grammes"
                        outputUnitDropdownExpanded = false
                        output = convert().toString()
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Résultat : ${(if (output.isNotEmpty()) output else "0.0")} $outputUnit",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )



    }

}


@Composable
fun LoginForm() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Titre
        Text(
            text = "Connexion",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Connectez-vous à votre compte",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Champ Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("exemple@email.com") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email"
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ Mot de passe
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            placeholder = { Text("Votre mot de passe") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password"
                )
            },
            trailingIcon = {
                TextButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Text(
                        text = if (passwordVisible) "Masquer" else "Afficher",
                        fontSize = 12.sp
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Bouton de connexion avec loader
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    isLoading = true
                }
            },
            enabled = !isLoading,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Connexion en cours...",
                    fontSize = 16.sp
                )
            } else {
                Text(
                    text = "Se connecter",
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lien mot de passe oublié
        TextButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Mot de passe oublié ?")
        }
    }

    // Simulation d'authentification
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2000)
            isLoading = false
            dialogMessage = "Connexion réussie pour $email"
            showDialog = true
        }
    }

    // Dialog simple
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Résultat") },
            text = { Text(dialogMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        email = ""
                        password = ""
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}