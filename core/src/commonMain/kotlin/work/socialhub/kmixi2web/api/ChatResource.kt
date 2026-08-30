package work.socialhub.kmixi2web.api

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
import kotlin.js.JsExport

@JsExport
interface ChatResource {
    suspend fun getChatRooms(
        request: GetChatRoomsRequest,
    ): Response<GetChatRoomsResponse>

    @JsExport.Ignore
    fun getChatRoomsBlocking(
        request: GetChatRoomsRequest,
    ): Response<GetChatRoomsResponse>

    suspend fun getChatRoom(
        request: GetChatRoomRequest,
    ): Response<GetChatRoomResponse>

    @JsExport.Ignore
    fun getChatRoomBlocking(
        request: GetChatRoomRequest,
    ): Response<GetChatRoomResponse>

    suspend fun getChatRoomMessages(
        request: GetChatRoomMessagesRequest,
    ): Response<GetChatRoomMessagesResponse>

    @JsExport.Ignore
    fun getChatRoomMessagesBlocking(
        request: GetChatRoomMessagesRequest,
    ): Response<GetChatRoomMessagesResponse>

    suspend fun getUnreadChatRoomCount(
        request: GetUnreadChatRoomCountRequest = GetUnreadChatRoomCountRequest(),
    ): Response<GetUnreadChatRoomCountResponse>

    @JsExport.Ignore
    fun getUnreadChatRoomCountBlocking(
        request: GetUnreadChatRoomCountRequest = GetUnreadChatRoomCountRequest(),
    ): Response<GetUnreadChatRoomCountResponse>

    /** Sends to an existing room. */
    suspend fun sendMessageToRoom(
        request: SendMessageToRoomRequest,
    ): Response<SendMessageToRoomResponse>

    @JsExport.Ignore
    fun sendMessageToRoomBlocking(
        request: SendMessageToRoomRequest,
    ): Response<SendMessageToRoomResponse>

    /**
     * Sends to a persona, creating the one-to-one room when it does not exist.
     * The room ID is on the returned message.
     */
    suspend fun sendDirectMessage(
        request: SendDirectMessageRequest,
    ): Response<SendDirectMessageResponse>

    @JsExport.Ignore
    fun sendDirectMessageBlocking(
        request: SendDirectMessageRequest,
    ): Response<SendDirectMessageResponse>

    /** Sends to several personas at once, creating a group room. */
    suspend fun sendGroupMessage(
        request: SendGroupMessageRequest,
    ): Response<SendGroupMessageResponse>

    @JsExport.Ignore
    fun sendGroupMessageBlocking(
        request: SendGroupMessageRequest,
    ): Response<SendGroupMessageResponse>
}
