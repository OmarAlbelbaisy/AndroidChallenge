package com.oza.challenge.ui.home

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.oza.challenge.R
import com.oza.challenge.databinding.FragmentHomeBinding
import com.oza.challenge.model.ImageData
import dagger.hilt.android.AndroidEntryPoint
import java.util.Objects


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var imagesAdapter: ImageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        imagesAdapter = ImageListAdapter(object : ItemClickListener {
            override fun onItemClick(item: ImageData) {
                val action = HomeFragmentDirections.actionHomeFragmentToImageDetailsFragment(item)
                findNavController().navigate(action)
            }
        })

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(
            Objects.requireNonNull<Drawable?>(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.divider
                )
            )
        )

        binding.recyclerViewImages.apply {
            addItemDecoration(dividerItemDecoration)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = imagesAdapter
        }

        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = false
            viewModel.search()
        }

        viewModel.pixabayResponseLiveData.observe(viewLifecycleOwner) {
            if (it != null && it.hits.size > 0) {
                viewModel.isEmpty.value = false
                imagesAdapter.submitList(it.hits)
            } else {
                viewModel.isEmpty.value = true
                imagesAdapter.submitList(mutableListOf())
            }
        }

        viewModel.pixabayResponseErrorLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(binding.swipeToRefresh, it, Snackbar.LENGTH_INDEFINITE)
                .setTextColor(Color.RED)
                .setAction(getString(R.string.retry)) { viewModel.search() }
                .show()
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchQuery.value = binding.searchEditText.text.toString()
                true // Return true to indicate that the action has been handled
            } else {
                false // Return false to let the system handle the action
            }
        }

        viewModel.searchQuery.observe(viewLifecycleOwner) {
            viewModel.search()
        }

        viewModel.search()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
