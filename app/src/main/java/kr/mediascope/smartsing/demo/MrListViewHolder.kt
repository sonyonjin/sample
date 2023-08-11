package kr.mediascope.smartsing.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.mediascope.smartsing.demo.data.model.remote.MrItem
import kr.mediascope.smartsing.demo.databinding.ItemMrListBinding

/**
 * 노래방 목록 아이템 view holder
 */
class MrListViewHolder(
    private val binding: ItemMrListBinding
) : RecyclerView.ViewHolder(binding.root){

    companion object {
        fun getViewHolder(
            parent: ViewGroup,
            onItemClick: (position: Int) -> Unit
        ): RecyclerView.ViewHolder {
            val binding = ItemMrListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            val viewHolder = MrListViewHolder(binding = binding)
            binding.tvSingASong.setOnClickListener { onItemClick(viewHolder.adapterPosition) }
            return viewHolder
        }
    }

    fun bind(mrItem: MrItem) {
        Glide.with(itemView.context)
            .load(mrItem.album.album_cover_path600)
            .placeholder(R.drawable.ic_bemily_song_cover)
            .error(R.drawable.ic_bemily_song_cover)
            .into(binding.ivCoverImage)

        binding.tvSongTitle.text = mrItem.title_ko
        binding.tvArtist.text = mrItem.artist_ko
        binding.tvSingASong.text = "부르기"
    }
}