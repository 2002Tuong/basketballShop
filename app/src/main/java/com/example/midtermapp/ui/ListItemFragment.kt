package com.example.midtermapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midtermapp.adapter.ListItemAdapter
import com.example.midtermapp.datanetwork.DataSource
import com.example.midtermapp.databinding.FragmentListItemBinding


class ListItemFragment : Fragment() {
    private lateinit var binding: FragmentListItemBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(requireActivity() as AppCompatActivity).supportActionBar?.show()
        val adapter = ListItemAdapter(requireContext(),DataSource.loadShoes(requireContext())) {
            val action = ListItemFragmentDirections.actionListItemFragmentToDetailFragment(id = it.id)
            findNavController().navigate(action)
        }
        binding.listItemRecyclerview.adapter = adapter
        binding.listItemRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.mapBtn.setOnClickListener{
            val action = ListItemFragmentDirections.actionListItemFragmentToMapsFragment()
            findNavController().navigate(action)
        }
    }

}