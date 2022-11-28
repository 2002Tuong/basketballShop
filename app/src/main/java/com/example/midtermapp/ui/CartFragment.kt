package com.example.midtermapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midtermapp.AppApplication
import com.example.midtermapp.R
import com.example.midtermapp.adapter.CartItemAdapter
import com.example.midtermapp.database.UserShoes
import com.example.midtermapp.databinding.FragmentCartBinding
import com.example.midtermapp.viewmodel.UserShoesViewModel
import com.example.midtermapp.viewmodel.UserShoesViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: UserShoesViewModel by activityViewModels {
        UserShoesViewModelFactory(
            (activity?.application as AppApplication).database.userShoesDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCartBinding.inflate(
            inflater,container,false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CartItemAdapter {
            showConfirmationDialogToDelete(it)

        }
        binding.cartRecycleView.adapter = adapter
        binding.cartRecycleView.layoutManager = LinearLayoutManager(this.context)
        viewModel.getAll().observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.buyBtn.setOnClickListener {
            viewModel.buyAll()
            Toast.makeText(this.context,"thank you for buy our product", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showConfirmationDialogToDelete(shoes: UserShoes) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteItem(shoes)
            }
            .show()
    }


}