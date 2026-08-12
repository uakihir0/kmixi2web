package work.socialhub.kmixi2web

class Mixi2WebException(
    message: String,
    cause: Throwable? = null,
    val status: Int? = null,
    val responseBody: ByteArray? = null,
) : RuntimeException(message, cause)
