package it.uniparthenope.studenti.francescobottino001.meteouniparthenope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Places
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Places.Place

class PlacesAdapter(private var dataSet: ArrayList<Place>, private val onItemClick: (place:Place ) -> Unit) : RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {
    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.places_list_item, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.place_name).text = dataSet[position].nome
        holder.itemView.findViewById<TextView>(R.id.place_code).text = dataSet[position].codice
        holder.itemView.setOnClickListener {
            onItemClick.invoke( dataSet[position] )
        }
    }

    override fun getItemCount() = dataSet.size
    fun setData(newDataSet: ArrayList<Place>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}