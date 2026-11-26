package com.faiz.localrestapi_0065.repositori

import com.faiz.localrestapi_0065.apiservice.ServiceApiSiswa
import com.faiz.localrestapi_0065.modeldata.DataSiswa

interface RepositoryDataSiswa {
    suspend fun getDataSiswa() : List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa) :retrofit2.Response
    <Void>
}

class JaringanRepositoryDataSiswa(
    private  val serviceApiSiswa: ServiceApiSiswa
):RepositoryDataSiswa{
    override suspend fun getDataSiswa() : List<DataSiswa> =
        serviceApiSiswa.getSiswa()
    override suspend fun postDataSiswa(dataSiswa: DataSiswa):retrofit2
    .Response<Void> = serviceApiSiswa.postSiswa(dataSiswa)
}