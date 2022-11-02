package com.jorgesanaguaray.videogamesinformation.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.ItemFavoriteBinding
import com.jorgesanaguaray.videogamesinformation.domain.item.FavoritesItem

/**
 * Created by Jorge Sanaguaray
 */

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MyFavoriteViewHolder>() {

    private var favorites: List<FavoritesItem> = ArrayList()
    private lateinit var onButtonClick: OnButtonClick
    private lateinit var onCardViewClick: OnCardViewClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFavoriteViewHolder {
        return MyFavoriteViewHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyFavoriteViewHolder, position: Int) {

        val favorite = favorites[position]

        holder.binding.apply {

            mTitle.text = favorite.title
            mImage.load(favorite.thumbnail) {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                crossfade(true)
                crossfade(400)
            }
            mShortDescription.text = favorite.short_description
            mButtonGoToTheGamePage.setOnClickListener {
                onButtonClick.onClick(favorite.game_url)
            }
            mCardViewGame.setOnClickListener {
                onCardViewClick.onClick(favorite.id)
            }

        }

    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    class MyFavoriteViewHolder(val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root)

    fun setGames(favorites: List<FavoritesItem>) {
        this.favorites = favorites
    }


    interface OnButtonClick {
        fun onClick(gameUrl: String)
    }

    fun setOnButtonClick(onButtonClick: OnButtonClick) {
        this.onButtonClick = onButtonClick
    }


    interface OnCardViewClick {
        fun onClick(id: Int)
    }

    fun setOnCardViewClick(onCardViewClick: OnCardViewClick) {
        this.onCardViewClick = onCardViewClick
    }


}