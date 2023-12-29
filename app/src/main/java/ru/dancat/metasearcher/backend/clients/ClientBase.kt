package ru.dancat.metasearcher.backend.clients

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.dancat.metasearcher.backend.ServerBadRequest
import ru.dancat.metasearcher.backend.ServerConnectionError
import ru.dancat.metasearcher.backend.models.*

class ClientBase(private val url: String) {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        expectSuccess = true
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                val clientException = exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest
                val exceptionResponse = clientException.response
                when(exceptionResponse.status){
                    HttpStatusCode.NotFound -> throw ServerBadRequest("Specified id do not found!")
                    HttpStatusCode.BadRequest -> throw ServerBadRequest("Please, check your request!")
                    HttpStatusCode.InternalServerError -> throw ServerConnectionError("The request for did not complete. Please try again later.")
                    else -> throw ServerConnectionError("Error!")
                }
            }
        }
    }

    suspend fun getMetros(): List<Metro> {
        return client.get("${url}/metro").body()
    }

    suspend fun getMetroById(id: String): Metro {
        return client.get("${url}/metro/${id}").body()
    }

    suspend fun getStyles(): List<Style> {
        return client.get("${url}/style").body()
    }

    suspend fun getStyleById(id: String): Style {
        return client.get("${url}/style/${id}").body()
    }

    suspend fun getStudios(request: StudioRequest): StudiosResponse {
        println(Json.encodeToString(request))
        return client.post("${url}/studio/getting") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                  "pageNumber": ${request.pageNumber},
                  "pageSize": ${request.pageSize},
                  "stylesList": ${request.stylesList},
                  "minimalRate": ${request.minimalRate},
                  "metroList": ${request.metroList},
                  "searchCriteria": "${request.searchCriteria}"
                }
            """.trimIndent()
            )
        }.body()
    }

    suspend fun createComment(id: String, username: String, comment: String, rate: Double) {
        client.post("${url}/studio/${id}/comment") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                  "username": "${username}",
                  "comment": "${comment}",
                  "rate": ${String.format("%.2f", rate)}
                }
            """.trimIndent()
            )
        }
    }

    suspend fun getStudioById(id: String): StudioContent {
        return client.get("${url}/studio/${id}").body()
    }

    suspend fun getStudioComment(id: String): List<Comment> {
        return client.get("${url}/studio/${id}/comment").body()
    }

    suspend fun getSocialById(id: String): Social? {
        return try{
            client.get("${url}/studio/${id}/social").body()
        } catch (e: ServerBadRequest){
            null
        }
    }
}

val client = ClientBase(
    "http://10.0.2.2:8080/api/v1"
)
