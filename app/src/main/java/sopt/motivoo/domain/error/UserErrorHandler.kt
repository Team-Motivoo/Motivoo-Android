package sopt.motivoo.domain.error

import sopt.motivoo.domain.entity.error.ResponseHandler

interface UserErrorHandler {
    fun <T> handleUserError(throwable: Throwable?, data: T?): ResponseHandler<T?>
}