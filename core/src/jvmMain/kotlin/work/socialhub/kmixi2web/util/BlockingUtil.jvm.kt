package work.socialhub.kmixi2web.util

import kotlinx.coroutines.runBlocking

internal actual fun <T> toBlocking(block: suspend () -> T): T = runBlocking {
    block()
}
