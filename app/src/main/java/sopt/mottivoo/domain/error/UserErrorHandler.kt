package sopt.mottivoo.domain.error

import sopt.mottivoo.domain.entity.error.ResponseHandler

interface UserErrorHandler {
    fun <T> handleUserError(throwable: Throwable?, data: T?): ResponseHandler<T?>
}
