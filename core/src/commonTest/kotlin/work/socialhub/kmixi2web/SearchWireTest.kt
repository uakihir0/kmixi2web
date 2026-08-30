package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.request.SearchRequest
import work.socialhub.kmixi2web.api.request.SearchTypeaheadRequest
import work.socialhub.kmixi2web.api.response.SearchResponse
import work.socialhub.kmixi2web.api.response.SearchTypeaheadResponse
import work.socialhub.kmixi2web.entity.SearchOperation
import work.socialhub.kmixi2web.entity.SearchPostOption
import work.socialhub.kmixi2web.entity.SearchType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class SearchWireTest {

    @Test
    fun searchRequestEncodesQueryAndOperation() {
        val encoded = wireProto.encodeToByteArray(
            SearchRequest.serializer(),
            SearchRequest(
                query = "q",
                operations = listOf(
                    SearchOperation(
                        type = SearchType.POSTS,
                        operationId = 1,
                        limit = 20,
                    ),
                ),
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x71,
                0x12, 0x06,
                0x08, 0x01,
                0x10, 0x01,
                0x28, 0x14,
            ),
            encoded,
        )
    }

    @Test
    fun searchOperationEncodesPostOptionAsNestedMessage() {
        val encoded = wireProto.encodeToByteArray(
            SearchOperation.serializer(),
            SearchOperation(
                type = SearchType.POSTS,
                operationId = 2,
                untilCursor = "c",
                postOption = SearchPostOption(
                    mediaAttachedOnly = true,
                    personaId = "p",
                ),
            ),
        )

        assertContentEquals(
            bytes(
                0x08, 0x01,
                0x10, 0x02,
                0x1A, 0x01, 0x63,
                0x5A, 0x05,
                0x08, 0x01,
                0x12, 0x01, 0x70,
            ),
            encoded,
        )
    }

    @Test
    fun searchOperationOmitsDefaultPersonaSearchType() {
        val encoded = wireProto.encodeToByteArray(
            SearchOperation.serializer(),
            SearchOperation(
                type = SearchType.PERSONAS,
                operationId = 1,
            ),
        )

        assertContentEquals(
            bytes(0x10, 0x01),
            encoded,
        )
    }

    @Test
    fun searchResponseDecodesPostAndPersonaResults() {
        val decoded = wireProto.decodeFromByteArray(
            SearchResponse.serializer(),
            bytes(
                0x0A, 0x0E,
                0x08, 0x01,
                0x12, 0x0A,
                0x0A, 0x05,
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
                0x12, 0x01, 0x63,
                0x0A, 0x0C,
                0x08, 0x02,
                0x1A, 0x08,
                0x0A, 0x03,
                0x0A, 0x01, 0x78,
                0x12, 0x01, 0x64,
            ),
        )

        val personas = decoded.results.first()
        assertEquals(1, personas.operationId)
        assertEquals(
            "p",
            personas.personasResult?.personaWithConnectivities?.single()?.persona?.personaId,
        )
        assertEquals("c", personas.personasResult?.nextCursor)

        val posts = decoded.results.last()
        assertEquals(2, posts.operationId)
        assertEquals("x", posts.postsResult?.posts?.single()?.postId)
        assertEquals("d", posts.postsResult?.nextCursor)
    }

    @Test
    fun typeaheadRequestAndResponseUseQueryAndItems() {
        val request = wireProto.encodeToByteArray(
            SearchTypeaheadRequest.serializer(),
            SearchTypeaheadRequest(query = "q"),
        )

        assertContentEquals(
            bytes(0x0A, 0x01, 0x71),
            request,
        )

        val decoded = wireProto.decodeFromByteArray(
            SearchTypeaheadResponse.serializer(),
            bytes(
                0x0A, 0x07,
                0x08, 0x00,
                0x12, 0x03,
                0x0A, 0x01, 0x70,
            ),
        )

        assertEquals(0, decoded.items.single().itemType)
        assertEquals("p", decoded.items.single().persona?.personaId)
    }
}
