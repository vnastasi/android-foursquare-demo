package nl.zoostation.fsd.screens.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.zoostation.fsd.BR
import nl.zoostation.fsd.R
import nl.zoostation.fsd.databinding.VenueListItemBinding
import nl.zoostation.fsd.persistence.model.Venue

class VenueListAdapter : ListAdapter<Venue, VenueListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = bindLayout(parent)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val venue = getItem(position)
        holder.bind(venue)
    }

    private fun bindLayout(parent: ViewGroup): VenueListItemBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataBindingUtil.inflate(layoutInflater, R.layout.venue_list_item, parent, false)
    }

    class ViewHolder(
        private val itemBinding: VenueListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(venue: Venue) {
            itemBinding.setVariable(BR.venue, venue)
            itemBinding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Venue>() {

        override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean =
            oldItem == newItem
    }
}