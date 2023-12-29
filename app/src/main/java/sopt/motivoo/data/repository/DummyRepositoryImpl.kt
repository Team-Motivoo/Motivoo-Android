package sopt.motivoo.data.repository

import sopt.motivoo.data.datasource.remote.DummyDataSource
import sopt.motivoo.data.model.request.DummyRequestData
import sopt.motivoo.domain.entity.dummy.DummyInfoList
import sopt.motivoo.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyDataSource: DummyDataSource,
) : DummyRepository {

    override suspend fun postDummyData(dummyRequestData: DummyRequestData): Result<DummyInfoList> =
        runCatching { dummyDataSource.postDummyData(dummyRequestData).toDummyInfo() }

}
