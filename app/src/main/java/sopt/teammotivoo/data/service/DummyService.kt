package sopt.teammotivoo.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import sopt.teammotivoo.data.model.request.DummyRequestData
import sopt.teammotivoo.data.model.response.DummyResponseData

interface DummyService {

    @POST("api")
    suspend fun postData(
        @Body requestData: DummyRequestData
    ): DummyResponseData
}
