package kr.mediascope.smartsing.demo

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.mediascope.smartsing.demo.data.model.remote.MrItem

/**
 * 노래 목록 어댑터
 */
class MrListAdapter (
    private val onItemClick: (item: MrItem) -> Unit
) : ListAdapter<MrItem, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MrListViewHolder.getViewHolder(
            parent = parent,
            onItemClick = { position ->
                onItemClick(getItem(position))
            }
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MrListViewHolder -> {
                holder.bind(item)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MrItem>() {
            override fun areItemsTheSame(
                oldItem: MrItem,
                newItem: MrItem
            ): Boolean {
                return oldItem.mr_id == newItem.mr_id
            }

            override fun areContentsTheSame(
                oldItem: MrItem,
                newItem: MrItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}