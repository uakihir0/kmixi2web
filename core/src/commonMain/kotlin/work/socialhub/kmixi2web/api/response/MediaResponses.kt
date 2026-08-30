package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.Media
import work.socialhub.kmixi2web.entity.MediaUploadTarget
import kotlin.js.JsExport

@Serializable
@JsExport
class PrepareMediaUploadingResponse(
    @ProtoNumber(1)
    var mediaId: String = "",
    @ProtoNumber(2)
    var request: MediaUploadTarget? = null,
)

@Serializable
@JsExport
class GetMediaResponse(
    @ProtoNumber(1)
    var media: Media? = null,
)

/**
 * Upload helper response. `media` is the last `GetMedia` result, so its status
 * is `SUCCESS` once processing finished and `IN_PROGRESS` when the polling
 * attempts ran out.
 */
@JsExport
class UploadMediaResponse(
    var mediaId: String = "",
    var media: Media? = null,
)
