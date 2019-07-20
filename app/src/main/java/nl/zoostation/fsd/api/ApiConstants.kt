package nl.zoostation.fsd.api

import nl.zoostation.fsd.BuildConfig

object ApiConstants {

    const val BASE_URL = BuildConfig.BASE_URL

    // region Default request parameter values
    const val CLIENT_ID_VALUE = BuildConfig.CLIENT_ID
    const val CLIENT_SECRET_VALUE = BuildConfig.CLIENT_SECRET
    const val VERSION_VALUE = 20190425
    const val RADIUS_VALUE = 1000
    const val LIMIT_VALUE = 10
    // endregion

    // region Request parameter names
    const val PARAM_CLIENT_ID = "client_id"
    const val PARAM_CLIENT_SECRET = "client_secret"
    const val PARAM_VERSION = "v"
    const val PARAM_NEAR = "near"
    const val PARAM_RADUIS = "radius"
    const val PARAM_LIMIT = "limit"
    //endregion

    const val CACHE_SIZE = 10L * 1024 * 1024
    const val CONNECTION_TIMEOUT_SECONDS = 10L
}
