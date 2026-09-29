package com.pemmob.nadin.ui.screen

import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

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

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

import com.pemmob.nadin.R

import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubungiKamiScreen(navController: NavController?) {

    // B.1: Deklarasi Variabel State
    var emailText by remember {
        mutableStateOf(value = "")
    }

    var messageText by remember {
        mutableStateOf(value = "")
    }

    var problemType by rememberSaveable {
        mutableStateOf(value = "Pilih Tipe Pesan")
    }

    var isAgreed by rememberSaveable {
        mutableStateOf(value = false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(value = null)
    }

    // B.2: Deklarasi Status Validasi dan Coroutine
    val isEmailValid =
        emailText.contains(other = "@") && emailText.isNotBlank()

    val isMessageValid =
        messageText.length >= 10

    val isFormValid =
        isEmailValid &&
                isMessageValid &&
                isAgreed &&
                problemType != "Pilih Tipe Pesan"

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("Hubungi Kami")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController?.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.back_icon
                            ),
                            contentDescription = "Back Icon"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        // B.12: Pemanggilan StatelessFormHubungiKami
        StatelessFormHubungiKami(
            modifier = Modifier.padding(paddingValues),

            email = emailText,
            onEmailChange = {
                emailText = it
            },
            isEmailValid = isEmailValid,

            message = messageText,
            onMessageChange = {
                messageText = it
            },
            isMessageValid = isMessageValid,

            problemType = problemType,
            onProblemTypeChange = {
                problemType = it
            },

            isAgreed = isAgreed,
            onAgreedChange = {
                isAgreed = it
            },

            imageUri = imageUri,
            onImagePicked = {
                imageUri = it
            },

            isFormValid = isFormValid,

            onSubmit = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Pesan Terkirim!"
                    )
                }
            }
        )
    }
}


// B.3: Function StatelessFormHubungiKami
@Composable
fun StatelessFormHubungiKami(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    isEmailValid: Boolean,
    message: String,
    onMessageChange: (String) -> Unit,
    isMessageValid: Boolean,
    problemType: String,
    onProblemTypeChange: (String) -> Unit,
    isAgreed: Boolean,
    onAgreedChange: (Boolean) -> Unit,
    imageUri: Uri?,
    onImagePicked: (Uri?) -> Unit,
    isFormValid: Boolean,
    onSubmit: () -> Unit
) {

    // B.4: Deklarasi Variabel PhotoPicker
    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                onImagePicked(uri)
            }
        )

    var expanded by remember {
        mutableStateOf(value = false)
    }

    val options = listOf(
        "Pertanyaan",
        "Keluhan",
        "Saran"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hubungi Kami",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // B.8: OutlinedTextField Email
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = {
                Text("Email Anda")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(
                        id = R.drawable.mail_icon
                    ),
                    contentDescription = "Email",
                    modifier = Modifier.size(24.dp)
                )
            },
            isError = email.isNotEmpty() && !isEmailValid,
            supportingText = {
                if (email.isNotEmpty() && !isEmailValid) {
                    Text("Format Email Salah")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // B.9: Dropdown Tipe Pesan
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = problemType,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Tipe Pesan")
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            expanded = !expanded
                        }
                    ) {
                        Text(
                            text = if (expanded) "▲" else "▼"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = true
                    },
                shape = MaterialTheme.shapes.medium
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->

                    DropdownMenuItem(
                        text = {
                            Text(text = selectionOption)
                        },
                        onClick = {
                            onProblemTypeChange(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // OutlinedTextField Pesan
        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            label = {
                Text("Pesan")
            },
            isError = message.isNotEmpty() && !isMessageValid,
            supportingText = {
                if (message.isNotEmpty() && !isMessageValid) {
                    Text("Pesan minimal 10 karakter")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Tombol Unggah Bukti
        OutlinedButton(
            onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts
                            .PickVisualMedia.ImageOnly
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Unggah Bukti (Screenshot / Foto)")
        }

        // B.10: Preview Uri Gambar Terpilih
        val selectedImageUri = imageUri

        if (selectedImageUri != null) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(all = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "File terpilih: ${
                            selectedImageUri.lastPathSegment
                                ?: "Gambar dipilih"
                        }"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // Checkbox Syarat dan Ketentuan
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isAgreed,
                onCheckedChange = onAgreedChange
            )

            Text("Saya menyetujui syarat & ketentuan")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // B.11: Button Submit
        Button(
            onClick = onSubmit,
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.send_icon
                    ),
                    contentDescription = "Send"
                )

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Text(
                    text = "Kirim Pesan",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}