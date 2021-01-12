package kotlin.com.siap.panoramaagroalimentario.shared.network

import com.siap.panoramaagroalimentario.shared.entity.Produccion
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class MapotecaApi {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAllProduccion(): List<Produccion> {
        
        return httpClient.get(LAUNCHES_ENDPOINT)

    }

    companion object {
        private const val LAUNCHES_ENDPOINT = "https://api.spacexdata.com/v3/launches"
    }
}