package com.beefirst.sns.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beefirst.sns.R
import com.beefirst.sns.model.IncidentModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_incident_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class IncidentAdapter(
    private val context: Context,
    private val list: IncidentModel,
) : RecyclerView.Adapter<IncidentAdapter.MyHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        return MyHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.single_incident_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = list.incidents.size

    override fun onBindViewHolder(
        holder: MyHolder,
        position: Int
    ) {
        if (list.incidents[position].medias[0].mimeType.contains("jpg") ||
            list.incidents[position].medias[0].mimeType.contains("png"))
                Picasso.get().load(list.incidents[position].medias[0].url).into(holder.incidentImage)

        holder.id.text = list.incidents[position].id
        holder.description.text = list.incidents[position].description
        holder.date.text = convertDate(list.incidents[position].createdAt)
        holder.status.text = statusTypes(list.incidents[position].status)
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var incidentImage = itemView.incident_img!!
        var id = itemView.tvID!!
        var description = itemView.tvDesc!!
        var date = itemView.tvDate!!
        var status = itemView.tvStatus!!

    }

    private fun convertDate(date: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date: Date = format.parse(date)
        return date.toString()
    }

    private fun statusTypes(type: Int): String {
        when (type) {
            0 -> return "Submitted"
            1 -> return "InProgress"
            2 -> return "Completed"
            3 -> return "Rejected"
        }
        return ""
    }
}