package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.request.VotePollRequest
import work.socialhub.kmixi2web.api.response.GetPostResponse
import work.socialhub.kmixi2web.api.response.VotePollResponse
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PollWireTest {

    @Test
    fun postDecodesPoll() {
        // field 40 = Poll { poll_id: "q", options: [{ index: 1, text: "a", vote_count: 2, percentage: 50.0 }],
        //                   total_votes: 4, can_view_results: true, voted_option_index: 1 }
        val decoded = wireProto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x1D,
                0x0A, 0x01, 0x70,
                0xC2, 0x02, 0x17,
                0x0A, 0x01, 0x71,
                0x12, 0x0C,
                0x08, 0x01,
                0x12, 0x01, 0x61,
                0x18, 0x02,
                0x25, 0x00, 0x00, 0x48, 0x42,
                0x18, 0x04,
                0x30, 0x01,
                0x38, 0x01,
            ),
        )

        val poll = assertNotNull(decoded.post?.poll)
        assertEquals("q", poll.pollId)
        assertEquals(4L, poll.totalVotes)
        assertFalse(poll.isClosed)
        assertTrue(poll.canViewResults)
        assertEquals(1, poll.votedOptionIndex)
        val option = poll.options.single()
        assertEquals(1, option.index)
        assertEquals("a", option.text)
        assertEquals(2L, option.voteCount)
        assertEquals(50.0f, option.percentage)
    }

    @Test
    fun postWithoutPollHasNoPoll() {
        val decoded = wireProto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
            ),
        )

        assertNull(decoded.post?.poll)
    }

    @Test
    fun votePollRequestEncodesPostIdAndOptionIndex() {
        val encoded = wireProto.encodeToByteArray(
            VotePollRequest.serializer(),
            VotePollRequest(postId = "p", optionIndex = 2),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x70,
                0x10, 0x02,
            ),
            encoded,
        )
    }

    @Test
    fun votePollResponseDecodesUnvotedPoll() {
        // A closed poll whose results are still hidden; can_view_results is authoritative
        val decoded = wireProto.decodeFromByteArray(
            VotePollResponse.serializer(),
            bytes(
                0x0A, 0x05,
                0x0A, 0x01, 0x71,
                0x28, 0x01,
            ),
        )

        val poll = assertNotNull(decoded.poll)
        assertEquals("q", poll.pollId)
        assertTrue(poll.isClosed)
        assertFalse(poll.canViewResults)
        assertNull(poll.votedOptionIndex)
    }
}
