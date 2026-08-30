package work.socialhub.kmixi2web.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.timeout
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import work.socialhub.kmixi2web.Mixi2WebConfig
import work.socialhub.kmixi2web.Mixi2WebException
import work.socialhub.kmixi2web.entity.share.Response

internal class MercuryClient(
    private val config: Mixi2WebConfig,
) {
    private val contentType = ContentType("application", "proto")
    private val proto = ProtoBuf {
        encodeDefaults = false
    }

    suspend fun <Request, Result> call(
        rpcName: String,
        request: Request,
        requestSerializer: KSerializer<Request>,
        resultSerializer: KSerializer<Result>,
    ): Response<Result> {
        val requestBody = proto.encodeToByteArray(requestSerializer, request)
        val raw = callRaw(rpcName, requestBody)
        val result = try {
            proto.decodeFromByteArray(resultSerializer, raw.data)
        } catch (e: Exception) {
            throw Mixi2WebException(
                message = "Failed to decode $rpcName response: ${e.message}",
                cause = e,
                status = raw.status,
                responseBody = raw.bytes,
            )
        }
        return Response(result, raw.status, raw.bytes)
    }

    suspend fun callRaw(
        rpcName: String,
        requestBody: ByteArray,
    ): Response<ByteArray> {
        require(RPC_NAME.matches(rpcName)) {
            "rpcName must contain only ASCII letters and digits"
        }

        val url = "${config.apiBaseUri.trimEnd('/')}/$rpcName"
        val result = try {
            execute(url, requestBody)
        } catch (e: Exception) {
            throw e as? Mixi2WebException
                ?: Mixi2WebException(
                    message = "Mercury request failed: ${e.message}",
                    cause = e,
                )
        }

        if (result.status !in 200..299) {
            val detail = result.grpcMessage
                ?: result.body.toReadableText()
                ?: "HTTP ${result.status}"
            throw Mixi2WebException(
                message = "$rpcName failed: $detail",
                status = result.status,
                responseBody = result.body,
            )
        }

        return Response(result.body, result.status, result.body)
    }

    /**
     * Sends raw bytes to an upload target returned by `PrepareMediaUploading`.
     * The target is a presigned storage URL, so the Mercury authentication
     * headers are deliberately not attached.
     */
    suspend fun upload(
        url: String,
        method: String,
        headers: Map<String, String>,
        body: ByteArray,
    ): Int {
        val client = HttpClient {
            expectSuccess = false
            install(HttpTimeout)
        }
        val status = try {
            val response = client.request(url) {
                this.method = HttpMethod.parse(method.ifBlank { "PUT" })
                headers.forEach { (key, value) -> header(key, value) }
                timeout {
                    requestTimeoutMillis = config.requestTimeoutMillis
                    connectTimeoutMillis = config.connectTimeoutMillis
                    socketTimeoutMillis = config.socketTimeoutMillis
                }
                setBody(body)
            }
            response.status.value
        } catch (e: Exception) {
            throw Mixi2WebException(
                message = "Media upload failed: ${e.message}",
                cause = e,
            )
        } finally {
            client.close()
        }

        if (status !in 200..299) {
            throw Mixi2WebException(
                message = "Media upload failed: HTTP $status",
                status = status,
            )
        }
        return status
    }

    private suspend fun execute(
        url: String,
        requestBody: ByteArray,
    ): HttpResult {
        val client = HttpClient {
            expectSuccess = false
            install(HttpTimeout)
        }
        return try {
            val response = client.post(url) {
                accept(contentType)
                header("content-type", contentType.toString())
                header("x-auth-key", config.authKey)
                header("x-mercury-user-agent", config.mercuryUserAgent)
                if (config.cookie.isNotBlank()) {
                    header(
                        if (config.useProxyCookieHeader) "x-proxy-cookie" else "cookie",
                        config.cookie,
                    )
                }
                timeout {
                    requestTimeoutMillis = config.requestTimeoutMillis
                    connectTimeoutMillis = config.connectTimeoutMillis
                    socketTimeoutMillis = config.socketTimeoutMillis
                }
                setBody(requestBody)
            }
            HttpResult(
                status = response.status.value,
                grpcMessage = response.headers["grpc-message"]
                    ?: response.headers["connect-error-message"],
                body = response.body(),
            )
        } finally {
            client.close()
        }
    }

    private fun ByteArray.toReadableText(): String? {
        if (isEmpty()) return null
        return try {
            decodeToString().takeIf { text ->
                text.isNotBlank() && text.all { it == '\n' || it == '\r' || it == '\t' || !it.isISOControl() }
            }
        } catch (_: Exception) {
            null
        }
    }

    private class HttpResult(
        val status: Int,
        val grpcMessage: String?,
        val body: ByteArray,
    )

    companion object {
        private val RPC_NAME = Regex("[A-Za-z][A-Za-z0-9]*")
    }
}
