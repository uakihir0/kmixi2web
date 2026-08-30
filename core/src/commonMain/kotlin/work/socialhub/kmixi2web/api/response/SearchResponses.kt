package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.SearchResult
import work.socialhub.kmixi2web.entity.SearchTypeaheadItem
import kotlin.js.JsExport

@Serializable
@JsExport
class SearchResponse(
    @ProtoNumber(1)
    var results: List<SearchResult> = emptyList(),
)

@Serializable
@JsExport
class SearchTypeaheadResponse(
    @ProtoNumber(1)
    var items: List<SearchTypeaheadItem> = emptyList(),
)
