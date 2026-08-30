# Agent Documentation

## Overview

This repository is a Kotlin Multiplatform client for mixi2's undocumented web
protobuf RPC interface.

## Architecture

- `core/`: typed MercuryService client and protobuf models
- `all/`: aggregate CocoaPods, XCFramework, Swift Package, and JS distribution
- `Mixi2WebFactory`: creates direct-cookie or browser-proxy clients
- `SessionResource`: signed-in session and persona switching
- `TimelineResource`: feed, timeline, and reaction-post reads
- `PostResource`: post reads, creation, replies, quotes, bookmarks, and deletion
- `PersonaResource`: persona lookup and profile reads and updates
- `FollowResource`: followings, followers, and follow requests
- `ReactionResource`: likes, stamps, reaction counts, and reaction personas
- `NotificationResource`: notifications, unread counts, and read markers
- `ModerationResource`: blocks, mutes, and reports
- `SearchResource`: persona and post search plus typeahead
- `MediaResource`: upload preparation, binary upload, and status polling
- `RawResource`: untyped unary RPC calls

## Protocol

- Base URL:
  `https://mixi.social/api/connect/com.mixi.mercury.api.MercuryService`
- Method: `POST`
- Content type: `application/proto`
- Required authentication header: `x-auth-key`
- Web client header: `x-mercury-user-agent`
- JVM/Native sends `Cookie`; browser proxy mode sends `x-proxy-cookie`

## Testing

```shell
./gradlew :core:jvmTest
./gradlew :core:compileKotlinJs
```

Wire tests must compare encoded bytes with independently known protobuf field
numbers. Live tests must remain opt-in and must not include credentials.

## Adding an RPC

1. Confirm the method name and request/response field numbers.
2. Add `@Serializable` DTOs with explicit `@ProtoNumber` annotations.
3. Add suspend and blocking methods to the matching resource.
4. Implement the call through `MercuryClient`.
5. Add an offline wire-format test.

Keep response models tolerant by omitting unknown fields that the library does
not yet expose.
