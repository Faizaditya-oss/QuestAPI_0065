package com.faiz.localrestapi_0065.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faiz.localrestapi_0065.modeldata.DataSiswa
import com.faiz.localrestapi_0065.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class DetailSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

class EntryViewModel(
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    var isSaving by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private fun validasiInput(data: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return data.nama.isNotBlank() &&
                data.alamat.isNotBlank() &&
                data.telpon.isNotBlank()
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
        if (errorMessage != null) {
            errorMessage = null
        }
    }

    fun addSiswa(onSuccess: () -> Unit) {
        if (!validasiInput()) {
            uiStateSiswa = uiStateSiswa.copy(isEntryValid = false)
            errorMessage = "Lengkapi semua field terlebih dahulu"
            return
        }

        viewModelScope.launch {
            isSaving = true
            errorMessage = null

            try {
                val data = uiStateSiswa.detailSiswa.copy(
                    nama = uiStateSiswa.detailSiswa.nama.trim(),
                    alamat = uiStateSiswa.detailSiswa.alamat.trim(),
                    telpon = uiStateSiswa.detailSiswa.telpon.trim()
                )
                val siswa = DataSiswa(
                    id = data.id,
                    nama = data.nama,
                    alamat = data.alamat,
                    telpon = data.telpon
                )

                val response = repositoryDataSiswa.postDataSiswa(siswa)

                if (response.isSuccessful) {
                    uiStateSiswa = UIStateSiswa()
                    onSuccess()
                } else {
                    errorMessage = "Gagal menyimpan data (HTTP ${response.code()})"
                }
            } catch (e: IOException) {
                errorMessage = "Tidak dapat terhubung ke server XAMPP (${e.localizedMessage ?: "IOException"})"
            } catch (e: HttpException) {
                errorMessage = "Kesalahan server (HTTP ${e.code()})"
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Terjadi kesalahan saat menyimpan data"
            } finally {
                isSaving = false
            }
        }
    }
}