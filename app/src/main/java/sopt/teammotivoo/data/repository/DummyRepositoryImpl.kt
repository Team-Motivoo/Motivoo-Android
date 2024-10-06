package sopt.teammotivoo.data.repository

import sopt.teammotivoo.data.datasource.remote.DummyDataSource
import sopt.teammotivoo.data.model.request.DummyRequestData
import sopt.teammotivoo.domain.entity.dummy.DummyInfoList
import sopt.teammotivoo.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyDataSource: DummyDataSource,
) : DummyRepository {

    override suspend fun postDummyData(dummyRequestData: DummyRequestData): Result<DummyInfoList> =
        runCatching { dummyDataSource.postDummyData(dummyRequestData).toDummyInfo() }
}
