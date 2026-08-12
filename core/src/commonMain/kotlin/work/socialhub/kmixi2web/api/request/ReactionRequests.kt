package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.LanguageCode
import kotlin.js.JsExport

@Serializable
@JsExport
class GetPostStampReactionsRequest(
    @ProtoNumber(1)
    var postId: String,
    @ProtoNumber(2)
    var cursor: String? = null,
    @ProtoNumber(3)
    var limit: Int? = null,
)

@Serializable
@JsExport
class GetStampsRequest(
    @ProtoNumber(1)
    var officialStampLanguage: LanguageCode? = null,
    @ProtoNumber(2)
    var communityIds: List<String> = emptyList(),
)

@Serializable
@JsExport
class AddStampToPostRequest(
    @ProtoNumber(1)
    var postId: String,
    @ProtoNumber(2)
    var stampId: String,
)

@Serializable
@JsExport
class RemoveStampFromPostRequest(
    @ProtoNumber(1)
    var postId: String,
    @ProtoNumber(2)
    var stampId: String,
)

@Serializable
@JsExport
class CreateLikeRequest(
    @ProtoNumber(1)
    var postId: String,
)

@Serializable
@JsExport
class DeleteLikeRequest(
    @ProtoNumber(1)
    var postId: String,
)

@Serializable
@JsExport
class GetLikingPersonasRequest(
    @ProtoNumber(1)
    var postId: String,
    @ProtoNumber(2)
    var limit: Int? = null,
    @ProtoNumber(3)
    var cursor: String? = null,
)
