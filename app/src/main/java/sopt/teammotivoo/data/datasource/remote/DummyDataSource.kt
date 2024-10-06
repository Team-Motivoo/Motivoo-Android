package sopt.teammotivoo.data.datasource.remote

import sopt.teammotivoo.data.model.request.DummyRequestData
import sopt.teammotivoo.data.model.response.DummyResponseData
import sopt.teammotivoo.data.service.DummyService
import javax.inject.Inject

class DummyDataSource @Inject constructor(
    private val dummyService: DummyService,
) {

    suspend fun postDummyData(dummyRequestData: DummyRequestData): DummyResponseData =
        dummyService.postData(dummyRequestData)
}
