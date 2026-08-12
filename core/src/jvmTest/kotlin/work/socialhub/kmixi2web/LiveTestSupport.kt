package work.socialhub.kmixi2web

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assumptions.assumeTrue
import java.io.File

internal object LiveTestSupport {
    fun client(): Mixi2Web {
        val file = File("../secrets.json")
        assumeTrue(file.isFile, "Create secrets.json in the repository root")
        val secrets = Json.decodeFromString<Secrets>(file.readText())
        assumeTrue(
            secrets.cookie.isNotBlank() && secrets.authKey.isNotBlank(),
            "cookie and authKey are required",
        )
        return Mixi2WebFactory.instance(secrets.cookie, secrets.authKey)
    }

    fun requireMode(vararg accepted: String) {
        val mode = System.getenv(LIVE_MODE_ENV)
        assumeTrue(
            mode in accepted,
            "Set $LIVE_MODE_ENV to ${accepted.joinToString(" or ")}",
        )
    }

    fun requiredPostId(): String {
        val postId = System.getenv(POST_ID_ENV)
        assumeTrue(!postId.isNullOrBlank(), "Set $POST_ID_ENV to a post you own")
        return checkNotNull(postId)
    }

    @Serializable
    private class Secrets(
        val cookie: String,
        val authKey: String,
    )

    private const val LIVE_MODE_ENV = "KMIXI2WEB_LIVE_MODE"
    private const val POST_ID_ENV = "KMIXI2WEB_POST_ID"
}
