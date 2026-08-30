package work.socialhub.kmixi2web.internal.api

import kotlinx.coroutines.delay
import work.socialhub.kmixi2web.Mixi2WebException
import work.socialhub.kmixi2web.api.MediaResource
import work.socialhub.kmixi2web.api.request.GetMediaRequest
import work.socialhub.kmixi2web.api.request.PrepareMediaUploadingRequest
import work.socialhub.kmixi2web.api.request.UploadMediaRequest
import work.socialhub.kmixi2web.api.response.GetMediaResponse
import work.socialhub.kmixi2web.api.response.PrepareMediaUploadingResponse
import work.socialhub.kmixi2web.api.response.UploadMediaResponse
import work.socialhub.kmixi2web.entity.Media
import work.socialhub.kmixi2web.entity.MediaStatus
import work.socialhub.kmixi2web.entity.share.Response
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class MediaResourceImpl(
    private val client: MercuryClient,
) : MediaResource {
    override suspend fun prepareMediaUploading(
        request: PrepareMediaUploadingRequest,
    ): Response<PrepareMediaUploadingResponse> {
        return fetchPrepareMediaUploading(request)
    }

    override fun prepareMediaUploadingBlocking(
        request: PrepareMediaUploadingRequest,
    ) = toBlocking { fetchPrepareMediaUploading(request) }

    override suspend fun getMedia(
        request: GetMediaRequest,
    ): Response<GetMediaResponse> {
        return fetchMedia(request)
    }

    override fun getMediaBlocking(
        request: GetMediaRequest,
    ) = toBlocking { fetchMedia(request) }

    override suspend fun uploadMedia(
        request: UploadMediaRequest,
    ): Response<UploadMediaResponse> {
        return doUploadMedia(request)
    }

    override fun uploadMediaBlocking(
        request: UploadMediaRequest,
    ) = toBlocking { doUploadMedia(request) }

    private suspend fun doUploadMedia(
        request: UploadMediaRequest,
    ): Response<UploadMediaResponse> {
        val prepared = fetchPrepareMediaUploading(
            PrepareMediaUploadingRequest(
                mimeType = request.mimeType,
                dataSize = request.data.size.toLong(),
                category = request.category,
                communityId = request.communityId,
                description = request.description,
            )
        )

        val target = prepared.data.request
            ?: throw Mixi2WebException(
                message = "PrepareMediaUploading returned no upload target",
                status = prepared.status,
                responseBody = prepared.bytes,
            )

        client.upload(
            url = target.url,
            method = target.method,
            headers = target.headers.associate { it.key to it.value },
            body = request.data,
        )

        val mediaId = prepared.data.mediaId
        var latest = fetchMedia(GetMediaRequest(mediaId))
        var attempt = 1
        while (attempt < request.pollAttempts && latest.data.media.isProcessing()) {
            delay(request.pollIntervalMillis)
            latest = fetchMedia(GetMediaRequest(mediaId))
            attempt++
        }

        val media = latest.data.media
        if (media?.status == MediaStatus.FAILURE) {
            throw Mixi2WebException(
                message = "Media processing failed for $mediaId",
                status = latest.status,
                responseBody = latest.bytes,
            )
        }

        return Response(
            UploadMediaResponse(mediaId, media),
            latest.status,
            latest.bytes,
        )
    }

    private suspend fun fetchPrepareMediaUploading(
        request: PrepareMediaUploadingRequest,
    ): Response<PrepareMediaUploadingResponse> {
        return client.call(
            "PrepareMediaUploading",
            request,
            PrepareMediaUploadingRequest.serializer(),
            PrepareMediaUploadingResponse.serializer(),
        )
    }

    private suspend fun fetchMedia(
        request: GetMediaRequest,
    ): Response<GetMediaResponse> {
        return client.call(
            "GetMedia",
            request,
            GetMediaRequest.serializer(),
            GetMediaResponse.serializer(),
        )
    }

    private fun Media?.isProcessing(): Boolean {
        return this == null ||
            status == MediaStatus.WAIT_FOR_UPLOADING ||
            status == MediaStatus.IN_PROGRESS ||
            status == MediaStatus.UNKNOWN
    }
}
