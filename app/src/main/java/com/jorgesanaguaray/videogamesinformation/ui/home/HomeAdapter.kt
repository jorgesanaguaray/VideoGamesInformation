package com.jorgesanaguaray.videogamesinformation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.ItemHomeBinding
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem

/**
 * Created by Jorge Sanaguaray
 */

class HomeAdapter(

    private val homeViewModel: HomeViewModel,
    private val itemPosition: (Int) -> Unit,
    private val onFavoriteClick: (GameItem) -> Unit,
    private val onButtonUrlClick: (String) -> Unit,
    private val onCardGameClick: (Int) -> Unit

) : RecyclerView.Adapter<HomeAdapter.MyHomeViewHolder>() {

    private var games: List<GameItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHomeViewHolder {
        return MyHomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHomeViewHolder, position: Int) {

        val game = games[position]

        holder.binding.apply {
            mTitle.text = game.title
            mDescription.text = game.short_description
            mImage.load(game.thumbnail) {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                crossfade(true)
                crossfade(400)
            }
            mFavorite.setOnClickListener { itemPosition(position) ; onFavoriteClick(game) }
            mButtonUrl.setOnClickListener { onButtonUrlClick(game.game_url) }
            mCardGame.setOnClickListener { onCardGameClick(game.id) }
        }

        setStateOfGame(game.id, holder.binding)

    }

    override fun getItemCount(): Int {
        return games.size
    }

    class MyHomeViewHolder(val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root)

    fun setGames(games: List<GameItem>) {
        this.games = games
    }

    private fun setStateOfGame(id: Int, binding: ItemHomeBinding) {

        if (homeViewModel.isFavorite(id)) {
            binding.mFavorite.setImageResource(R.drawable.ic_favored)
        } else {
            binding.mFavorite.setImageResource(R.drawable.ic_favorite)
        }

    }

}