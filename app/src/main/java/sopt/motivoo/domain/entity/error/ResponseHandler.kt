package sopt.motivoo.domain.entity.error

data class ResponseHandler<T>(
    val code: Int?,
    val data: T?
)
