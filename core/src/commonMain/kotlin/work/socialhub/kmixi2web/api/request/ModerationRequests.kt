package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.ReportReasonType
import work.socialhub.kmixi2web.entity.ReportRightInfringementTarget
import kotlin.js.JsExport

@Serializable
@JsExport
class MakePersonaBlockRequest(
    @ProtoNumber(1)
    var personaId: String,
)

@Serializable
@JsExport
class MakePersonaUnblockRequest(
    @ProtoNumber(1)
    var personaId: String,
)

@Serializable
@JsExport
class MakePersonaMuteRequest(
    @ProtoNumber(1)
    var personaId: String,
)

@Serializable
@JsExport
class MakePersonaUnmuteRequest(
    @ProtoNumber(1)
    var personaId: String,
)

@Serializable
@JsExport
class GetBlockPersonasRequest

@Serializable
@JsExport
class GetMutePersonasRequest

@Serializable
@JsExport
class ReportPostRequest(
    @ProtoNumber(1)
    var postId: String,
    @ProtoNumber(2)
    var reasonType: ReportReasonType,
    @ProtoNumber(3)
    var reasonContent: String = "",
    @ProtoNumber(4)
    var rightInfringementTarget: ReportRightInfringementTarget? = null,
)

@Serializable
@JsExport
class ReportPersonaRequest(
    @ProtoNumber(1)
    var personaId: String,
    @ProtoNumber(2)
    var reasonType: ReportReasonType,
    @ProtoNumber(3)
    var reasonContent: String = "",
    @ProtoNumber(4)
    var rightInfringementTarget: ReportRightInfringementTarget? = null,
)
