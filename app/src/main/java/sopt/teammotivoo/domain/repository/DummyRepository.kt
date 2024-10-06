package sopt.teammotivoo.domain.repository

import sopt.teammotivoo.data.model.request.DummyRequestData
import sopt.teammotivoo.domain.entity.dummy.DummyInfoList

interface DummyRepository {

    suspend fun postDummyData(dummyRequestData: DummyRequestData): Result<DummyInfoList>
}
