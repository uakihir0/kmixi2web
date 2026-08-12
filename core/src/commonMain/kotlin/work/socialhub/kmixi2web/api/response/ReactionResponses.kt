package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.CommunityStampSet
import work.socialhub.kmixi2web.entity.OfficialStampSet
import work.socialhub.kmixi2web.entity.Persona
import work.socialhub.kmixi2web.entity.PersonaObtainedStampSet
import work.socialhub.kmixi2web.entity.Post
import work.socialhub.kmixi2web.entity.PostStamp
import work.socialhub.kmixi2web.entity.StampReaction
import kotlin.js.JsExport

@Serializable
@JsExport
class GetPostStampReactionsResponse(
    @ProtoNumber(1)
    var stamps: List<PostStamp> = emptyList(),
    @ProtoNumber(2)
    var stampReactions: List<StampReaction> = emptyList(),
    @ProtoNumber(3)
    var nextCursor: String? = null,
)

@Serializable
@JsExport
class GetStampsResponse(
    @ProtoNumber(1)
    var officialStampSets: List<OfficialStampSet> = emptyList(),
    @ProtoNumber(2)
    var communityStampSets: List<CommunityStampSet> = emptyList(),
    @ProtoNumber(3)
    var personaObtainedStampSets: List<PersonaObtainedStampSet> = emptyList(),
)

@Serializable
@JsExport
class AddStampToPostResponse(
    @ProtoNumber(1)
    var post: Post? = null,
)

@Serializable
@JsExport
class RemoveStampFromPostResponse(
    @ProtoNumber(1)
    var post: Post? = null,
)

@Serializable
@JsExport
class CreateLikeResponse(
    @ProtoNumber(1)
    var post: Post? = null,
)

@Serializable
@JsExport
class DeleteLikeResponse(
    @ProtoNumber(1)
    var post: Post? = null,
)

@Serializable
@JsExport
class GetLikingPersonasResponse(
    @ProtoNumber(1)
    var personas: List<Persona> = emptyList(),
    @ProtoNumber(2)
    var nextCursor: String = "",
    @ProtoNumber(3)
    var hasNext: Boolean = false,
)
