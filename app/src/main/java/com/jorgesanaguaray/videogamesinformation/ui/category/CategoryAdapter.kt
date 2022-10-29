package com.jorgesanaguaray.videogamesinformation.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.ItemCategoryBinding
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem

/**
 * Created by Jorge Sanaguaray
 */

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.MyCategoryViewHolder>() {

    private var games: List<GameItem> = ArrayList()
    private lateinit var onButtonClick: OnButtonClick
    private lateinit var onCardViewClick: OnCardViewClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
        return MyCategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {

        val game = games[position]

        holder.binding.apply {

            mTitle.text = game.title
            mImage.load(game.thumbnail) {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                crossfade(true)
                crossfade(400)
            }
            mShortDescription.text = game.short_description

        }

        holder.binding.mButtonGoToTheGamePage.setOnClickListener {
            onButtonClick.onClick(game.game_url)
        }

        holder.binding.mCardViewGame.setOnClickListener {
            onCardViewClick.onClick(game.id)
        }

    }

    override fun getItemCount(): Int {
        return games.size
    }

    class MyCategoryViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)

    fun setGames(games: List<GameItem>) {
        this.games = games
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