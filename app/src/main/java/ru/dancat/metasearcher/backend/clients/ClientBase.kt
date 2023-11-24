package ru.dancat.metasearcher.backend.clients

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.dancat.metasearcher.backend.ServerBadRequest
import ru.dancat.metasearcher.backend.ServerConnectionError
import ru.dancat.metasearcher.backend.models.Metro
import ru.dancat.metasearcher.backend.models.StudioRequest
import ru.dancat.metasearcher.backend.models.Style

//@OptIn(DelicateCoroutinesApi::class, InternalAPI::class)
//GlobalScope

//TODO: Decorator for each function (handle 400, 500 errors)
class ClientBase(private val url: String) {
    private val client = HttpClient(Android){
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getMetros(): List<Metro> {
        val data: HttpResponse = this.client.get("${this.url}/metro/")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for metros pickup did not complete. Please try again later."
            )
        }

        return data.body()
    }

    suspend fun getStyles(): List<Style>{
        val data: HttpResponse = this.client.get("${this.url}/style")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for dance-styles pickup did not complete. Please try again later."
            )
        }

        return data.body()
    }

    suspend fun studios(request: StudioRequest): List<Style>{
        val data: HttpResponse = this.client.get("${this.url}/studio")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for studios pickup did not complete. Please try again later."
            )
        }
        if (data.status.value == 400){
            throw ServerBadRequest(
                "Bad Request. Please check your request.",
                request.toString() // May be i can change on data class type
            )
        }

        return data.body()
    }

    suspend fun studioById(id: String): Style{
        val data: HttpResponse = this.client.get("${this.url}/studio/${id}")

        if (data.status.value == 500){
            throw ServerConnectionError(
                "The request for a studio by id did not complete. Please try again later."
            )
        }

        return data.body()
    }
}

val client = ClientBase(
    "http://localhost:8080/api/v1"
)
