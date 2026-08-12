package work.socialhub.kmixi2web

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
object Kmixi2webFactory {
    fun instance(
        cookie: String,
        authKey: String,
    ): Mixi2Web {
        return Mixi2WebFactory.instance(cookie, authKey)
    }

    @JsName("instanceFromConfig")
    fun instance(config: Mixi2WebConfig): Mixi2Web {
        return Mixi2WebFactory.instance(config)
    }

    @JsName("instanceForProxy")
    fun instanceForProxy(
        cookie: String,
        authKey: String,
        baseUrl: String,
    ): Mixi2Web {
        return Mixi2WebFactory.instanceForProxy(cookie, authKey, baseUrl)
    }
}
