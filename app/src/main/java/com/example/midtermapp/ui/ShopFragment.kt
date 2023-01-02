package com.example.midtermapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midtermapp.AppApplication

import com.example.midtermapp.adapter.ShopListItemAdapter
import com.example.midtermapp.databinding.FragmentShopBinding

import com.example.midtermapp.viewmodel.ShopShoesViewModel
import com.example.midtermapp.viewmodel.ShopShoesViewModelFactory

/*
this fragment list all of shop product
 */
class ShopFragment : Fragment() {
    private lateinit var binding: FragmentShopBinding
    private val shopViewModel : ShopShoesViewModel by activityViewModels {
        ShopShoesViewModelFactory(
            (activity?.application as AppApplication).firebase
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentShopBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ShopListItemAdapter {
            val action = ShopFragmentDirections.actionShopFragmentToEditFragment(id = it.id)
            findNavController().navigate(action)
        }
        shopViewModel.retrieveShopShoes().observe(viewLifecycleOwner) {
            Log.d("ShopFragment",it.toString())
            adapter.submitList(it)
        }

        binding.listItemRecyclerview.adapter = adapter
        binding.listItemRecyclerview.layoutManager = LinearLayoutManager(this.context)
    }

}