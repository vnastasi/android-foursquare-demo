package nl.zoostation.fsd.api.client.impl

import io.reactivex.functions.Function
import nl.zoostation.fsd.exception.ApiException
import retrofit2.Response

class ApiResponseMapper<T> : Function<Response<T>, T> {

    override fun apply(response: Response<T>): T {
        return if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("API response body was null")
        } else {
            throw ApiException(response.code(), response.message())
        }
    }
}
