package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.api.request.GetMediaRequest
import work.socialhub.kmixi2web.api.request.PrepareMediaUploadingRequest
import work.socialhub.kmixi2web.api.request.UploadMediaRequest
import work.socialhub.kmixi2web.api.response.GetMediaResponse
import work.socialhub.kmixi2web.api.response.PrepareMediaUploadingResponse
import work.socialhub.kmixi2web.api.response.UploadMediaResponse
import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface MediaResource {
    suspend fun prepareMediaUploading(
        request: PrepareMediaUploadingRequest,
    ): Response<PrepareMediaUploadingResponse>

    @JsExport.Ignore
    fun prepareMediaUploadingBlocking(
        request: PrepareMediaUploadingRequest,
    ): Response<PrepareMediaUploadingResponse>

    suspend fun getMedia(
        request: GetMediaRequest,
    ): Response<GetMediaResponse>

    @JsExport.Ignore
    fun getMediaBlocking(
        request: GetMediaRequest,
    ): Response<GetMediaResponse>

    /**
     * Prepares an upload, sends the bytes to the returned target, and polls
     * `GetMedia` until the media leaves the uploading and processing states.
     * The returned media ID can be used as a `CreatePostRequest.mediaIds` entry.
     */
    suspend fun uploadMedia(
        request: UploadMediaRequest,
    ): Response<UploadMediaResponse>

    @JsExport.Ignore
    fun uploadMediaBlocking(
        request: UploadMediaRequest,
    ): Response<UploadMediaResponse>
}
