package work.socialhub.kmixi2web

import kotlin.js.JsExport

@JsExport
class Mixi2WebConfig {
    var cookie: String = ""
    var authKey: String = ""
    var apiBaseUri: String = Mixi2Web.DEFAULT_API_BASE_URI
    var mercuryUserAgent: String = "Mercury-web/3.2.0"
    var useProxyCookieHeader: Boolean = false
    var requestTimeoutMillis: Long = 30_000
    var connectTimeoutMillis: Long = 15_000
    var socketTimeoutMillis: Long = 30_000
}
