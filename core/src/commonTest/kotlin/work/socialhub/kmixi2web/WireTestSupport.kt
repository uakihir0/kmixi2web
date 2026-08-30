package work.socialhub.kmixi2web

import kotlinx.serialization.protobuf.ProtoBuf

/** Encoder configured exactly like [work.socialhub.kmixi2web.internal.MercuryClient]. */
internal val wireProto = ProtoBuf {
    encodeDefaults = false
}

internal fun bytes(vararg values: Int): ByteArray {
    return ByteArray(values.size) { index -> values[index].toByte() }
}
