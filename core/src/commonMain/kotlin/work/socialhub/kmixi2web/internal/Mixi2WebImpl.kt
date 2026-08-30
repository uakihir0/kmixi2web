package work.socialhub.kmixi2web.internal

import work.socialhub.kmixi2web.Mixi2Web
import work.socialhub.kmixi2web.Mixi2WebConfig
import work.socialhub.kmixi2web.api.FollowResource
import work.socialhub.kmixi2web.api.ModerationResource
import work.socialhub.kmixi2web.api.NotificationResource
import work.socialhub.kmixi2web.api.PersonaResource
import work.socialhub.kmixi2web.api.PostResource
import work.socialhub.kmixi2web.api.RawResource
import work.socialhub.kmixi2web.api.ReactionResource
import work.socialhub.kmixi2web.api.SearchResource
import work.socialhub.kmixi2web.api.SessionResource
import work.socialhub.kmixi2web.api.TimelineResource
import work.socialhub.kmixi2web.internal.api.FollowResourceImpl
import work.socialhub.kmixi2web.internal.api.ModerationResourceImpl
import work.socialhub.kmixi2web.internal.api.NotificationResourceImpl
import work.socialhub.kmixi2web.internal.api.PersonaResourceImpl
import work.socialhub.kmixi2web.internal.api.PostResourceImpl
import work.socialhub.kmixi2web.internal.api.RawResourceImpl
import work.socialhub.kmixi2web.internal.api.ReactionResourceImpl
import work.socialhub.kmixi2web.internal.api.SearchResourceImpl
import work.socialhub.kmixi2web.internal.api.SessionResourceImpl
import work.socialhub.kmixi2web.internal.api.TimelineResourceImpl

internal class Mixi2WebImpl(
    config: Mixi2WebConfig,
) : Mixi2Web {
    private val client = MercuryClient(config)
    private val session = SessionResourceImpl(client)
    private val timeline = TimelineResourceImpl(client)
    private val post = PostResourceImpl(client)
    private val persona = PersonaResourceImpl(client)
    private val follow = FollowResourceImpl(client)
    private val reaction = ReactionResourceImpl(client)
    private val notification = NotificationResourceImpl(client)
    private val moderation = ModerationResourceImpl(client)
    private val search = SearchResourceImpl(client)
    private val raw = RawResourceImpl(client)

    override fun session(): SessionResource = session
    override fun timeline(): TimelineResource = timeline
    override fun post(): PostResource = post
    override fun persona(): PersonaResource = persona
    override fun follow(): FollowResource = follow
    override fun reaction(): ReactionResource = reaction
    override fun notification(): NotificationResource = notification
    override fun moderation(): ModerationResource = moderation
    override fun search(): SearchResource = search
    override fun raw(): RawResource = raw
}
