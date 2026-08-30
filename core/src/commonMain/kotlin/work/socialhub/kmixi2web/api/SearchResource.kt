package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.api.request.SearchRequest
import work.socialhub.kmixi2web.api.request.SearchTypeaheadRequest
import work.socialhub.kmixi2web.api.response.SearchResponse
import work.socialhub.kmixi2web.api.response.SearchTypeaheadResponse
import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface SearchResource {
    suspend fun search(
        request: SearchRequest,
    ): Response<SearchResponse>

    @JsExport.Ignore
    fun searchBlocking(
        request: SearchRequest,
    ): Response<SearchResponse>

    suspend fun searchTypeahead(
        request: SearchTypeaheadRequest,
    ): Response<SearchTypeaheadResponse>

    @JsExport.Ignore
    fun searchTypeaheadBlocking(
        request: SearchTypeaheadRequest,
    ): Response<SearchTypeaheadResponse>
}
