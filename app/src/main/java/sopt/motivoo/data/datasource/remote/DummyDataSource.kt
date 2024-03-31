package sopt.motivoo.data.datasource.remote

import sopt.motivoo.data.model.request.DummyRequestData
import sopt.motivoo.data.model.response.DummyResponseData
import sopt.motivoo.data.service.DummyService
import javax.inject.Inject

class DummyDataSource @Inject constructor(
    private val dummyService: DummyService,
) {

    suspend fun postDummyData(dummyRequestData: DummyRequestData): DummyResponseData =
        dummyService.postData(dummyRequestData)
}
