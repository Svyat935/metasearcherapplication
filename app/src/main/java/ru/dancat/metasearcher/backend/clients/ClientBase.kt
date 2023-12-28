package ru.dancat.metasearcher.backend.clients

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.dancat.metasearcher.backend.ServerBadRequest
import ru.dancat.metasearcher.backend.ServerConnectionError
import ru.dancat.metasearcher.backend.models.*

//@OptIn(DelicateCoroutinesApi::class, InternalAPI::class)
//GlobalScope

//TODO: Decorator for each function (handle 400, 500 errors)
class ClientBase(private val url: String) {
    private val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getMetros(): List<Metro> {
        val data: HttpResponse = client.get("${url}/metro/")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for metros pickup did not complete. Please try again later."
            )
        }

        return data.body()
    }

    suspend fun getMetroById(id: String): Metro {
        val data: HttpResponse = client.get("${url}/metro/${id}")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for metros pickup did not complete. Please try again later."
            )
        }
        if (data.status.value == 404){
            throw ServerBadRequest("Specified id do not found.")
        }

        return data.body()
    }

    suspend fun getStyles(): List<Style>{
        val data: HttpResponse = client.get("${url}/style/")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for dance-styles pickup did not complete. Please try again later."
            )
        }

        return data.body()
    }

    suspend fun getStyleById(id: String): Style {
        val data: HttpResponse = client.get("${url}/style/${id}")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for metros pickup did not complete. Please try again later."
            )
        }
        if (data.status.value == 404){
            throw ServerBadRequest("Specified id do not found.")
        }

        return data.body()
    }

    suspend fun getStudios(request: StudioRequest): StudiosResponse{
        println("""
                {
                  "pageNumber": ${request.pageNumber},
                  "pageSize": ${request.pageSize},
                  "stylesList": ${request.stylesList},
                  "minimalRate": ${request.minimalRate},
                  "metroList": ${request.metroList},
                  "searchCriteria": ${request.searchCriteria}
                }
            """.trimIndent())
        val data: HttpResponse = client.post("${url}/studio/getting"){
            contentType(ContentType.Application.Json)
            setBody("""
                {
                  "pageNumber": ${request.pageNumber},
                  "pageSize": ${request.pageSize},
                  "stylesList": ${request.stylesList},
                  "minimalRate": ${request.minimalRate},
                  "metroList": ${request.metroList},
                  "searchCriteria": "${request.searchCriteria}"
                }
            """.trimIndent())
        }

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for studios pickup did not complete. Please try again later."
            )
        }
        if (data.status.value == 400){
            throw ServerBadRequest(
                "Bad Request. Please check your request.",
                request.javaClass // May be i can change on data class type
            )
        }

        return data.body()
    }

    suspend fun getStudioById(id: String): StudioContent{
        val data: HttpResponse = client.get("${url}/studio/${id}")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for a studio by id did not complete. Please try again later."
            )
        }

        return data.body()
    }

    suspend fun getStudioComment(id: String): List<Comment> {
        val data: HttpResponse = client.get("${url}/studio/${id}/comment")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for comments of studio by id did not complete. Please try again later."
            )
        }

        return data.body()
    }
}

val client = ClientBase(
    "http://10.0.2.2:8080/api/v1"
)
