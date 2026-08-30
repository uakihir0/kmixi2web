package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.SearchOperation
import kotlin.js.JsExport

@Serializable
@JsExport
class SearchRequest(
    @ProtoNumber(1)
    var query: String,
    @ProtoNumber(2)
    var operations: List<SearchOperation> = emptyList(),
)

@Serializable
@JsExport
class SearchTypeaheadRequest(
    @ProtoNumber(1)
    var query: String,
)
