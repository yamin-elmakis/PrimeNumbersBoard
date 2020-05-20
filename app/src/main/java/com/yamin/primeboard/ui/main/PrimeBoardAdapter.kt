package com.yamin.primeboard.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yamin.primeboard.R
import com.yamin.primeboard.model.NumberItem
import kotlinx.android.synthetic.main.item_number.view.*


private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NumberItem>() {
    override fun areItemsTheSame(oldItem: NumberItem, newItem: NumberItem): Boolean {
        return oldItem.value == newItem.value
    }

    override fun areContentsTheSame(oldItem: NumberItem, newItem: NumberItem): Boolean {
        return oldItem == newItem
    }
}

class PrimeBoardAdapter(context: Context) :
    PagedListAdapter<NumberItem, PrimeBoardAdapter.ViewHolder.NumberViewHolder>(DIFF_CALLBACK) {

    // because we have a lot of items in the screen at once lets keep the LayoutInflater
    // so we won't create a new one each time onCreateViewHolder is called
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    companion object ViewHolder {
        class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: NumberItem?) {
                item?.let {
                    itemView.itemNumber.text = it.value.toString()
                    itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            it.type.getBackgroundColor()
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        return NumberViewHolder(inflater.inflate(R.layout.item_number, parent, false))
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}