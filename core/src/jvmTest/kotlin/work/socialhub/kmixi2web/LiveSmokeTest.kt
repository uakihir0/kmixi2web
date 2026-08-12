package work.socialhub.kmixi2web

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import work.socialhub.kmixi2web.api.request.CreatePostRequest
import work.socialhub.kmixi2web.api.request.GetPersonasRequest
import work.socialhub.kmixi2web.api.request.GetPostRequest
import work.socialhub.kmixi2web.api.request.GetSubscribingFeedsRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LiveSmokeTest {
    @Test
    fun authenticatedRead() = runBlocking {
        requireMode("read", "post")
        val client = LiveTestSupport.client()
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
        val client = LiveTestSupport.client()
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

    private fun requireMode(vararg accepted: String) {
        LiveTestSupport.requireMode(*accepted)
    }

    companion object {
        private const val POST_TEXT = "ねむい"
    }
}
