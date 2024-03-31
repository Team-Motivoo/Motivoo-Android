package sopt.mottivoo.data.repository

import sopt.mottivoo.data.datasource.remote.DummyDataSource
import sopt.mottivoo.data.model.request.DummyRequestData
import sopt.mottivoo.domain.entity.dummy.DummyInfoList
import sopt.mottivoo.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyDataSource: DummyDataSource,
) : DummyRepository {

    override suspend fun postDummyData(dummyRequestData: DummyRequestData): Result<DummyInfoList> =
        runCatching { dummyDataSource.postDummyData(dummyRequestData).toDummyInfo() }
}
