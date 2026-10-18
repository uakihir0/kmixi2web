package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

/**
 * Wire-compatible model of `google.type.Date` (a calendar date without time
 * or time zone). Named `CalendarDate` to avoid clashing with JavaScript's
 * global `Date` in the exported JS API.
 *
 * A zero value means the component is unspecified (e.g. `day = 0` for a
 * year-and-month date).
 */
@Serializable
@JsExport
class CalendarDate(
    @ProtoNumber(1)
    var year: Int = 0,
    @ProtoNumber(2)
    var month: Int = 0,
    @ProtoNumber(3)
    var day: Int = 0,
)
