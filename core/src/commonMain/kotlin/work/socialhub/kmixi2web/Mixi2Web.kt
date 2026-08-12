package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.PersonaResource
import work.socialhub.kmixi2web.api.PostResource
import work.socialhub.kmixi2web.api.RawResource
import work.socialhub.kmixi2web.api.ReactionResource
import work.socialhub.kmixi2web.api.TimelineResource
import kotlin.js.JsExport

@JsExport
interface Mixi2Web {
    fun timeline(): TimelineResource
    fun post(): PostResource
    fun persona(): PersonaResource
    fun reaction(): ReactionResource
    fun raw(): RawResource

    companion object {
        const val DEFAULT_API_BASE_URI =
            "https://mixi.social/api/connect/com.mixi.mercury.api.MercuryService"
    }
}
