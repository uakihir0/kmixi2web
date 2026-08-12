package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class Avatar(
    @ProtoNumber(1)
    var iconUrl: String = "",
    @ProtoNumber(2)
    var iconMimeType: String = "",
    @ProtoNumber(3)
    var iconHeight: Int = 0,
    @ProtoNumber(4)
    var iconWidth: Int = 0,
    @ProtoNumber(5)
    var profileImageUrl: String = "",
    @ProtoNumber(6)
    var profileImageMimeType: String = "",
    @ProtoNumber(7)
    var profileImageHeight: Int = 0,
    @ProtoNumber(8)
    var profileImageWidth: Int = 0,
    @ProtoNumber(9)
    var blurhash: String = "",
)

@Serializable
@JsExport
class PostImage(
    @ProtoNumber(1)
    var largeImageUrl: String = "",
    @ProtoNumber(2)
    var largeImageMimeType: String = "",
    @ProtoNumber(3)
    var largeImageHeight: Int = 0,
    @ProtoNumber(4)
    var largeImageWidth: Int = 0,
    @ProtoNumber(5)
    var smallImageUrl: String = "",
    @ProtoNumber(6)
    var smallImageMimeType: String = "",
    @ProtoNumber(7)
    var smallImageHeight: Int = 0,
    @ProtoNumber(8)
    var smallImageWidth: Int = 0,
    @ProtoNumber(9)
    var blurhash: String = "",
)

@Serializable
@JsExport
class PostVideo(
    @ProtoNumber(1)
    var url: String = "",
    @ProtoNumber(2)
    var mimeType: String = "",
    @ProtoNumber(3)
    var height: Int = 0,
    @ProtoNumber(4)
    var width: Int = 0,
    @ProtoNumber(5)
    var previewImageUrl: String = "",
    @ProtoNumber(6)
    var previewImageMimeType: String = "",
    @ProtoNumber(7)
    var previewImageHeight: Int = 0,
    @ProtoNumber(8)
    var previewImageWidth: Int = 0,
    @ProtoNumber(9)
    var blurhash: String = "",
    @ProtoNumber(10)
    var duration: Float = 0f,
)

@Serializable
@JsExport
class Media(
    @ProtoNumber(1)
    var mediaId: String = "",
    @ProtoNumber(3)
    var category: Int = 0,
    @ProtoNumber(4)
    var status: Int = 0,
    @ProtoNumber(5)
    var avatar: Avatar? = null,
    @ProtoNumber(6)
    var postImage: PostImage? = null,
    @ProtoNumber(7)
    var postVideo: PostVideo? = null,
    @ProtoNumber(9)
    var description: String? = null,
)
