package com.poc.customSnackbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poc.customSnackbar.ui.theme.CustomSnackbarTheme
import com.poc.snackBarSample.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomSnackbarTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                val snackbarHostState = remember { SnackbarHostState() }
                var showSnackBarMessage by remember { mutableStateOf(false) }

                Scaffold(
                    snackbarHost = {
                        // reuse default SnackbarHost to have default animation and timing handling
                        Extracted(snackbarHostState)
                    }
                ) {
                    LaunchedEffect(key1 = showSnackBarMessage) {
                        snackbarHostState.showSnackbar(
                            withDismissAction = true,
                            duration = SnackbarDuration.Indefinite,
                            message = "Supporting text lorem ipsumwraps dolor"
                        )
                    }
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Greeting(it,"SnackBar Sample")
                            Button(onClick = {showSnackBarMessage = !showSnackBarMessage}) {
                                Text("Show Snackbar")
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Extracted(snackbarHostState: SnackbarHostState) {
        SnackbarHost(snackbarHostState) { data ->
            // custom snackbar with the custom border
            Snackbar(
                modifier = Modifier.padding(20.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = Color(0xff4C5870),
                dismissActionContentColor = Color(0xff4C5870),
                actionContentColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium,
                dismissAction = {
                    IconButton(
                        onClick = { snackbarHostState.currentSnackbarData?.dismiss() },
                        content = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Dismiss"
                            )
                        }
                    )
                },
                action = {
                         TextButton(onClick = { }) {
                             Text("Action")
                         }
                },
                content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painterResource(id = R.drawable.leadingicon),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Column(Modifier.padding(start = 12.dp)) {
                                Text(text = "HeadLine", fontWeight = FontWeight(700))
                                Text(text = data.visuals.message)
                            }
                    }
                }
            )
        }
    }
}

@Composable
fun Greeting(paddingValues: PaddingValues, name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name!",
        modifier = modifier.padding(paddingValues)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomSnackbarTheme {
        Scaffold {
            Greeting(it,"Android")
        }
    }
}