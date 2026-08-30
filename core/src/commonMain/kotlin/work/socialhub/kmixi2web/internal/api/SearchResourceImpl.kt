package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.SearchResource
import work.socialhub.kmixi2web.api.request.SearchRequest
import work.socialhub.kmixi2web.api.request.SearchTypeaheadRequest
import work.socialhub.kmixi2web.api.response.SearchResponse
import work.socialhub.kmixi2web.api.response.SearchTypeaheadResponse
import work.socialhub.kmixi2web.entity.share.Response
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class SearchResourceImpl(
    private val client: MercuryClient,
) : SearchResource {
    override suspend fun search(
        request: SearchRequest,
    ): Response<SearchResponse> {
        return client.call(
            "Search",
            request,
            SearchRequest.serializer(),
            SearchResponse.serializer(),
        )
    }

    override fun searchBlocking(
        request: SearchRequest,
    ) = toBlocking { search(request) }

    override suspend fun searchTypeahead(
        request: SearchTypeaheadRequest,
    ): Response<SearchTypeaheadResponse> {
        return client.call(
            "SearchTypeahead",
            request,
            SearchTypeaheadRequest.serializer(),
            SearchTypeaheadResponse.serializer(),
        )
    }

    override fun searchTypeaheadBlocking(
        request: SearchTypeaheadRequest,
    ) = toBlocking { searchTypeahead(request) }
}
