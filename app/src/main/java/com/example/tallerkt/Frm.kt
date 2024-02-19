package com.example.tallerkt

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.GREEN
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.tallerkt.data.Tarea
import com.google.android.material.textfield.TextInputEditText
import com.orm.SugarRecord

class Frm : AppCompatActivity() {
    lateinit var txtNombre: TextInputEditText
    lateinit var txtDescripcion: TextInputEditText
    lateinit var txtEstado: TextInputEditText
    lateinit var spinner: Spinner
    private var tareaIds: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frm)
        txtNombre = findViewById(R.id.txtNombre)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        txtEstado = findViewById(R.id.txtEstado)
        spinner = findViewById(R.id.spnId)
        cargarTareaIds()
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tareaIds)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setSelection(0)
        spinner.adapter = adapter
    }

    fun cargarTareaIds() {
        tareaIds.clear()
        tareaIds.add(0, "Selecciona un ID")
        var todos: List<Tarea> = SugarRecord.listAll(Tarea::class.java)
        for (tarea in todos.reversed()) {
            tareaIds.add(tarea.id.toString())
        }
    }

    fun listar(b:View){
        cargarTareaIds()
        val intent = Intent(applicationContext, ListarActivity::class.java)
        startActivity(intent)
        txtNombre.text?.clear()
        txtDescripcion.text?.clear()
        txtEstado.text?.clear()
        spinner.setSelection(0)
    }

    fun mostrar(b: View) {
        try {
            if (spinner.selectedItem.toString().toLong() > 0) {
                val spnId = spinner.selectedItem.toString().toLong()
                val tarea = SugarRecord.findById(Tarea::class.java, spnId)
                if (tarea != null) {
                    txtNombre.setText(tarea.nombre.toString())
                    txtDescripcion.setText(tarea.descripcion.toString())
                    txtEstado.setText((tarea.completado.toString()))
                    when (txtEstado.text.toString()) {
                        "Pendiente!" -> {
                            txtEstado.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPendienteBackground))
                            txtEstado.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPendienteText))
                        }
                        "Actualizada!" -> {
                            txtEstado.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorActualizadoBackground))
                            txtEstado.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorActualizadoText))
                        }
                        "Completado!" -> {
                            txtEstado.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorCompletadoBackground))
                            txtEstado.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorCompletadoText))
                        }
                    }

                } else {
                    Toast.makeText(applicationContext, "No se encontró una tarea con el ID seleccionado.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Debes seleccionar un ID del listado.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(applicationContext, "Debes seleccionar un ID del listado.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Ocurrió un error al buscar la tarea.", Toast.LENGTH_SHORT).show()
        }
        cargarTareaIds()
        (spinner.adapter as ArrayAdapter<String>).notifyDataSetChanged()
    }

    fun guardar(b: View) {
        try {
            if (txtNombre.text.toString().isNotEmpty() && txtDescripcion.text.toString().isNotEmpty()) {
                val tarea = Tarea(txtNombre.text.toString(), txtDescripcion.text.toString(), "Pendiente!")
                tarea.save()
                Toast.makeText(applicationContext, "Guardado OK!..", Toast.LENGTH_SHORT).show()
                txtNombre.text?.clear()
                txtDescripcion.text?.clear()
                txtEstado.text?.clear()
            } else {
                Toast.makeText(applicationContext, "No se puede guardar tienes campos vacíos!..", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Ocurrió un error al guardar la tarea.", Toast.LENGTH_SHORT).show()
        }
        cargarTareaIds()
        (spinner.adapter as ArrayAdapter<String>).notifyDataSetChanged()
        spinner.setSelection(0)
    }


    fun actualizar(b: View) {
        try {
            if (spinner.selectedItem.toString().toLong() > 0) {
                val spnId = spinner.selectedItem.toString().toLong()
                val tarea = SugarRecord.findById(Tarea::class.java, spnId)
                if (tarea != null) {
                    Toast.makeText(applicationContext, "Escogiste: " + tarea.nombre.toString(), Toast.LENGTH_SHORT).show()
                    tarea.nombre = txtNombre.text.toString()
                    tarea.descripcion = txtDescripcion.text.toString()
                    tarea.completado = "Actualizada!"
                    tarea.save()
                    Toast.makeText(applicationContext, "Actualizado OK!..", Toast.LENGTH_SHORT).show()
                    txtEstado.setText("Tarea ID: "+spnId.toString() + " Actualizada")
                } else {
                    Toast.makeText(applicationContext, "No se encontró una tarea con el ID seleccionado.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Debes seleccionar un número del listado.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(applicationContext, "Debes seleccionar un número del listado.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Ocurrió un error al actualizar la tarea.", Toast.LENGTH_SHORT).show()
        }
        txtNombre.text?.clear()
        txtDescripcion.text?.clear()
        spinner.setSelection(0)
    }


    fun eliminar(b: View) {
        try {
            if (spinner.selectedItem.toString().toLong() > 0) {
                val spnId = spinner.selectedItem.toString().toLong()
                val tarea = SugarRecord.findById(Tarea::class.java, spnId)
                if (tarea != null) {
                    Toast.makeText(applicationContext, "Escogiste: " + tarea.nombre.toString(), Toast.LENGTH_SHORT).show()
                    tarea.delete()
                    Toast.makeText(applicationContext, "Eliminado OK..", Toast.LENGTH_SHORT).show()
                    txtEstado.setText("Tarea ID: " + spnId.toString() + " Eliminada")
                } else {
                    Toast.makeText(applicationContext, "No se encontró una tarea con el ID seleccionado.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Debes seleccionar un número del listado.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(applicationContext, "Debes seleccionar un número del listado.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Ocurrió un error al eliminar la tarea.", Toast.LENGTH_SHORT).show()
        }
        txtNombre.text?.clear()
        txtDescripcion.text?.clear()
        cargarTareaIds()
        (spinner.adapter as ArrayAdapter<String>).notifyDataSetChanged()
        spinner.setSelection(0)
    }

    fun completar(b: View) {
        val spnId = spinner.selectedItem.toString().toLong()
        val tarea = SugarRecord.findById(Tarea::class.java, spnId)
        Toast.makeText(applicationContext, tarea.nombre.toString(), Toast.LENGTH_SHORT).show()
        tarea.completado = "Completado!"
        tarea.save()
        txtEstado.setText("Tarea ID: " + spnId.toString() + "Completada")
        txtNombre.text?.clear()
        txtDescripcion.text?.clear()
        Toast.makeText(applicationContext, "Tarea Completada OK..", Toast.LENGTH_SHORT).show()
    }
}