package sopt.mottivoo.data.error

import retrofit2.HttpException
import sopt.mottivoo.domain.entity.error.ResponseHandler
import sopt.mottivoo.domain.error.UserErrorHandler
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
