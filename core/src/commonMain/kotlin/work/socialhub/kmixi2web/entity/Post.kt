package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlinx.serialization.protobuf.ProtoPacked
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
    /**
     * Community shared by this post (a "community share" post created with
     * [work.socialhub.kmixi2web.api.request.CreatePostRequest.attachedCommunityId]).
     * The server may include a nested [Community] with the fields modeled
     * here (name, purpose, cover image, member count, member previews and the
     * reader's own membership); fields not modeled by [Community] are skipped.
     */
    @ProtoNumber(26)
    var attachedCommunity: Community? = null,
    @ProtoNumber(27)
    var mentions: List<PersonaName> = emptyList(),
    @ProtoNumber(28)
    @ProtoPacked
    var decorations: List<Int> = emptyList(),
    @ProtoNumber(29)
    var stamps: List<PostStamp> = emptyList(),
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
    /**
     * Calendar date carried by some posts, such as the original posting date
     * of a post imported from an X archive (the web client shows it as
     * "<date> memories"). Only the date is provided, not the time of day.
     * Null when the post has no such date.
     */
    @ProtoNumber(38)
    var date: CalendarDate? = null,
    /**
     * Raw [PostImportSource] value. 0 (UNSPECIFIED) when the post was not
     * imported, 1 (X_ARCHIVE) for a post imported from an X archive.
     */
    @ProtoNumber(42)
    var importSource: Int = 0,
)
