package com.example.midtermapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.midtermapp.AppApplication
import com.example.midtermapp.R
import com.example.midtermapp.databinding.FragmentDetailBinding
import com.example.midtermapp.datanetwork.DataSource
import com.example.midtermapp.datanetwork.Model3D
import com.example.midtermapp.datanetwork.Shoes
import com.example.midtermapp.viewmodel.ShopShoesViewModel
import com.example.midtermapp.viewmodel.ShopShoesViewModelFactory
import com.example.midtermapp.viewmodel.UserShoesViewModel
import com.example.midtermapp.viewmodel.UserShoesViewModelFactory
import java.text.NumberFormat


class DetailFragment : Fragment() {
    private val listModel : List<Model3D> by lazy {
        DataSource.loadModel(requireContext())
    }
    private val viewModel : UserShoesViewModel by activityViewModels() {
        UserShoesViewModelFactory(
            (activity?.application as AppApplication).database.userShoesDao(),
            (activity?.application as AppApplication).firebase
        )
    }
    private val shopViewModel : ShopShoesViewModel by activityViewModels {
        ShopShoesViewModelFactory(
            (activity?.application as AppApplication).firebase
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
        //(requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val id = navigationArgs.id
        shopViewModel.retrieveShopShoes().observe(viewLifecycleOwner) { listShopShoes ->
            for (item in listShopShoes!!) {
                if (id == item.id) {
                    shoes = item
                    break
                }
            }

            binding(shoes)
            pickSize()
            binding.arBtn.setOnClickListener {
                var check = false
                listModel.forEach {
                    if(shoes.id == it.id) {
                        check = true
                        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                        val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                            .appendQueryParameter("file",
                                it.url)
                            .appendQueryParameter("mode", "3d_only")
                            .build()
                        sceneViewerIntent.data = intentUri
                        sceneViewerIntent.setPackage("com.google.ar.core")
                        startActivity(sceneViewerIntent)
                    }
                }
               if(!check) {
                   Toast.makeText(requireContext(),"Sorry we will update model for this shoes soon!", Toast.LENGTH_SHORT).show()
               }
            }
        }
    }

    private fun binding(shoes: Shoes) {
        binding.thumbnailImg.load(shoes.imgUrl.toUri().buildUpon().scheme("https").build()) {

        }
        binding.nameTv.text = shoes.name
        binding.priceTv.text = NumberFormat.getCurrencyInstance().format(shoes.price)
        binding.addBtn.setOnClickListener {
            if(shoes.quantity > 0) {
                viewModel.insert(shoes.id,shoes.name,shoes.price,size, shoes.imgUrl)
                Toast.makeText(this.context,"add to cart success", Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this.context, "this product is out of stock", Toast.LENGTH_SHORT).show()
            }

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