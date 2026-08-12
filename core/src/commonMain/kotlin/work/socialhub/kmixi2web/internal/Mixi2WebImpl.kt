package work.socialhub.kmixi2web.internal

import work.socialhub.kmixi2web.Mixi2Web
import work.socialhub.kmixi2web.Mixi2WebConfig
import work.socialhub.kmixi2web.api.PersonaResource
import work.socialhub.kmixi2web.api.PostResource
import work.socialhub.kmixi2web.api.RawResource
import work.socialhub.kmixi2web.api.ReactionResource
import work.socialhub.kmixi2web.api.TimelineResource
import work.socialhub.kmixi2web.internal.api.PersonaResourceImpl
import work.socialhub.kmixi2web.internal.api.PostResourceImpl
import work.socialhub.kmixi2web.internal.api.RawResourceImpl
import work.socialhub.kmixi2web.internal.api.ReactionResourceImpl
import work.socialhub.kmixi2web.internal.api.TimelineResourceImpl

internal class Mixi2WebImpl(
    config: Mixi2WebConfig,
) : Mixi2Web {
    private val client = MercuryClient(config)
    private val timeline = TimelineResourceImpl(client)
    private val post = PostResourceImpl(client)
    private val persona = PersonaResourceImpl(client)
    private val reaction = ReactionResourceImpl(client)
    private val raw = RawResourceImpl(client)

    override fun timeline(): TimelineResource = timeline
    override fun post(): PostResource = post
    override fun persona(): PersonaResource = persona
    override fun reaction(): ReactionResource = reaction
    override fun raw(): RawResource = raw
}
