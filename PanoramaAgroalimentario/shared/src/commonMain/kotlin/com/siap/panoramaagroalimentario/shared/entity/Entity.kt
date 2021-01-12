package com.siap.panoramaagroalimentario.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    @SerialName("producto_id")
    val id: String,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("anio_atlas")
    val anioatlas: Int,
    @SerialName("descripcion")
    val descripcion: String?
    )

@Serializable
data class Produccion (
    @SerialName("anio_volumen")
    val aniovolumen: Int,
    @SerialName("volumen_produccion")
    val volumenproduccion: Int,
    @SerialName("producto")
    val producto: Producto
    )
