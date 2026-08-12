package work.socialhub.kmixi2web.util

internal actual fun <T> toBlocking(block: suspend () -> T): T {
    throw UnsupportedOperationException("Blocking calls are not supported on JavaScript.")
}
