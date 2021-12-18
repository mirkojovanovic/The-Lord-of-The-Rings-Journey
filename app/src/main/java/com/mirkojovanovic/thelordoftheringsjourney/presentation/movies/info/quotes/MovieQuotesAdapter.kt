package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mirkojovanovic.thelordoftheringsjourney.databinding.QuoteItemBinding
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.character.Character
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie.MovieDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc

class MovieQuotesAdapter : RecyclerView.Adapter<MovieQuotesAdapter.ViewHolder>(), Filterable {

    var quotes = mutableListOf<QuoteDoc>()
    var filteredQuotes = mutableListOf<QuoteDoc>()
    var characters = mutableListOf<Character>()
    var movie: MovieDoc? = null

    private val filterObject = object : Filter() {

        val filterResults = FilterResults()
        val filteredList = mutableListOf<QuoteDoc>()

        override fun performFiltering(query: CharSequence?): FilterResults {
            when {
                query.isNullOrBlank() -> {
                    filteredList.addAll(quotes)
                }
                else -> {
                    val targetQuotes = quotes.filter { quote ->
                        quote.character in characters.filter { it.name.startsWith(query) }.map { it._id }
                    }
                    filteredList.addAll(targetQuotes)
                }
            }
            filterResults.count = filteredList.size
            filterResults.values = filteredList
            return filterResults
        }

        @SuppressLint("NotifyDataSetChanged")
        @SuppressWarnings("unchecked")
        override fun publishResults(query: CharSequence?, filterResults: FilterResults?) {
            filteredQuotes = (filterResults!!.values as? MutableList<QuoteDoc>)!!
            notifyDataSetChanged()
        }
    }

    class ViewHolder(private val binding: QuoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quoteDoc: QuoteDoc, characters: List<Character>, movie: MovieDoc?) {
            binding.character.text = characters.find { it._id == quoteDoc.character }?.name
            binding.movie.text = movie?.name
            binding.quote.text = quoteDoc.dialog
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            QuoteItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(filteredQuotes[position], characters, movie)
    }

    override fun getItemCount() = filteredQuotes.size

    override fun getFilter(): Filter {
        return filterObject
    }

}
