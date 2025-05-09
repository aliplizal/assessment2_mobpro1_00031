package com.aliplizal607062300031.assessment2.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aliplizal607062300031.assessment2.R
import com.aliplizal607062300031.assessment2.database.BukuDb
import com.aliplizal607062300031.assessment2.ui.theme.Assessment2Theme
import com.aliplizal607062300031.assessment2.util.ViewModelFactory

const val KEY_ID_BUKU = "idBuku"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = BukuDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var judul by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getBuku(id) ?: return@LaunchedEffect
        judul = data.judul
        kategori = data.kategori
        status = data.status
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(
                        text = if (id == null) stringResource(R.string.tambah_buku)
                        else stringResource(R.string.edit_buku)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (judul.isEmpty() || kategori.isEmpty() || status.isEmpty()) {
                            Toast.makeText(context, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insert(judul, kategori, status)
                        } else {
                            viewModel.update(id, judul, kategori, status)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormBuku(
            judul = judul,
            onJudulChange = { judul = it },
            kategori = kategori,
            onKategoriChange = { kategori = it },
            status = status,
            onStatusChange = { status = it },
            modifier = Modifier.padding(padding)
        )
    }

    if (id != null && showDialog) {
        DisplayAlertDialog(
            onDismissRequest = { showDialog = false }) {
            showDialog = false
            viewModel.delete(id)
            navController.popBackStack()
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormBuku(
    judul: String, onJudulChange: (String) -> Unit,
    kategori: String, onKategoriChange: (String) -> Unit,
    status: String, onStatusChange: (String) -> Unit,
    modifier: Modifier
) {
    val kategoriList = listOf("Novel", "Dongeng", "Religius", "Komik", "Self Improvement", "Politik", "Lain-lain")
    val statusList = listOf("Sudah Baca", "Dalam Proses", "Belum Baca")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = judul,
            onValueChange = onJudulChange,
            label = { Text("Judul") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Text("Pilih Kategori:", style = MaterialTheme.typography.bodyMedium)
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                kategoriList.forEach { kategoriItem ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onKategoriChange(kategoriItem) }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = (kategori == kategoriItem),
                            onClick = { onKategoriChange(kategoriItem) }
                        )
                        Text(
                            text = kategoriItem,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        Text("Pilih Status:", style = MaterialTheme.typography.bodyMedium)
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                statusList.forEach { statusiItem ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onStatusChange(statusiItem) }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = (status == statusiItem),
                            onClick = { onKategoriChange(statusiItem) }
                        )
                        Text(
                            text = statusiItem,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailMahasiswaScreenPreview() {
    Assessment2Theme {
        DetailScreen(rememberNavController())
    }
}