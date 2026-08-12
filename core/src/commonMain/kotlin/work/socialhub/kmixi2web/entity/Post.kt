package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class LinkCard(
    @ProtoNumber(1)
    var cardId: String = "",
    @ProtoNumber(2)
    var url: String = "",
    @ProtoNumber(3)
    var status: Int = 0,
    @ProtoNumber(4)
    var contentType: Int = 0,
    @ProtoNumber(5)
    var title: String = "",
    @ProtoNumber(6)
    var description: String = "",
    @ProtoNumber(7)
    var imageUrl: String = "",
    @ProtoNumber(8)
    var faviconUrl: String = "",
)

@Serializable
@JsExport
class Post(
    @ProtoNumber(1)
    var postId: String = "",
    @ProtoNumber(2)
    var timeSeriesId: String = "",
    @ProtoNumber(3)
    var createdAt: Timestamp? = null,
    @ProtoNumber(4)
    var personaId: String = "",
    @ProtoNumber(5)
    var visibility: Int = 0,
    @ProtoNumber(6)
    var isDeleted: Boolean = false,
    @ProtoNumber(7)
    var medias: List<Media> = emptyList(),
    @ProtoNumber(8)
    var repostCount: Long = 0,
    @ProtoNumber(9)
    var likesCount: Long = 0,
    @ProtoNumber(10)
    var repliesCount: Long = 0,
    @ProtoNumber(11)
    var quotedCount: Long = 0,
    @ProtoNumber(12)
    var text: String? = null,
    @ProtoNumber(13)
    var inReplyToPostId: String? = null,
    @ProtoNumber(14)
    var repostId: String? = null,
    @ProtoNumber(15)
    var quotePostId: String? = null,
    @ProtoNumber(16)
    var liked: Boolean = false,
    @ProtoNumber(17)
    var reposted: Boolean = false,
    @ProtoNumber(18)
    var bookmarked: Boolean = false,
    @ProtoNumber(19)
    var referencePost: Post? = null,
    @ProtoNumber(20)
    var quoted: Boolean = false,
    @ProtoNumber(21)
    var replied: Boolean = false,
    @ProtoNumber(22)
    var linkCards: List<LinkCard> = emptyList(),
    @ProtoNumber(23)
    var isMuted: Boolean = false,
    @ProtoNumber(24)
    var isSensitive: Boolean = false,
    @ProtoNumber(25)
    var community: CommunitySummary? = null,
    @ProtoNumber(27)
    var mentions: List<PersonaName> = emptyList(),
    @ProtoNumber(28)
    var decorations: List<Int> = emptyList(),
    @ProtoNumber(30)
    var readerStampId: String? = null,
    @ProtoNumber(31)
    var isRestricted: Boolean = false,
    @ProtoNumber(32)
    var maskType: Int = 0,
    @ProtoNumber(33)
    var maskCaption: String? = null,
    @ProtoNumber(34)
    var publishingType: Int = 0,
)
