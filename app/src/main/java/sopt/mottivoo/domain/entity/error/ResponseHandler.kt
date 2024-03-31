package sopt.mottivoo.domain.entity.error

data class ResponseHandler<T>(
    val code: Int?,
    val data: T?
)
