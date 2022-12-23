package com.jorgesanaguaray.videogamesinformation.ui.category

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentCategoryBinding
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.util.Constants.Companion.KEY_GAME_ID
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jorge Sanaguaray
 */

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryViewModel = ViewModelProvider(this).get()
        categoryAdapter = CategoryAdapter(
            categoryViewModel = categoryViewModel,
            itemPosition = { categoryAdapter.notifyItemChanged(it) },
            onFavoriteClick = { insertOrDeleteFavorite(it) },
            onButtonUrlClick = { goToTheGamePage(it) },
            onCardGameClick = { goToTheGameDetails(it) }
        )

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel.games.observe(viewLifecycleOwner) {
            categoryAdapter.setGames(it)
            binding.mRecyclerView.adapter = categoryAdapter
        }

        categoryViewModel.error.observe(viewLifecycleOwner) {
            binding.mTextError.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.error) + "</b>" + " " + it + "." + "<br><br>" + "<b>" + resources.getString(R.string.possible_solution) + "</b>" + " " + resources.getString(R.string.check_your_internet_connection), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        categoryViewModel.textInputLayoutVisibility.observe(viewLifecycleOwner) {
            binding.mTextInputLayout.visibility = if (it) View.VISIBLE else View.GONE
        }

        categoryViewModel.recyclerViewVisibility.observe(viewLifecycleOwner) {
            binding.mRecyclerView.visibility = if (it) View.VISIBLE else View.GONE
        }

        categoryViewModel.cardErrorVisibility.observe(viewLifecycleOwner) {
            binding.mCardError.visibility = if (it) View.VISIBLE else View.GONE
        }

        categoryViewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.mSwipeRefresh.setOnRefreshListener {
            categoryViewModel.getGamesByCategory(binding.mAutoComplete.text.toString())
            binding.mSwipeRefresh.isRefreshing = false
        }

    }

    override fun onResume() {
        super.onResume()

        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.text_view, categories)
        binding.mAutoComplete.setAdapter(arrayAdapter)

        binding.mAutoComplete.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                categoryViewModel.getGamesByCategory(binding.mAutoComplete.text.toString())
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertOrDeleteFavorite(gameItem: GameItem) {

        if (categoryViewModel.isFavorite(gameItem.id)) {
            categoryViewModel.deleteFavoriteById(gameItem.id)
            Snackbar.make(requireView(), R.string.game_removed_from_favorites, Snackbar.LENGTH_SHORT).show()
        } else {
            categoryViewModel.insertFavorite(gameItem)
            Snackbar.make(requireView(), R.string.game_saved_in_favorites, Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun goToTheGamePage(gameUrl: String) {
        val uri = Uri.parse(gameUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun goToTheGameDetails(id: Int) {
        val bundle = bundleOf(KEY_GAME_ID to id)
        findNavController().navigate(R.id.action_nav_category_to_nav_detail, bundle)
    }

}