package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.MediaCategory
import kotlin.js.JsExport

@Serializable
@JsExport
class PrepareMediaUploadingRequest(
    @ProtoNumber(1)
    var mimeType: String,
    @ProtoNumber(2)
    var dataSize: Long,
    @ProtoNumber(3)
    var category: MediaCategory,
    @ProtoNumber(4)
    var communityId: String? = null,
    @ProtoNumber(5)
    var description: String? = null,
)

@Serializable
@JsExport
class GetMediaRequest(
    @ProtoNumber(1)
    var mediaId: String,
)

/**
 * Upload helper request. This is not a protobuf message: it drives
 * `PrepareMediaUploading`, the binary upload to the returned target, and the
 * `GetMedia` polling that follows.
 */
@JsExport
class UploadMediaRequest(
    var mimeType: String,
    var data: ByteArray,
    var category: MediaCategory,
    var communityId: String? = null,
    var description: String? = null,
    var pollIntervalMillis: Long = 1000,
    var pollAttempts: Int = 30,
)
