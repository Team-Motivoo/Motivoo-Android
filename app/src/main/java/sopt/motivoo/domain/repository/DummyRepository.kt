package sopt.motivoo.domain.repository

import sopt.motivoo.data.model.request.DummyRequestData
import sopt.motivoo.domain.entity.dummy.DummyInfoList

interface DummyRepository {

    suspend fun postDummyData(dummyRequestData: DummyRequestData): Result<DummyInfoList>

}
