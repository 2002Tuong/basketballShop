package com.example.midtermapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.midtermapp.R

import com.example.midtermapp.databinding.FragmentLoginShopModeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginShopModeFragment : Fragment() {

    private lateinit var listCode: MutableList<String>
    private lateinit var binding: FragmentLoginShopModeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore
        val collection = db.collection("shop_code")
        CoroutineScope(Dispatchers.IO).launch {
             listCode = try {
                 val list = mutableListOf<String>()
                 val snapshot = collection.get().await()
                 for (doc in snapshot.documents) {
                    list.add(doc["code"].toString())
                 }
                 list
            }catch (e:Exception) {
                Log.d("LoginShopModeFragment", e.message ?: "cannot load list code")
                 mutableListOf<String>("123456")
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginShopModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkBtn.setOnClickListener {
            var isError = true
            val inputCode = binding.codeCheckEd.text.toString()
            if(inputCode.isEmpty()) {
                setErrorTextField(true)
            }
            for (code in listCode) {
                if ( inputCode == code) {
                    isError = false
                    setErrorTextField(isError)
                    val action = LoginShopModeFragmentDirections.actionLoginShopModeFragmentToShopFragment()
                    findNavController().navigate(action)

                }
            }
            setErrorTextField(isError)

        }
    }
    private fun setErrorTextField(error: Boolean) {
        if(error) {
            binding.inputLayout.isErrorEnabled = true
            binding.inputLayout.error = getString(R.string.error_on_code)
        }else {
            binding.inputLayout.isErrorEnabled = false
            binding.inputLayout.error = null
        }
    }

}