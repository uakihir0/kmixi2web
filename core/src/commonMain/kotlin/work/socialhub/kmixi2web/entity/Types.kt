package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
enum class FeedSourceType {
    UNSPECIFIED,
    FOLLOWING,
    COMMUNITY,
}

@Serializable
@JsExport
enum class PostMaskType {
    NONE,
    SENSITIVE,
    SPOILER,
}

@Serializable
@JsExport
enum class PostPublishingType {
    UNSPECIFIED,
    FOLLOW_AND_COMMUNITY,
}

@Serializable
@JsExport
enum class LanguageCode {
    UNKNOWN,
    JP,
    EN,
}
