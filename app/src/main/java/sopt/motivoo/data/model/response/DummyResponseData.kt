package sopt.motivoo.data.model.response

import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.dummy.DummyInfoList

@Serializable
data class DummyResponseData(
    val status: Int,
    val message: String,
    val data: List<DummyData>,
) {
    @Serializable
    data class DummyData(
        val id: Int,
        val name: String,
        val email: String,
    )

    fun toDummyInfo() = DummyInfoList(
        dummyInfo = data.map { dummy ->
            DummyInfoList.DummyInfo(
                id = dummy.id,
                name = dummy.name,
                email = dummy.email,
            )
        }
    )
}
