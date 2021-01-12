package kotlin.com.siap.panoramaagroalimentario.shared

import com.siap.panoramaagroalimentario.shared.cache.Database
import com.siap.panoramaagroalimentario.shared.cache.DatabaseDriverFactory
import com.siap.panoramaagroalimentario.shared.entity.Produccion
import com.siap.panoramaagroalimentario.shared.network.MapotecaApi

class Mapoteca (databaseDriverFactory: DatabaseDriverFactory){

    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class) suspend fun getProduccion(forceReload: Boolean): List<Produccion> {
        val cachedProduccion = database.getAllProduccion()
        return if (cachedProduccion.isNotEmpty() && !forceReload) {
            cachedProduccion
        } else {
            api.getAllProduccion().also {
                database.clearDatabase()
                database.createProduccion(it)
            }
        }
    }
}