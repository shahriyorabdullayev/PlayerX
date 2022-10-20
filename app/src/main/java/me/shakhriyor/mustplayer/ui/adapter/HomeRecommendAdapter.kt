package me.shakhriyor.mustplayer.ui.adapter

import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.shakhriyor.mustplayer.R
import me.shakhriyor.mustplayer.common.util.Constants.MP3
import me.shakhriyor.mustplayer.common.util.RandomColor
import me.shakhriyor.mustplayer.data.model.MusicModel
import me.shakhriyor.mustplayer.databinding.ItemMusicHomeBinding
import me.shakhriyor.mustplayer.ui.adapter.HomeRecommendAdapter.HomeRecommendViewHolder

class HomeRecommendAdapter :
    ListAdapter<MusicModel, HomeRecommendViewHolder>(HomeMusicItemDiffCallBack()) {

    var itemClickListener: ((MusicModel) -> Unit)? = null
    var count = 0

    inner class HomeRecommendViewHolder(private val binding: ItemMusicHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendMusic: MusicModel) {
            binding.apply {

                val uri = Uri.parse(recommendMusic.path)
                val retriever = MediaMetadataRetriever()
                val resultUri =
                    uri.toString().substring(uri.toString().length - 3, uri.toString().length)

                if (resultUri == MP3) {
                    retriever.setDataSource(uri.toString())

                    if (retriever.embeddedPicture != null) {
                        Glide.with(ivMusic).asBitmap().load(retriever.embeddedPicture)
                            .placeholder(RandomColor.randomColor()).into(ivMusic)
                    } else {
                        ivMusic.setImageResource(R.drawable.img_music)
                    }

                    tvMusicName.text = recommendMusic.title
                    tvArtistName.text = recommendMusic.artistName
                }

                root.setOnClickListener {
                    itemClickListener?.invoke(recommendMusic)
                }
            }
        }


    }


    private class HomeMusicItemDiffCallBack : DiffUtil.ItemCallback<MusicModel>() {
        override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecommendViewHolder {
        val view = ItemMusicHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeRecommendViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeRecommendViewHolder, position: Int) {
        val recommendMusic = getItem(position)
        holder.bind(recommendMusic)
    }


}