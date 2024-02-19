package com.example.tallerkt

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tallerkt.data.Tarea
import com.orm.SugarRecord

class ListarActivity : AppCompatActivity(), CustomAdapter.CompletarListener {
    lateinit var lista: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)
        lista = findViewById(R.id.rv_lista)
        listarTareas()
    }

    override fun completar(position: Long) {
        val tarea = SugarRecord.findById(Tarea::class.java, position)
        Toast.makeText(applicationContext, tarea.nombre.toString(), Toast.LENGTH_SHORT).show()
        tarea.completado = "Completado!"
        tarea.save()
        Toast.makeText(applicationContext, "Tarea Completada OK..", Toast.LENGTH_SHORT).show()
        listarTareas()
    }

     override fun elimin(position: Long) {
         val tarea = SugarRecord.findById(Tarea::class.java, position)
         Toast.makeText(applicationContext, tarea.nombre.toString(), Toast.LENGTH_SHORT).show()
         tarea.delete()
         Toast.makeText(applicationContext, "Tarea Eliminada OK..", Toast.LENGTH_SHORT).show()
         listarTareas()
    }

        fun listarTareas() {
        lista.layoutManager = LinearLayoutManager(applicationContext)
        var todos: MutableIterator<Tarea>? = SugarRecord.findAll(Tarea::class.java)
        val listaTareas: List<Tarea> = todos?.asSequence()?.toList()?.reversed()?: emptyList()
        val adapter = CustomAdapter(listaTareas, this)
        lista.adapter = adapter
    }
}
