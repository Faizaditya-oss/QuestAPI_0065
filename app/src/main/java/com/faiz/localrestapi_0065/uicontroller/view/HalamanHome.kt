package com.faiz.localrestapi_0065.uicontroller.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faiz.localrestapi_0065.R
import com.faiz.localrestapi_0065.modeldata.DataSiswa
import com.faiz.localrestapi_0065.uicontroller.route.DestinasiHome
import com.faiz.localrestapi_0065.viewmodel.HomeViewModel
import com.faiz.localrestapi_0065.viewmodel.PenyediaViewModel
import com.faiz.localrestapi_0065.viewmodel.StatusUiSiswa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(id = DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.entry_siswa)
                )
            }
        }
    ) { innerPadding ->
        HomeBody(
            statusUiSiswa = viewModel.listSiswa,
            onRetryClick = { viewModel.loadSiswa() },
            onSiswaClick = onDetailClick,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun HomeBody(
    statusUiSiswa: StatusUiSiswa,
    onRetryClick: () -> Unit,
    onSiswaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (statusUiSiswa) {
        is StatusUiSiswa.Loading -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is StatusUiSiswa.Success -> {
            if (statusUiSiswa.siswa.isEmpty()) {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada data siswa",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                DaftarSiswa(
                    listSiswa = statusUiSiswa.siswa,
                    onSiswaClick = onSiswaClick,
                    contentPadding = PaddingValues(16.dp),
                    modifier = modifier
                )
            }
        }

        is StatusUiSiswa.Error -> {
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Gagal memuat data siswa",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onRetryClick) {
                    Text(text = "Coba Lagi")
                }
            }
        }
    }
}

@Composable
fun DaftarSiswa(
    listSiswa: List<DataSiswa>,
    onSiswaClick: (Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = listSiswa, key = { it.id }) { siswa ->
            CardSiswa(
                siswa = siswa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSiswaClick(siswa.id) }
            )
        }
    }
}

@Composable
fun CardSiswa(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = siswa.nama,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = siswa.alamat,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = siswa.telpon,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}