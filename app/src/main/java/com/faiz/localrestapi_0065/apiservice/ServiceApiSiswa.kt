
package com.faiz.localrestapi_0065.apiservice

import com.faiz.localrestapi_0065.modeldata.DataSiswa

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceApiSiswa {

    @GET("bacaTeman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun postSiswa(
        @Body dataSiswa: DataSiswa
    ): Response<Void>
}

