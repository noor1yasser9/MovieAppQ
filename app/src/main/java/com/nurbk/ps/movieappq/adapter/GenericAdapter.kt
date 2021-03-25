package com.nurbk.ps.movieappq.adapter

import android.annotation.SuppressLint
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.nurbk.ps.movieappq.model.detailsMovie.Genre
import com.nurbk.ps.movieappq.others.StringConstants
import javax.inject.Inject

class GenericAdapter<T>(
    @LayoutRes val layoutId: Int,
    var type: Int,
    val itemclick: OnListItemViewClickListener<T>
) :
    RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {
    // var inflater: LayoutInflater? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>( LayoutInflater.from(parent.context), layoutId, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = data[position]
        holder.bind(itemViewModel, type)
        holder.itemView.apply {
            setOnClickListener {
                itemclick.onClickItem(itemViewModel, 1)
            }
        }
    }


    class GenericViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewModel: T, F: Int) {
            binding.setVariable(F, itemViewModel)
        }

    }


    interface OnListItemViewClickListener<T> {
        fun onClickItem(itemViewModel: T, type: Int)
    }

     val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
     val differ = AsyncListDiffer(this, diffCallback)

    var data: List<T>
        get() = differ.currentList
        set(value) = differ.submitList(value)

}