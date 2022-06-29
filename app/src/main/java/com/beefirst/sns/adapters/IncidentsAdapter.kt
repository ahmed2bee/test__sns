package com.beefirst.sns.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beefirst.sns.R
import com.beefirst.sns.enums.StatusTypes
import com.beefirst.sns.model.Incident
import com.beefirst.sns.utils.DatesUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_incident_layout.view.*
import kotlin.collections.ArrayList

class IncidentAdapter(
    private val context: Context,
    private val list: ArrayList<Incident>,
    private val listener: IncidentClicked,
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        holder: MyHolder,
        position: Int
    ) {
        if (list[position].medias.isNotEmpty()) {
            if (list[position].medias[0].mimeType.contains("jpg") ||
                list[position].medias[0].mimeType.contains("png")
            )
                Picasso.get().load(list[position].medias[0].url)
                    .into(holder.incidentImage)
        }

        holder.id.text = list[position].id
        holder.description.text = list[position].description
        holder.date.text = DatesUtils.convertDateTime(list[position].createdAt)

        holder.status.text = StatusTypes.statusTypes(list[position].status)

        holder.changeStatus.setOnClickListener { listener.onIncidentClicked(position) }
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var incidentImage = itemView.incident_img!!
        var id = itemView.tvID!!
        var description = itemView.tvDesc!!
        var date = itemView.tvDate!!
        var status = itemView.tvStatus!!
        var changeStatus = itemView.tvChangeStatus!!

    }

    interface IncidentClicked {
        fun onIncidentClicked(
            position: Int
        )
    }
}