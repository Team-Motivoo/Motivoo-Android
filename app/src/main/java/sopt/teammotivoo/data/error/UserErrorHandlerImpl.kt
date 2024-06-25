package sopt.teammotivoo.data.error

import retrofit2.HttpException
import sopt.teammotivoo.domain.entity.error.ResponseHandler
import sopt.teammotivoo.domain.error.UserErrorHandler
import javax.inject.Inject

class UserErrorHandlerImpl @Inject constructor() : UserErrorHandler {
    override fun <T> handleUserError(throwable: Throwable?, data: T?): ResponseHandler<T?> =
        when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    412 -> {
                        ResponseHandler(throwable.code(), data)
                    }

                    else -> ResponseHandler(throwable.code(), data)
                }
            }

            else -> ResponseHandler(null, null)
        }
}
