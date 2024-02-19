package com.example.tallerkt.data

import com.orm.SugarRecord

class Tarea : SugarRecord<Tarea?> {
    var nombre: String? = null
    var descripcion: String? = null
    var completado: String? = null

    constructor()
    constructor(nombre: String?, descripcion: String?, completado: String?) {
        this.nombre = nombre
        this.descripcion = descripcion
        this.completado = completado
    }
}