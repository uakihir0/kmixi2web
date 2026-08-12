package work.socialhub.kmixi2web.util

internal expect fun <T> toBlocking(block: suspend () -> T): T
