package work.socialhub.kmixi2web

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assumptions.assumeTrue
import work.socialhub.kmixi2web.api.request.CreatePostRequest
import work.socialhub.kmixi2web.api.request.GetPersonasRequest
import work.socialhub.kmixi2web.api.request.GetPostRequest
import work.socialhub.kmixi2web.api.request.GetSubscribingFeedsRequest
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LiveSmokeTest {
    @Test
    fun authenticatedRead() = runBlocking {
        requireMode("read", "post")
        val client = liveClient()
        val response = client.timeline().getSubscribingFeeds(
            GetSubscribingFeedsRequest(limit = 10)
        )

        assertEquals(200, response.status)
        val posts = response.data.feeds.mapNotNull { feed ->
            feed.post ?: feed.communityAggregationPost?.post
        }
        assertTrue(posts.isNotEmpty(), "Expected at least one readable post")

        val personaIds = posts.map { it.personaId }
            .filter { it.isNotBlank() }
            .distinct()
        if (personaIds.isNotEmpty()) {
            val personas = client.persona().getPersonas(
                GetPersonasRequest(personaIds)
            )
            assertEquals(200, personas.status)
            assertTrue(personas.data.personas.isNotEmpty())
        }

        println("Authenticated read succeeded with ${posts.size} posts.")
    }

    @Test
    fun createNaturalPost() = runBlocking {
        requireMode("post")
        val client = liveClient()
        val created = client.post().createPost(
            CreatePostRequest(text = POST_TEXT)
        )

        assertEquals(200, created.status)
        val post = assertNotNull(created.data.post)
        assertTrue(post.postId.isNotBlank())
        assertEquals(POST_TEXT, post.text)

        if (!created.data.isPending) {
            val fetched = fetchPost(client, post.postId)
            assertEquals(POST_TEXT, fetched.text)
        }

        println("Created post ${post.postId}.")
    }

    private suspend fun fetchPost(
        client: Mixi2Web,
        postId: String,
    ): work.socialhub.kmixi2web.entity.Post {
        var lastFailure: Throwable? = null
        repeat(5) {
            try {
                return assertNotNull(
                    client.post().getPost(GetPostRequest(postId)).data.post
                )
            } catch (e: Throwable) {
                lastFailure = e
                delay(1_000)
            }
        }
        throw AssertionError("Created post could not be fetched", lastFailure)
    }

    private fun liveClient(): Mixi2Web {
        val file = File("../secrets.json")
        assumeTrue(file.isFile, "Create secrets.json in the repository root")
        val secrets = Json.decodeFromString<Secrets>(file.readText())
        assumeTrue(
            secrets.cookie.isNotBlank() && secrets.authKey.isNotBlank(),
            "cookie and authKey are required",
        )
        return Mixi2WebFactory.instance(secrets.cookie, secrets.authKey)
    }

    private fun requireMode(vararg accepted: String) {
        val mode = System.getenv(LIVE_MODE_ENV)
        assumeTrue(
            mode in accepted,
            "Set $LIVE_MODE_ENV to ${accepted.joinToString(" or ")}",
        )
    }

    @Serializable
    private class Secrets(
        val cookie: String,
        val authKey: String,
    )

    companion object {
        private const val LIVE_MODE_ENV = "KMIXI2WEB_LIVE_MODE"
        private const val POST_TEXT = "ねむい"
    }
}
