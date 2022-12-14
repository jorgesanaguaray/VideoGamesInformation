package com.jorgesanaguaray.videogamesinformation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.data.remote.models.Screenshot
import com.jorgesanaguaray.videogamesinformation.databinding.ItemDetailBinding

/**
 * Created by Jorge Sanaguaray
 */

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.MyScreenshotViewHolder>() {

    private var screenshots: List<Screenshot> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyScreenshotViewHolder {
        return MyScreenshotViewHolder(ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyScreenshotViewHolder, position: Int) {

        val screenshot = screenshots[position]

        holder.binding.apply {

            mImage.load(screenshot.image) {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                crossfade(true)
                crossfade(400)
            }

        }

    }

    override fun getItemCount(): Int {
        return screenshots.size
    }

    class MyScreenshotViewHolder(val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root)

    fun setScreenshots(screenshots: List<Screenshot>) {
        this.screenshots = screenshots
    }

}