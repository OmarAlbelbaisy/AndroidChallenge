package com.oza.challenge.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oza.challenge.databinding.FragmentImageDetailsBinding
import com.oza.challenge.model.ImageData

class ImageDetailFragment : Fragment() {

    private var _binding: FragmentImageDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        if (arguments != null) {
            binding.image = requireArguments().getSerializable("item") as ImageData
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
