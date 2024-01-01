package sopt.motivoo.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import sopt.motivoo.data.model.request.DummyRequestData
import sopt.motivoo.data.model.response.DummyResponseData

interface DummyService {

    @POST("api")
    suspend fun postData(
        @Body requestData: DummyRequestData
    ): DummyResponseData
}
