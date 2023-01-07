package com.example.midtermapp.ui

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midtermapp.AppApplication
import com.example.midtermapp.adapter.ListItemAdapter

import com.example.midtermapp.databinding.FragmentListItemBinding
import com.example.midtermapp.viewmodel.ShopShoesViewModel
import com.example.midtermapp.viewmodel.ShopShoesViewModelFactory


class ListItemFragment : Fragment() {
    private lateinit var binding: FragmentListItemBinding
    private val shopViewModel: ShopShoesViewModel by activityViewModels {
        ShopShoesViewModelFactory(
            (activity?.application as AppApplication).firebase
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       shopViewModel.retrieveShopShoes().observe(this.viewLifecycleOwner) { data ->
            val adapter = ListItemAdapter(requireContext(),data) {
                val action = ListItemFragmentDirections.actionListItemFragmentToDetailFragment(id = it.id)
                findNavController().navigate(action)
            }
            binding.listItemRecyclerview.adapter = adapter
            binding.listItemRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.swipeRefresh.setOnRefreshListener {
            shopViewModel.retrieveShopShoes()
                .observe(this@ListItemFragment.viewLifecycleOwner) { data ->
                    val adapter = ListItemAdapter(requireContext(), data) {
                        val action =
                            ListItemFragmentDirections.actionListItemFragmentToDetailFragment(id = it.id)
                        findNavController().navigate(action)
                    }
                    binding.listItemRecyclerview.adapter = adapter
                    binding.listItemRecyclerview.layoutManager = LinearLayoutManager(requireContext())
                    binding.swipeRefresh.isRefreshing = false
                }
        }
    }

}