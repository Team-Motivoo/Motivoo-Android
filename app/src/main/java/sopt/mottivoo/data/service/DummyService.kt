package sopt.mottivoo.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import sopt.mottivoo.data.model.request.DummyRequestData
import sopt.mottivoo.data.model.response.DummyResponseData

interface DummyService {

    @POST("api")
    suspend fun postData(
        @Body requestData: DummyRequestData
    ): DummyResponseData
}
