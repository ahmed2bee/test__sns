package com.beefirst.zatsh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beefirst.zatsh.R

class DeviceIssueAdapter(){}
//    private val context: Context,
//    private val list: DeviceIssueResponse,
//) : RecyclerView.Adapter<DeviceIssueAdapter.MyHolder>() {
//
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): MyHolder {
//        return MyHolder(
//            LayoutInflater.from(context)
//                .inflate(R.layout.single_device_issue_layout, parent, false)
//        )
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
//
//    override fun getItemCount(): Int = list.data.size
//
//    override fun onBindViewHolder(
//        holder: MyHolder,
//        position: Int
//    ) {
//        holder.deviceIssue.text = list.data[position].issue_name
//    }
//
//    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        var deviceIssue = itemView.deviceIssueTv!!
//    }
//
//}