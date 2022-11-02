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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentCategoryBinding
import com.jorgesanaguaray.videogamesinformation.ui.detail.DetailActivity
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
        categoryAdapter = CategoryAdapter()

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
            categoryAdapter.setOnButtonClick(object : CategoryAdapter.OnButtonClick {
                override fun onClick(gameUrl: String) {
                    val uri = Uri.parse(gameUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            })
            categoryAdapter.setOnCardViewClick(object : CategoryAdapter.OnCardViewClick {
                override fun onClick(id: Int) {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra(KEY_GAME_ID, id)
                    startActivity(intent)
                }
            })

        }

        categoryViewModel.textInputLayoutVisibility.observe(viewLifecycleOwner) {
            binding.mTextInputLayout.visibility = if (it) View.VISIBLE else View.GONE
        }

        categoryViewModel.recyclerViewVisibility.observe(viewLifecycleOwner) {
            binding.mRecyclerView.visibility = if (it) View.VISIBLE else View.GONE
        }

        categoryViewModel.textViewNoInternetVisibility.observe(viewLifecycleOwner) {
            binding.mTextViewNoInternet.visibility = if (it) View.VISIBLE else View.GONE
        }

        categoryViewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.mSwipeRefreshLayout.setOnRefreshListener {
            categoryViewModel.getCategoriesGamesFromService(binding.mAutoComplete.text.toString())
            binding.mSwipeRefreshLayout.isRefreshing = false
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
                categoryViewModel.getCategoriesGamesFromService(binding.mAutoComplete.text.toString())
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}