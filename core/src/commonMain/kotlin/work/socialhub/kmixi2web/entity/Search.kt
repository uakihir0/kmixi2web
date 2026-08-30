package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class SearchOperation(
    @ProtoNumber(1)
    var type: SearchType = SearchType.PERSONAS,
    @ProtoNumber(2)
    var operationId: Int = 0,
    @ProtoNumber(3)
    var untilCursor: String? = null,
    @ProtoNumber(4)
    var sinceCursor: String? = null,
    @ProtoNumber(5)
    var limit: Int? = null,
    @ProtoNumber(6)
    var endCursor: String? = null,
    @ProtoNumber(7)
    var mediaAttachedOnly: Boolean? = null,
    @ProtoNumber(8)
    var startTimeAfter: Timestamp? = null,
    @ProtoNumber(9)
    var endTimeAfter: Timestamp? = null,
    @ProtoNumber(10)
    var personaOption: SearchPersonaOption? = null,
    @ProtoNumber(11)
    var postOption: SearchPostOption? = null,
    @ProtoNumber(12)
    var eventOption: SearchEventOption? = null,
)

@Serializable
@JsExport
class SearchPostOption(
    @ProtoNumber(1)
    var mediaAttachedOnly: Boolean = false,
    @ProtoNumber(2)
    var personaId: String? = null,
)

@Serializable
@JsExport
class SearchPersonaOption(
    @ProtoNumber(1)
    var botOnly: Boolean = false,
)

@Serializable
@JsExport
class SearchEventOption(
    @ProtoNumber(1)
    var startTimeAfter: Timestamp? = null,
    @ProtoNumber(2)
    var endTimeAfter: Timestamp? = null,
)

@Serializable
@JsExport
class SearchResult(
    @ProtoNumber(1)
    var operationId: Int = 0,
    @ProtoNumber(2)
    var personasResult: PersonasResult? = null,
    @ProtoNumber(3)
    var postsResult: PostsResult? = null,
)

@Serializable
@JsExport
class PersonasResult(
    @ProtoNumber(1)
    var personaWithConnectivities: List<PersonaWithConnectivity> = emptyList(),
    @ProtoNumber(2)
    var nextCursor: String = "",
)

@Serializable
@JsExport
class PostsResult(
    @ProtoNumber(1)
    var posts: List<Post> = emptyList(),
    @ProtoNumber(2)
    var nextCursor: String = "",
)

@Serializable
@JsExport
class SearchTypeaheadItem(
    @ProtoNumber(1)
    var itemType: Int = 0,
    @ProtoNumber(2)
    var persona: Persona? = null,
)
