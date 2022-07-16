package com.steve.msms.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.steve.msms.R
import com.steve.msms.databinding.SearchMessageItemBinding
import com.steve.msms.domain.model.Message


class SearchMessageAdapter(

) : RecyclerView.Adapter<SearchMessageAdapter.MyViewHolder>() {

    private var list: ArrayList<Message> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val activityAdapterBinding = DataBindingUtil.inflate<SearchMessageItemBinding>(
            LayoutInflater
                .from(parent.context),
            R.layout.search_message_item, parent, false
        )
        return MyViewHolder(activityAdapterBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(newList: List<Message>) {
        list.clear()
        list.addAll(newList)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val message = list[position]
        holder.binding.body.text = message.body
        holder.binding.name.text = message.number

    }

    inner class MyViewHolder(var binding: SearchMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

}