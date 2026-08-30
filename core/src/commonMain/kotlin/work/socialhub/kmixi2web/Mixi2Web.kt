package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.FollowResource
import work.socialhub.kmixi2web.api.ModerationResource
import work.socialhub.kmixi2web.api.NotificationResource
import work.socialhub.kmixi2web.api.PersonaResource
import work.socialhub.kmixi2web.api.PostResource
import work.socialhub.kmixi2web.api.RawResource
import work.socialhub.kmixi2web.api.ReactionResource
import work.socialhub.kmixi2web.api.SessionResource
import work.socialhub.kmixi2web.api.TimelineResource
import kotlin.js.JsExport

@JsExport
interface Mixi2Web {
    fun session(): SessionResource
    fun timeline(): TimelineResource
    fun post(): PostResource
    fun persona(): PersonaResource
    fun follow(): FollowResource
    fun reaction(): ReactionResource
    fun notification(): NotificationResource
    fun moderation(): ModerationResource
    fun raw(): RawResource

    companion object {
        const val DEFAULT_API_BASE_URI =
            "https://mixi.social/api/connect/com.mixi.mercury.api.MercuryService"
    }
}
