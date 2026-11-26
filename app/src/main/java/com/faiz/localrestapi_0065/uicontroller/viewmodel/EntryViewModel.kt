package com.faiz.localrestapi_0065.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.faiz.localrestapi_0065.modeldata.DataSiswa
import com.faiz.localrestapi_0065.repositori.RepositoryDataSiswa
import retrofit2.Response

class EntryViewModel(private val repositoryDataSiswa:
                     RepositoryDataSiswa): ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStatesSiswa())
        private  set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailsSiswa = uiStateSiswa
        .detailsSiswa ): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon
                .isNotBlank()
        }
    }

    fun updateUiState(detailsSiswa: DetailsSiswa) {
        uiStateSiswa =
            UIStatesSiswa(detailsSiswa = detailsSiswa, isEntryValid =
                validasiInput(detailsSiswa))
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun addSiswa() {
        if (validasiInput()) {
            val sip:Response<Void> =repositoryDataSiswa.postDataSiswa(
                uiStateSiswa.detailsSiswa.toDataSiswa())
            if (sip.isSuccessful){
                println("Sukses Tambah Data : ${sip.message()}")
            }else{
                println("Gagal tambah data : ${sip.errorBody()}")
            }
        }
    }
}

data class UIStatesSiswa(
    val detailsSiswa: DetailsSiswa = DetailsSiswa(),
    val isEntryValid: Boolean = false
)

data class DetailsSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = "",
)
fun DetailsSiswa.toDataSiswa(): DataSiswa = DataSiswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

fun DataSiswa.toUIStatesSiswa(isEntryValid: Boolean = false):
        UIStatesSiswa = UIStatesSiswa(
    detailsSiswa = this.toDetailSiswa(),
    isEntryValid = isEntryValid
)

fun DataSiswa.toDetailSiswa(): DetailsSiswa = DetailsSiswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)