package kotlin.com.siap.panoramaagroalimentario.shared.cache

import com.siap.panoramaagroalimentario.shared.cache.AppDatabase
import com.siap.panoramaagroalimentario.shared.entity.Producto
import com.siap.panoramaagroalimentario.shared.entity.Produccion

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllProductos()
            dbQuery.removeAllProduccion()
        }
    }

    internal fun getAllProduccion(): List<Produccion> {
        return dbQuery.selectProduccionByProducto(::mapProduccionSelecting).executeAsList()
    }

    private fun mapProduccionSelecting(
        aniovolumen: Int,
        volumenproduccion: Int,
        productoId: String,
        producto_id: String?,
        nombre: String,
        anioatlas: Int,
        descripcion: String?
    ):Produccion{
        return Produccion(
            aniovolumen = aniovolumen,
            volumenproduccion = volumenproduccion,
            producto = Producto(
                id = productoId,
                nombre = nombre,
                anioatlas = anioatlas,
                descripcion = descripcion
            )
        )
    }

    internal fun createGrafica(grafica: List<Produccion>) {
        dbQuery.transaction {
            grafica.forEach { grap ->
                val producto = dbQuery.selectProductoById(grap.producto.id).executeAsOneOrNull()
                if (producto == null) {
                    insertProducto(grap)
                }

                insertProduccion(grap)
            }
        }
    }

    private fun insertProducto(grap: Produccion){
        dbQuery.insertProducto(
            id = grap.producto.id,
            nombre = grap.producto.nombre,
            anioatlas = grap.producto.anioatlas,
            descripcion = grap.producto.descripcion
        )
    }

    private fun insertProduccion(grap: Produccion){
        dbQuery.insertProduccion(
            aniovolumen = grap.aniovolumen,
            volumenproduccion = grap.volumenproduccion,
            productoid = grap.producto.id
        )
    }

}