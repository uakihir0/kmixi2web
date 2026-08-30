package work.socialhub.kmixi2web

import kotlinx.coroutines.runBlocking
import work.socialhub.kmixi2web.api.request.GetFollowersRequest
import work.socialhub.kmixi2web.api.request.GetFollowingsRequest
import work.socialhub.kmixi2web.api.request.GetProfileRequest
import work.socialhub.kmixi2web.api.request.GetSessionRequest
import work.socialhub.kmixi2web.api.request.SearchRequest
import work.socialhub.kmixi2web.api.request.SearchTypeaheadRequest
import work.socialhub.kmixi2web.entity.SearchOperation
import work.socialhub.kmixi2web.entity.SearchType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GraphLiveTest {
    @Test
    fun readFollowingsAndFollowers() = runBlocking {
        LiveTestSupport.requireMode("graph-read")
        val client = LiveTestSupport.client()

        val session = client.session().getSession(GetSessionRequest())
        assertEquals(200, session.status)
        val personaId = assertNotNull(
            session.data.activePersonaId?.takeIf { it.isNotBlank() },
            "Session did not report an active persona",
        )

        val profile = client.persona().getProfile(GetProfileRequest(personaId))
        assertEquals(200, profile.status)
        val counts = assertNotNull(profile.data.profile)

        val followings = client.follow().getFollowings(
            GetFollowingsRequest(limit = PAGE_SIZE, personaId = personaId)
        )
        assertEquals(200, followings.status)
        followings.data.followings.forEach {
            assertTrue(it.personaId.isNotBlank())
            assertEquals(it.personaId, it.persona?.personaId)
        }

        val followers = client.follow().getFollowers(
            GetFollowersRequest(limit = PAGE_SIZE, personaId = personaId)
        )
        assertEquals(200, followers.status)
        followers.data.followers.forEach {
            assertTrue(it.personaId.isNotBlank())
            assertEquals(it.personaId, it.persona?.personaId)
        }

        // The first page cannot exceed the profile totals.
        assertTrue(followings.data.followings.size <= counts.followingCount)
        assertTrue(followers.data.followers.size <= counts.followedCount)

        println(
            "Read ${followings.data.followings.size} following(s) and " +
                "${followers.data.followers.size} follower(s) of $personaId " +
                "(totals ${counts.followingCount}/${counts.followedCount})."
        )
    }

    @Test
    fun searchPersonasAndPosts() = runBlocking {
        LiveTestSupport.requireMode("search-read")
        val client = LiveTestSupport.client()

        val search = client.search().search(
            SearchRequest(
                query = QUERY,
                operations = listOf(
                    SearchOperation(
                        type = SearchType.PERSONAS,
                        operationId = 1,
                        limit = PAGE_SIZE,
                    ),
                    SearchOperation(
                        type = SearchType.POSTS,
                        operationId = 2,
                        limit = PAGE_SIZE,
                    ),
                ),
            )
        )
        assertEquals(200, search.status)

        val personas = search.data.results
            .firstOrNull { it.operationId == 1 }
            ?.personasResult
            ?.personaWithConnectivities
            .orEmpty()
        personas.forEach {
            assertTrue(it.persona?.personaId?.isNotBlank() == true)
        }

        val posts = search.data.results
            .firstOrNull { it.operationId == 2 }
            ?.postsResult
            ?.posts
            .orEmpty()
        posts.forEach {
            assertTrue(it.postId.isNotBlank())
        }

        assertTrue(
            personas.isNotEmpty() || posts.isNotEmpty(),
            "Expected at least one persona or post for '$QUERY'",
        )

        val typeahead = client.search().searchTypeahead(SearchTypeaheadRequest(QUERY))
        assertEquals(200, typeahead.status)
        typeahead.data.items.forEach {
            assertTrue(it.persona?.personaId?.isNotBlank() == true)
        }

        println(
            "Search for '$QUERY' returned ${personas.size} persona(s), " +
                "${posts.size} post(s), and ${typeahead.data.items.size} suggestion(s)."
        )
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val QUERY = "mixi"
    }
}
