package work.socialhub.kmixi2web

import kotlinx.coroutines.runBlocking
import work.socialhub.kmixi2web.api.request.AddStampToPostRequest
import work.socialhub.kmixi2web.api.request.CreateLikeRequest
import work.socialhub.kmixi2web.api.request.DeleteLikeRequest
import work.socialhub.kmixi2web.api.request.GetLikingPersonasRequest
import work.socialhub.kmixi2web.api.request.GetPostRequest
import work.socialhub.kmixi2web.api.request.GetPostStampReactionsRequest
import work.socialhub.kmixi2web.api.request.GetStampsRequest
import work.socialhub.kmixi2web.api.request.GetSubscribingFeedsRequest
import work.socialhub.kmixi2web.api.request.RemoveStampFromPostRequest
import work.socialhub.kmixi2web.entity.LanguageCode
import work.socialhub.kmixi2web.entity.Post
import work.socialhub.kmixi2web.entity.PostStamp
import work.socialhub.kmixi2web.entity.StampReaction
import java.net.HttpURLConnection
import java.net.URI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ReactionLiveTest {
    @Test
    fun readReactionCountsAndImageUrls() = runBlocking {
        LiveTestSupport.requireMode("reaction-read", "reaction-write")
        val client = LiveTestSupport.client()
        val posts = readTimelinePosts(client)

        assertTrue(posts.isNotEmpty())
        val likedPosts = posts.filter { it.likesCount > 0 }
        assertTrue(likedPosts.isNotEmpty(), "Expected a post with at least one like")

        val catalog = client.reaction().getStamps(
            GetStampsRequest(officialStampLanguage = LanguageCode.JP)
        )
        assertEquals(200, catalog.status)
        val officialStamps = catalog.data.officialStampSets.flatMap { it.stamps }
        assertTrue(officialStamps.isNotEmpty())
        assertTrue(officialStamps.all { it.stampId.isNotBlank() })
        assertTrue(officialStamps.all { it.url.isWebUrl() })
        assertImageUrlReachable(officialStamps.first().url)

        val stampedPost = posts
            .filter { it.stamps.isNotEmpty() }
            .maxByOrNull { post -> post.stamps.sumOf { it.count } }
        assertNotNull(
            stampedPost,
            "Expected a post with at least one image reaction",
        )
        verifyStampDetails(client, stampedPost)

        val likedPost = likedPosts
            .maxByOrNull { it.likesCount }
        assertNotNull(likedPost)
        verifyLikeSummary(client, likedPost)

        println(
            "Scanned ${posts.size} posts: " +
                "${likedPosts.size} with likes, " +
                "${posts.count { it.stamps.isNotEmpty() }} with image reactions; " +
                "${officialStamps.size} stamp image URLs available."
        )
    }

    @Test
    fun controlledReactionRoundTrip() = runBlocking {
        LiveTestSupport.requireMode("reaction-write")
        val client = LiveTestSupport.client()
        val postId = LiveTestSupport.requiredPostId()
        val original = getPost(client, postId)

        assertFalse(original.liked, "Use a post not already liked by the active persona")
        assertTrue(
            original.readerStampId.isNullOrBlank(),
            "Use a post without an existing stamp from the active persona",
        )

        val catalog = client.reaction().getStamps(
            GetStampsRequest(officialStampLanguage = LanguageCode.JP)
        )
        assertEquals(200, catalog.status)
        val stamp = assertNotNull(
            catalog.data.officialStampSets
                .flatMap { it.stamps }
                .firstOrNull { it.stampId.isNotBlank() && it.url.isWebUrl() }
        )
        assertImageUrlReachable(stamp.url)

        val originalStampCount = original.stampCount(stamp.stampId)
        var stampAdded = false
        var likeAdded = false

        try {
            val addStampResponse = client.reaction().addStampToPost(
                AddStampToPostRequest(postId, stamp.stampId)
            )
            assertEquals(200, addStampResponse.status)
            val stamped = assertNotNull(addStampResponse.data.post)
            stampAdded = true
            assertEquals(stamp.stampId, stamped.readerStampId)
            val stampSummary = assertNotNull(
                stamped.stamps.firstOrNull { it.stamp?.stampId == stamp.stampId }
            )
            assertEquals(originalStampCount + 1, stampSummary.count)
            assertEquals(stamp.url, stampSummary.stamp?.url)

            val stampDetails = client.reaction().getPostStampReactions(
                GetPostStampReactionsRequest(postId, limit = 100)
            )
            assertEquals(200, stampDetails.status)
            val detailSummary = assertNotNull(
                stampDetails.data.stamps.firstOrNull {
                    it.stamp?.stampId == stamp.stampId
                }
            )
            assertEquals(stamp.url, detailSummary.stamp?.url)
            assertEquals(originalStampCount + 1, detailSummary.count)
            assertTrue(
                stampDetails.data.stampReactions.any {
                    it.stampId == stamp.stampId &&
                        it.persona?.personaId == original.personaId
                }
            )

            val createLikeResponse = client.reaction().createLike(
                CreateLikeRequest(postId)
            )
            assertEquals(200, createLikeResponse.status)
            val liked = assertNotNull(createLikeResponse.data.post)
            likeAdded = true
            assertTrue(liked.liked)
            assertEquals(original.likesCount + 1, liked.likesCount)

            val likingPersonas = client.reaction().getLikingPersonas(
                GetLikingPersonasRequest(postId, limit = 100)
            )
            assertEquals(200, likingPersonas.status)
            assertTrue(
                likingPersonas.data.personas.any {
                    it.personaId == original.personaId
                }
            )
            assertEquals(
                liked.likesCount,
                likingPersonas.data.personas.distinctBy { it.personaId }.size.toLong(),
            )

            val refreshed = getPost(client, postId)
            assertTrue(refreshed.liked)
            assertEquals(original.likesCount + 1, refreshed.likesCount)
            assertEquals(stamp.stampId, refreshed.readerStampId)
            assertEquals(originalStampCount + 1, refreshed.stampCount(stamp.stampId))

            println(
                "Verified stamp ${stamp.stampId} (${stamp.url}) and like round trip " +
                    "on post $postId."
            )
        } finally {
            if (likeAdded) {
                val deleted = client.reaction().deleteLike(DeleteLikeRequest(postId))
                assertEquals(200, deleted.status)
            }
            if (stampAdded) {
                val removed = client.reaction().removeStampFromPost(
                    RemoveStampFromPostRequest(postId, stamp.stampId)
                )
                assertEquals(200, removed.status)
            }
        }

        val restored = getPost(client, postId)
        assertFalse(restored.liked)
        assertEquals(original.likesCount, restored.likesCount)
        assertTrue(restored.readerStampId.isNullOrBlank())
        assertEquals(originalStampCount, restored.stampCount(stamp.stampId))
    }

    private suspend fun readTimelinePosts(client: Mixi2Web): List<Post> {
        val posts = mutableListOf<Post>()
        var cursor: String? = null
        repeat(4) {
            val response = client.timeline().getSubscribingFeeds(
                GetSubscribingFeedsRequest(
                    untilCursor = cursor,
                    limit = 50,
                )
            )
            assertEquals(200, response.status)
            posts += response.data.feeds.mapNotNull { feed ->
                feed.post ?: feed.communityAggregationPost?.post
            }
            cursor = response.data.nextCursor
            if (cursor.isNullOrBlank()) {
                return posts.distinctBy { it.postId }
            }
        }
        return posts.distinctBy { it.postId }
    }

    private suspend fun verifyStampDetails(
        client: Mixi2Web,
        timelinePost: Post,
    ) {
        timelinePost.stamps.verifySummaries()
        val fetched = getPost(client, timelinePost.postId)
        fetched.stamps.verifySummaries()
        assertEquals(
            timelinePost.stamps.associateCountById(),
            fetched.stamps.associateCountById(),
        )

        val details = client.reaction().getPostStampReactions(
            GetPostStampReactionsRequest(timelinePost.postId, limit = PAGE_SIZE)
        )
        assertEquals(200, details.status)
        details.data.stamps.verifySummaries()
        assertEquals(
            fetched.stamps.associateCountById(),
            details.data.stamps.associateCountById(),
        )
        val stampIds = details.data.stamps.mapNotNull { it.stamp?.stampId }.toSet()
        assertTrue(
            details.data.stampReactions.all { it.stampId in stampIds }
        )
        assertImageUrlReachable(
            checkNotNull(details.data.stamps.first().stamp?.url)
        )

        val allReactions = getAllStampReactions(client, timelinePost.postId)
        val detailCounts = allReactions.groupingBy { it.stampId }.eachCount()
        for (summary in details.data.stamps) {
            val stampId = checkNotNull(summary.stamp?.stampId)
            assertEquals(summary.count, detailCounts[stampId]?.toLong() ?: 0)
        }
    }

    private suspend fun verifyLikeSummary(
        client: Mixi2Web,
        timelinePost: Post,
    ) {
        val fetched = getPost(client, timelinePost.postId)
        assertEquals(timelinePost.likesCount, fetched.likesCount)
    }

    private suspend fun getAllStampReactions(
        client: Mixi2Web,
        postId: String,
    ): List<StampReaction> {
        val reactions = mutableListOf<StampReaction>()
        var cursor: String? = null
        var page = 0
        do {
            val response = client.reaction().getPostStampReactions(
                GetPostStampReactionsRequest(
                    postId = postId,
                    cursor = cursor,
                    limit = PAGE_SIZE,
                )
            )
            assertEquals(200, response.status)
            reactions += response.data.stampReactions
            cursor = response.data.nextCursor?.takeIf { it.isNotBlank() }
            page += 1
        } while (cursor != null && page < MAX_PAGES)

        assertTrue(cursor == null, "Stamp-reaction pagination exceeded $MAX_PAGES pages")
        return reactions
    }

    private suspend fun getPost(client: Mixi2Web, postId: String): Post {
        return assertNotNull(
            client.post().getPost(GetPostRequest(postId)).data.post
        )
    }

    private fun List<PostStamp>.verifySummaries() {
        assertTrue(isNotEmpty())
        forEach {
            assertTrue(it.count > 0)
            assertTrue(it.stamp?.stampId?.isNotBlank() == true)
            assertTrue(it.stamp?.url?.isWebUrl() == true)
        }
    }

    private fun List<PostStamp>.associateCountById(): Map<String, Long> {
        return associate { checkNotNull(it.stamp?.stampId) to it.count }
    }

    private fun Post.stampCount(stampId: String): Long {
        return stamps.firstOrNull { it.stamp?.stampId == stampId }?.count ?: 0
    }

    private fun String.isWebUrl(): Boolean {
        return startsWith("https://") || startsWith("http://")
    }

    private fun assertImageUrlReachable(url: String) {
        val connection = URI(url).toURL().openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "HEAD"
            connection.instanceFollowRedirects = true
            connection.connectTimeout = 10_000
            connection.readTimeout = 10_000
            connection.setRequestProperty("User-Agent", "kmixi2web-live-test")
            assertTrue(
                connection.responseCode in 200..399,
                "Stamp image returned HTTP ${connection.responseCode}: $url",
            )
            val contentType = connection.contentType.orEmpty()
            assertTrue(
                contentType.startsWith("image/") ||
                    contentType == "application/octet-stream",
                "Unexpected stamp image content type $contentType: $url",
            )
        } finally {
            connection.disconnect()
        }
    }

    companion object {
        private const val PAGE_SIZE = 100
        private const val MAX_PAGES = 20
    }
}
