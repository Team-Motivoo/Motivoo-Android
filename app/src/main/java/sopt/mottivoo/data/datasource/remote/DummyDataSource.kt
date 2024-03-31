package sopt.mottivoo.data.datasource.remote

import sopt.mottivoo.data.model.request.DummyRequestData
import sopt.mottivoo.data.model.response.DummyResponseData
import sopt.mottivoo.data.service.DummyService
import javax.inject.Inject

class DummyDataSource @Inject constructor(
    private val dummyService: DummyService,
) {

    suspend fun postDummyData(dummyRequestData: DummyRequestData): DummyResponseData =
        dummyService.postData(dummyRequestData)
}
