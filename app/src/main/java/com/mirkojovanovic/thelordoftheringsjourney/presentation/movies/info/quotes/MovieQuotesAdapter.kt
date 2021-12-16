package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mirkojovanovic.thelordoftheringsjourney.databinding.QuoteItemBinding
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc

class MovieQuotesAdapter :
    PagingDataAdapter<QuoteDoc, MovieQuotesAdapter.ViewHolder>(SERVICE_COMPARATOR) {

    class ViewHolder(private val binding: QuoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quoteDoc: QuoteDoc) {
            binding.quote.text = quoteDoc.dialog
            binding.character.text = quoteDoc.character
            binding.movie.text = quoteDoc.movie
        }
    }

    companion object {
        private val SERVICE_COMPARATOR = object : DiffUtil.ItemCallback<QuoteDoc>() {
            override fun areItemsTheSame(oldItem: QuoteDoc, newItem: QuoteDoc) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: QuoteDoc, newItem: QuoteDoc) =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QuoteItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}
