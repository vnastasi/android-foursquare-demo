package nl.zoostation.fsd.exception

class ApiException(
    val httpStatusCode: Int,
    override val message: String
) : Exception(message)