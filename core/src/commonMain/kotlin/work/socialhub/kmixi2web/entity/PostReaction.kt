package work.socialhub.kmixi2web.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.js.JsExport

@Serializable(with = PostReactionTypeSerializer::class)
@JsExport
enum class PostReactionType(
    val code: Int,
) {
    UNKNOWN(0),
    REPLY(100),
    REPOST(101),
    QUOTE(102),
    LIKE(200),
    BOOKMARK(201),
    ;

    companion object {
        fun fromCode(code: Int): PostReactionType {
            return entries.firstOrNull { it.code == code } ?: UNKNOWN
        }
    }
}

internal object PostReactionTypeSerializer :
    KSerializer<PostReactionType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "work.socialhub.kmixi2web.entity.PostReactionType",
            PrimitiveKind.INT,
        )

    override fun serialize(
        encoder: Encoder,
        value: PostReactionType,
    ) {
        encoder.encodeInt(value.code)
    }

    override fun deserialize(
        decoder: Decoder,
    ): PostReactionType {
        return PostReactionType.fromCode(decoder.decodeInt())
    }
}
