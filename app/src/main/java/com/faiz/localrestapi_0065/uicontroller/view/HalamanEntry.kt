package com.faiz.localrestapi_0065.uicontroller.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faiz.localrestapi_0065.R
import com.faiz.localrestapi_0065.uicontroller.viewmodel.DetailSiswa
import com.faiz.localrestapi_0065.uicontroller.viewmodel.EntryViewModel
import com.faiz.localrestapi_0065.uicontroller.viewmodel.PenyediaViewModel
import com.faiz.localrestapi_0065.uicontroller.viewmodel.UIStateSiswa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SiswaTopAppBar(
                title = stringResource(id = R.string.entry_siswa),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntrySiswaBody(
            uiStateSiswa = viewModel.uiStateSiswa,
            isSaving = viewModel.isSaving,
            errorMessage = viewModel.errorMessage,
            onSiswaValueChange = viewModel::updateUiState,
            onSaveClick = {
                viewModel.addSiswa {
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntrySiswaBody(
    uiStateSiswa: UIStateSiswa,
    isSaving: Boolean,
    errorMessage: String?,
    onSiswaValueChange: (DetailSiswa) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FormInputSiswa(
            detailSiswa = uiStateSiswa.detailSiswa,
            onValueChange = onSiswaValueChange,
            enabled = !isSaving,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateSiswa.isEntryValid && !isSaving,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.loading))
            } else {
                Text(text = stringResource(id = R.string.btn_submit))
            }
        }
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun FormInputSiswa(
    detailSiswa: DetailSiswa,
    onValueChange: (DetailSiswa) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = detailSiswa.nama,
            onValueChange = { onValueChange(detailSiswa.copy(nama = it)) },
            label = { Text(stringResource(id = R.string.nama)) },
            enabled = enabled,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = detailSiswa.alamat,
            onValueChange = { onValueChange(detailSiswa.copy(alamat = it)) },
            label = { Text(stringResource(id = R.string.alamat)) },
            enabled = enabled,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = detailSiswa.telpon,
            onValueChange = { onValueChange(detailSiswa.copy(telpon = it)) },
            label = { Text(stringResource(id = R.string.telpon)) },
            enabled = enabled,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}