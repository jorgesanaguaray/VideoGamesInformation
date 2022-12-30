package com.jorgesanaguaray.videogamesinformation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.ItemSearchBinding
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem

/**
 * Created by Jorge Sanaguaray
 */

class SearchAdapter(

    private val searchViewModel: SearchViewModel,
    private val itemPosition: (Int) -> Unit,
    private val onFavoriteClick: (GameItem) -> Unit,
    private val onButtonUrlClick: (String) -> Unit,
    private val onCardGameShortClick: (Int) -> Unit,
    private val onCardGameLongClick: (GameItem) -> Unit

) : RecyclerView.Adapter<SearchAdapter.MySearchViewHolder>() {

    private var games: List<GameItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySearchViewHolder {
        return MySearchViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MySearchViewHolder, position: Int) {

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

    class MySearchViewHolder(val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root)

    fun setGames(games: List<GameItem>) {
        this.games = games
    }

    private fun setStateOfGame(id: Int, binding: ItemSearchBinding) {

        if (searchViewModel.isFavorite(id)) {
            binding.mFavorite.setImageResource(R.drawable.ic_favored)
        } else {
            binding.mFavorite.setImageResource(R.drawable.ic_favorite)
        }

    }

}