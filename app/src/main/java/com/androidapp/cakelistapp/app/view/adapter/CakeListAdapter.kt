package com.androidapp.cakelistapp.app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.cakelistapp.R
import com.androidapp.cakelistapp.data.model.CakeModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_row_item.view.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Nur Uysal on 06/12/2021.
 */

@Singleton
class CakeListAdapters @Inject constructor() :
    RecyclerView.Adapter<CakeListAdapters.CakeViewHolder>() {

    inner class CakeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<CakeModel>() {
        override fun areItemsTheSame(oldItem: CakeModel, newItem: CakeModel): Boolean {
            return (oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.imageUrl == newItem.imageUrl)
        }

        override fun areContentsTheSame(oldItem: CakeModel, newItem: CakeModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: MutableList<CakeModel>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        return CakeViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.view_row_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {

        val item = differ.currentList[position]
        holder.itemView.apply {
            tv_row_item_title.text = "${item.title}"
            Glide.with(this).load(item.imageUrl).error(R.drawable.no_image).into(iv_row_item_image)
            this.setOnClickListener {
                //TODO make more user friendly alert dialog or etc. it cause problem for continous clicks
                Toast.makeText(context, item.description, Toast.LENGTH_SHORT).show()
            }
        }

    }
}

