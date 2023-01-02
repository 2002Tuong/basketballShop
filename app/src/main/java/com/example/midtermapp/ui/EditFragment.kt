package com.example.midtermapp.ui


import android.net.Uri
import android.os.Bundle

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.midtermapp.AppApplication
import com.example.midtermapp.R
import com.example.midtermapp.databinding.FragmentEditBinding

import com.example.midtermapp.datanetwork.Shoes

import com.example.midtermapp.viewmodel.ShopShoesViewModel
import com.example.midtermapp.viewmodel.ShopShoesViewModelFactory

import com.google.firebase.storage.FirebaseStorage

import kotlinx.coroutines.*

import java.text.NumberFormat
import java.util.UUID

const val PICK_IMAGE_REQUEST = 1000
class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val shopViewModel: ShopShoesViewModel by activityViewModels {
        ShopShoesViewModelFactory(
            (activity?.application as AppApplication).firebase
        )
    }
    private val  navigationArgs: EditFragmentArgs by navArgs()
    private lateinit var item: Shoes
    //new thumbnail for shoes
    //this uri get from local device
    private var imgUri: Uri? = null
    //this url get from firebase storage
    private var imgUrl: String = ""
    //this id the id of thumbnail on firebase storage
    private lateinit var imgFireStoreId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEditBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        Log.d("EditFragment", "EditFragment id of shoes is $id")
        if(id >= 0) {
           shopViewModel.retrieveSpecifyShoesById(id).observe(viewLifecycleOwner) {
               item = it
               Log.d("EditFragment", "value of item is ${item}")
               binding(item)
           }

        }else {

        }
    }

    private fun binding(shoes: Shoes) {
        val price = NumberFormat.getCurrencyInstance().format(shoes.price)
        binding.apply {
            nameInput.setText(shoes.name, TextView.BufferType.SPANNABLE)
            priceInput.setText(price, TextView.BufferType.SPANNABLE)
            quantityInput.setText(shoes.quantity.toString(), TextView.BufferType.SPANNABLE)
            thumbnailImg.load(shoes.imgUrl.toUri().buildUpon().scheme("https").build()) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.broken_image_24)
            }
            thumbnailImg.setOnClickListener {
                pickImage()
            }
            applyBtn.setOnClickListener {

                val newData = getInputFromUser().toMutableMap()
                uploadImage(imgUri)
                CoroutineScope(Dispatchers.IO).launch {
                    delay(5000L)
                    if(imgUrl.isNotEmpty()) {
                        newData["imgUrl"] = imgUrl
                    }
                    shopViewModel.update(item.id, newData)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EditFragment.context, "Update success", Toast.LENGTH_SHORT).show()
                        val action = EditFragmentDirections.actionEditFragmentToShopFragment()
                        findNavController().navigate(action)
                    }
                }

            }
            cancelBtn.setOnClickListener {
                val action = EditFragmentDirections.actionEditFragmentToShopFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun getNewUpdate(name: String, price: String, quantity: String, imgUrl: String = "") : Map<String,Any> {
        val map = mutableMapOf<String, Any>()
        if(name.isNotEmpty()) {
            map["name"] = name
        }
        if(price.isNotEmpty()) {
            val formatPrice = price.replace("₫", "").trim()
            map["price"] = formatPrice
        }
        if(quantity.isNotEmpty()) {
            map["quantity"] = quantity.toInt()
        }
        if(imgUrl.isNotEmpty()) {
            map["imgUrl"] = imgUrl
        }
        return map
    }

    private  fun getInputFromUser() : Map<String, Any> {
        val name = binding.nameInput.text.toString()
        val price = binding.priceInput.text.toString()
        val quantity = binding.quantityInput.text.toString()
        return getNewUpdate(name,price,quantity,imgUrl)
    }
    //to modify the thumbnail of shoes
    private fun pickImage() {
        getContent.launch("image/*")
    }
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imgUri = uri
        if(uri != null) {
            binding.thumbnailImg.load(
                uri.buildUpon().build()){
                    error(R.drawable.broken_image_24)
                }
        }
    }
    //upload image to fire storage and get download url
    private fun uploadImage(uri: Uri?) {
        val storage = FirebaseStorage.getInstance()
        imgFireStoreId = UUID.randomUUID().toString()
        val storageRef = storage.reference.child(imgFireStoreId)
        if(uri != null) {
            storageRef.putFile(uri).addOnSuccessListener {
                if(it.metadata != null) {
                    if(it.metadata!!.reference != null) {
                        val result = it.storage.downloadUrl
                        result.addOnSuccessListener { uri ->
                            imgUrl = uri.toString()
                            Log.d("EditFragment", "this is url of thumbnail $imgUrl")
                        }
                    }
                }
            }
        }
    }
}