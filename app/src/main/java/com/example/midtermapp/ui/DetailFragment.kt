package com.example.midtermapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.midtermapp.AppApplication
import com.example.midtermapp.R

import com.example.midtermapp.databinding.FragmentDetailBinding
import com.example.midtermapp.datanetwork.DataSource
import com.example.midtermapp.datanetwork.Shoes
import com.example.midtermapp.viewmodel.UserShoesViewModel
import com.example.midtermapp.viewmodel.UserShoesViewModelFactory
import java.text.NumberFormat


class DetailFragment : Fragment() {
    private val viewModel : UserShoesViewModel by activityViewModels() {
        UserShoesViewModelFactory(
            (activity?.application as AppApplication).database.userShoesDao()
        )
    }
    private lateinit var binding : FragmentDetailBinding
    private val navigationArgs : DetailFragmentArgs by navArgs()
    private lateinit var shoes: Shoes
    private var size: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val id = navigationArgs.id
        for(item in DataSource.loadShoes(requireContext())) {
            if(id == item.id) {
                shoes = item
                break
            }
        }
        binding(shoes)
        pickSize()

    }

    private fun binding(shoes: Shoes) {
        binding.thumbnailImg.load(shoes.imgUrl.toUri().buildUpon().scheme("https").build()) {

        }
        binding.nameTv.text = shoes.name
        binding.priceTv.text = NumberFormat.getCurrencyInstance().format(shoes.price)
        binding.addBtn.setOnClickListener {
            viewModel.insert(shoes.name,shoes.price,size, shoes.imgUrl)
            Toast.makeText(this.context,"add to cart success", Toast.LENGTH_LONG).show()
        }
        binding.sizeSelectionRadioGroup.setOnCheckedChangeListener { _, _ ->
            pickSize()
        }

    }
    private fun pickSize() {
        size = when(binding.sizeSelectionRadioGroup.checkedRadioButtonId) {
            R.id.size_42 -> 42
            R.id.size_43 -> 43
            R.id.size_44 -> 44
            R.id.size_45 -> 45
            else -> 46
        }
    }
}