package sopt.mottivoo.domain.repository

import sopt.mottivoo.data.model.request.DummyRequestData
import sopt.mottivoo.domain.entity.dummy.DummyInfoList

interface DummyRepository {

    suspend fun postDummyData(dummyRequestData: DummyRequestData): Result<DummyInfoList>
}
