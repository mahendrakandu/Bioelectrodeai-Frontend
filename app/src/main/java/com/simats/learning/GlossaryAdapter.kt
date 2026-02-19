package com.simats.learning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GlossaryAdapter(private val glossaryTerms: List<GlossaryTerm>) :
    RecyclerView.Adapter<GlossaryAdapter.GlossaryViewHolder>() {

    class GlossaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivTermIcon)
        val tvTerm: TextView = itemView.findViewById(R.id.tvTermName)
        val tvDefinition: TextView = itemView.findViewById(R.id.tvTermDefinition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlossaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_glossary_term, parent, false)
        return GlossaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GlossaryViewHolder, position: Int) {
        val term = glossaryTerms[position]
        holder.ivIcon.setImageResource(term.iconResId)
        holder.tvTerm.text = term.term
        holder.tvDefinition.text = term.definition
    }

    override fun getItemCount(): Int = glossaryTerms.size
}
