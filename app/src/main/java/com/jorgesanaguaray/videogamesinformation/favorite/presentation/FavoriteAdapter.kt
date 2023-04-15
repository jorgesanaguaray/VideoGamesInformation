package com.jorgesanaguaray.videogamesinformation.favorite.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game
import com.jorgesanaguaray.videogamesinformation.databinding.ItemFavoriteBinding

/**
 * Created by Jorge Sanaguaray
 */

class FavoriteAdapter(

    private val favoriteViewModel: FavoriteViewModel,
    private val itemPosition: (Int) -> Unit,
    private val onFavoriteClick: (Game) -> Unit,
    private val onButtonUrlClick: (String) -> Unit,
    private val onCardGameShortClick: (Int) -> Unit,
    private val onCardGameLongClick: (Game) -> Unit

) : RecyclerView.Adapter<FavoriteAdapter.MyFavoriteViewHolder>() {

    private var games: List<Game> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFavoriteViewHolder {
        return MyFavoriteViewHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyFavoriteViewHolder, position: Int) {

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
            mCardGame.setOnClickListener { onCardGameShortClick(game.id) }
            mCardGame.setOnLongClickListener {
                onCardGameLongClick(game)
                true
            }
        }

        setStateOfGame(game.id, holder.binding)

    }

    override fun getItemCount(): Int {
        return games.size
    }

    class MyFavoriteViewHolder(val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root)

    fun setGames(games: List<Game>) {
        this.games = games
    }

    private fun setStateOfGame(id: Int, binding: ItemFavoriteBinding) {

        if (favoriteViewModel.isFavorite(id)) {
            binding.mFavorite.setImageResource(R.drawable.ic_favored)
        } else {
            binding.mFavorite.setImageResource(R.drawable.ic_favorite)
        }

    }

}