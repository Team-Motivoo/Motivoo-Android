package sopt.motivoo.domain.entity.dummy

data class DummyInfoList(
    val dummyInfo: List<DummyInfo>,
) {
    data class DummyInfo(
        val id: Int,
        val name: String,
        val email: String
    )
}
