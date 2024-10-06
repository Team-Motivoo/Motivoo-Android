package sopt.teammotivoo.domain.error

import sopt.teammotivoo.domain.entity.error.ResponseHandler

interface UserErrorHandler {
    fun <T> handleUserError(throwable: Throwable?, data: T?): ResponseHandler<T?>
}
