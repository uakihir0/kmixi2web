package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.internal.Mixi2WebImpl
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
object Mixi2WebFactory {

    fun instance(
        cookie: String,
        authKey: String,
    ): Mixi2Web {
        return instance(
            Mixi2WebConfig().also {
                it.cookie = cookie
                it.authKey = authKey
            }
        )
    }

    @JsName("instanceFromConfig")
    fun instance(config: Mixi2WebConfig): Mixi2Web {
        require(config.authKey.isNotBlank()) { "authKey is required" }
        return Mixi2WebImpl(config)
    }

    @JsName("instanceForProxy")
    fun instanceForProxy(
        cookie: String,
        authKey: String,
        baseUrl: String,
    ): Mixi2Web {
        return instance(
            Mixi2WebConfig().also {
                it.cookie = cookie
                it.authKey = authKey
                it.apiBaseUri = baseUrl
                it.useProxyCookieHeader = true
            }
        )
    }
}
