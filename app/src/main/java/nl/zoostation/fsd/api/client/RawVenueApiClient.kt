package nl.zoostation.fsd.api.client

import io.reactivex.Single
import nl.zoostation.fsd.api.ApiConstants
import nl.zoostation.fsd.api.model.ResponseWrapper
import nl.zoostation.fsd.api.model.VenueDetailsWrapper
import nl.zoostation.fsd.api.model.VenueSearchWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawVenueApiClient {

    @GET("/v2/venues/search")
    fun searchVenues(
        @Query(ApiConstants.PARAM_NEAR) near: String,
        @Query(ApiConstants.PARAM_RADUIS) radius: Int,
        @Query(ApiConstants.PARAM_LIMIT) limit: Int
    ): Single<Response<ResponseWrapper<VenueSearchWrapper>>>

    @GET("/v2/venues/{id}")
    fun getDetails(@Path("id") venueId: String): Single<Response<ResponseWrapper<VenueDetailsWrapper>>>
}