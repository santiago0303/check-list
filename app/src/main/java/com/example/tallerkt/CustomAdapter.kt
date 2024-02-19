package com.example.tallerkt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tallerkt.data.Tarea

class CustomAdapter(private val mList: List<Tarea>, private val completarListener: CompletarListener)
    : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    interface CompletarListener {
        fun completar(position: Long)
        fun elimin(position: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plantilla, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id1: TextView = itemView.findViewById(R.id.txtIdCard)
        val nombre1: TextView = itemView.findViewById(R.id.txtNombreCard)
        val descripcion1: TextView = itemView.findViewById(R.id.txtDescCard)
        val completado1: TextView = itemView.findViewById(R.id.txtCompleteCard)
        val bt: Button = itemView.findViewById(R.id.btnComplete)
        val bt2: Button = itemView.findViewById(R.id.btnElim)


        init {
            bt.setOnClickListener {
                val position = adapterPosition
                val id = mList[position].id
                completarListener.completar(id)
            }
            bt2.setOnClickListener {
                val position = adapterPosition
                val id = mList[position].id
                completarListener.elimin(id)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tareaz = mList[position]
        holder.id1.text = "ID: " + tareaz.id.toString()
        holder.nombre1.text = tareaz.nombre
        holder.descripcion1.text = tareaz.descripcion
        holder.completado1.text = tareaz.completado

        when (tareaz.completado.toString()) {
            "Pendiente!" -> {
                holder.completado1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPendienteBackground))
                holder.completado1.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPendienteText))
            }
            "Actualizada!" -> {
                holder.completado1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorActualizadoBackground))
                holder.completado1.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorActualizadoText))
            }
            "Completado!" -> {
                holder.completado1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorCompletadoBackground))
                holder.completado1.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorCompletadoText))
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
