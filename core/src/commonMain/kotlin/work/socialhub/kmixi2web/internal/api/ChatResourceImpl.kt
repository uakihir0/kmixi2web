package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.ChatResource
import work.socialhub.kmixi2web.api.request.GetChatRoomMessagesRequest
import work.socialhub.kmixi2web.api.request.GetChatRoomRequest
import work.socialhub.kmixi2web.api.request.GetChatRoomsRequest
import work.socialhub.kmixi2web.api.request.GetUnreadChatRoomCountRequest
import work.socialhub.kmixi2web.api.request.SendDirectMessageRequest
import work.socialhub.kmixi2web.api.request.SendGroupMessageRequest
import work.socialhub.kmixi2web.api.request.SendMessageToRoomRequest
import work.socialhub.kmixi2web.api.response.GetChatRoomMessagesResponse
import work.socialhub.kmixi2web.api.response.GetChatRoomResponse
import work.socialhub.kmixi2web.api.response.GetChatRoomsResponse
import work.socialhub.kmixi2web.api.response.GetUnreadChatRoomCountResponse
import work.socialhub.kmixi2web.api.response.SendDirectMessageResponse
import work.socialhub.kmixi2web.api.response.SendGroupMessageResponse
import work.socialhub.kmixi2web.api.response.SendMessageToRoomResponse
import work.socialhub.kmixi2web.entity.share.Response
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class ChatResourceImpl(
    private val client: MercuryClient,
) : ChatResource {
    override suspend fun getChatRooms(
        request: GetChatRoomsRequest,
    ): Response<GetChatRoomsResponse> {
        return client.call(
            "GetChatRooms",
            request,
            GetChatRoomsRequest.serializer(),
            GetChatRoomsResponse.serializer(),
        )
    }

    override fun getChatRoomsBlocking(
        request: GetChatRoomsRequest,
    ) = toBlocking { getChatRooms(request) }

    override suspend fun getChatRoom(
        request: GetChatRoomRequest,
    ): Response<GetChatRoomResponse> {
        return client.call(
            "GetChatRoom",
            request,
            GetChatRoomRequest.serializer(),
            GetChatRoomResponse.serializer(),
        )
    }

    override fun getChatRoomBlocking(
        request: GetChatRoomRequest,
    ) = toBlocking { getChatRoom(request) }

    override suspend fun getChatRoomMessages(
        request: GetChatRoomMessagesRequest,
    ): Response<GetChatRoomMessagesResponse> {
        return client.call(
            "GetChatRoomMessages",
            request,
            GetChatRoomMessagesRequest.serializer(),
            GetChatRoomMessagesResponse.serializer(),
        )
    }

    override fun getChatRoomMessagesBlocking(
        request: GetChatRoomMessagesRequest,
    ) = toBlocking { getChatRoomMessages(request) }

    override suspend fun getUnreadChatRoomCount(
        request: GetUnreadChatRoomCountRequest,
    ): Response<GetUnreadChatRoomCountResponse> {
        return client.call(
            "GetUnreadChatRoomCount",
            request,
            GetUnreadChatRoomCountRequest.serializer(),
            GetUnreadChatRoomCountResponse.serializer(),
        )
    }

    override fun getUnreadChatRoomCountBlocking(
        request: GetUnreadChatRoomCountRequest,
    ) = toBlocking { getUnreadChatRoomCount(request) }

    override suspend fun sendMessageToRoom(
        request: SendMessageToRoomRequest,
    ): Response<SendMessageToRoomResponse> {
        return client.call(
            "SendMessageToRoom",
            request,
            SendMessageToRoomRequest.serializer(),
            SendMessageToRoomResponse.serializer(),
        )
    }

    override fun sendMessageToRoomBlocking(
        request: SendMessageToRoomRequest,
    ) = toBlocking { sendMessageToRoom(request) }

    override suspend fun sendDirectMessage(
        request: SendDirectMessageRequest,
    ): Response<SendDirectMessageResponse> {
        return client.call(
            "SendDirectMessage",
            request,
            SendDirectMessageRequest.serializer(),
            SendDirectMessageResponse.serializer(),
        )
    }

    override fun sendDirectMessageBlocking(
        request: SendDirectMessageRequest,
    ) = toBlocking { sendDirectMessage(request) }

    override suspend fun sendGroupMessage(
        request: SendGroupMessageRequest,
    ): Response<SendGroupMessageResponse> {
        return client.call(
            "SendGroupMessage",
            request,
            SendGroupMessageRequest.serializer(),
            SendGroupMessageResponse.serializer(),
        )
    }

    override fun sendGroupMessageBlocking(
        request: SendGroupMessageRequest,
    ) = toBlocking { sendGroupMessage(request) }
}
